package vms.controllers;

import vms.hotel.entity.DepartmentOrInstitution;
import vms.controllers.util.JsfUtil;
import vms.controllers.util.JsfUtil.PersistAction;
import vms.hotel.enums.DepartmentOrInstitutionType;
import vms.hotel.faces.DepartmentOrInstitutionFacade;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@ManagedBean(name = "departmentOrInstitutionController")
@SessionScoped
public class DepartmentOrInstitutionController implements Serializable {

    @EJB
    private vms.hotel.faces.DepartmentOrInstitutionFacade ejbFacade;
    private List<DepartmentOrInstitution> items = null;
    private DepartmentOrInstitution selected;

    List<DepartmentOrInstitution> halls;

    public List<DepartmentOrInstitution> getHalls() {
        if (null == halls || halls.isEmpty()) {
            fillHalls();
        } else {
        }
        return halls;
    }

    @Inject
    SessionController sessionController;
    
    public void fillHalls() {
        String j;
        j = "select h from Department h where h.retired=false and h.departmentOrInstitutionType=:dit ";
        Map m = new HashMap();
        if(sessionController.getInstitution()!=null){
            j = j+ " and h.parent=:ins ";
            m.put("ins", sessionController.getInstitution());
        }
        j = j + "order by h.name";
        
        m.put("dit", DepartmentOrInstitutionType.Hall);
        System.out.println("m = " + m);
        System.out.println("j = " + j);
        halls = ejbFacade.findBySQL(j, m);
        System.out.println("halls = " + halls);
    }

    public void completeInstitutions() {
        String j;
        j = "select h from Institution h where h.retired=false and h.departmentOrInstitutionType=:dit order by h.name";
        Map m = new HashMap();
        m.put("dit", DepartmentOrInstitutionType.Hall);
        halls = ejbFacade.findBySQL(j, m);
    }

    public void setHalls(List<DepartmentOrInstitution> halls) {
        this.halls = halls;
    }

    public DepartmentOrInstitutionController() {
    }

    public DepartmentOrInstitution getSelected() {
        return selected;
    }

    public void setSelected(DepartmentOrInstitution selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DepartmentOrInstitutionFacade getFacade() {
        return ejbFacade;
    }

    public DepartmentOrInstitution prepareCreate() {
        selected = new DepartmentOrInstitution();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DepartmentOrInstitutionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DepartmentOrInstitutionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DepartmentOrInstitutionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DepartmentOrInstitution> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<DepartmentOrInstitution> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DepartmentOrInstitution> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DepartmentOrInstitution.class)
    public static class DepartmentOrInstitutionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DepartmentOrInstitutionController controller = (DepartmentOrInstitutionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "departmentOrInstitutionController");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof DepartmentOrInstitution) {
                DepartmentOrInstitution o = (DepartmentOrInstitution) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DepartmentOrInstitution.class.getName()});
                return null;
            }
        }

    }

}
