package vms.controllers;

import vms.entity.Vehicle;
import vms.controllers.util.JsfUtil;
import vms.controllers.util.JsfUtil.PersistAction;
import vms.faces.VehicleFacade;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import vms.entity.DepartmentOrInstitution;

@Named("vehicleController")
@SessionScoped
public class VehicleController implements Serializable {

    @EJB
    private vms.faces.VehicleFacade ejbFacade;
    @Inject
    SessionController sessionController;
    private List<Vehicle> items = null;
    private Vehicle selected;

    public VehicleController() {
    }

    public Vehicle getSelected() {
        return selected;
    }

    public void setSelected(Vehicle selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private VehicleFacade getFacade() {
        return ejbFacade;
    }

    public Vehicle prepareCreate() {
        selected = new Vehicle();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        System.out.println("this = " + sessionController);
        System.out.println("this = " + sessionController.getInstitution().getName());        
        if(sessionController.getInstitution()==null){
            JsfUtil.addErrorMessage("You do not belog to any institution. So you can not add vehicles.");
            return;
        }else{
            selected.setOwnerDepartmentOrInstitution(sessionController.getInstitution());
        }
        persist(PersistAction.CREATE, "Vehicle Created");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ("VehicleUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ("VehicleDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Vehicle> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ("PersistenceErrorOccured"));
            }
        }
    }

    public Vehicle getVehicle(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Vehicle> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Vehicle> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    public List<Vehicle> getVechiclesOfLoggedInstitution() {
        return getVechiclesOfSelectedInstitution(sessionController.getInstitution());
    }
    
    public List<Vehicle> getVechiclesOfSelectedInstitution(DepartmentOrInstitution dor) {
        String jpql;
        Map m = new HashMap();
        jpql = "select v from Vehicle v where v.ownerDepartmentOrInstitution=:dor order by v.name";
        m.put("dor", dor);
        return getFacade().findBySQL(jpql, m);
    }

    
    
    @FacesConverter(forClass = Vehicle.class)
    public static class VehicleControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VehicleController controller = (VehicleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "vehicleController");
            return controller.getVehicle(getKey(value));
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
            if (object instanceof Vehicle) {
                Vehicle o = (Vehicle) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Vehicle.class.getName()});
                return null;
            }
        }

    }

}
