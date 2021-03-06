package vms.controllers;

import vms.entity.WebUser;
import vms.controllers.util.JsfUtil;
import vms.controllers.util.JsfUtil.PersistAction;
import vms.entity.Institution;
import vms.entity.Person;
import vms.entity.Staff;
import vms.faces.WebUserFacade;

import java.io.Serializable;
import java.util.Date;
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

@ManagedBean(name = "webUserController")
@SessionScoped
public class WebUserController implements Serializable {

    @EJB
    private vms.faces.WebUserFacade ejbFacade;
    private List<WebUser> items = null;
    private WebUser selected;

    Person person;
    Staff staff;
    WebUser webUser;

    public String createNewUser() {
        person = new Person();
        staff = new Staff();
        staff.setPerson(person);
        webUser = new WebUser();
        webUser.setStaff(staff);
        return "/webUser/webuser";
    }

    public String listUsers() {
        return "/webUser/List";
    }

    public String saveWebUser() {
        if (webUser.getId() == null) {
            ejbFacade.create(webUser);
            webUser = null;
            
            JsfUtil.addSuccessMessage("New User Created");
            return "/webUser/webuser_index.xhtml"; 
        } else {
            ejbFacade.edit(webUser);
            JsfUtil.addSuccessMessage("User Details Updated");
            return "/webUser/webuser_index.xhtml";
        }

    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public WebUser getWebUser() {
        return webUser;
    }

    public void setWebUser(WebUser webUser) {
        this.webUser = webUser;
    }

    public WebUserController() {
    }

    public WebUser getSelected() {
        return selected;
    }

    public void setSelected(WebUser selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private WebUserFacade getFacade() {
        return ejbFacade;
    }

    public WebUser prepareCreate() {
        selected = new WebUser();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ("WebUserCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ("WebUserUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ("WebUserDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    @Inject
    SessionController sessionController;

    public void retire() {
        if (selected == null) {
            return;
        }
        selected.setRetired(true);
        selected.setRetiredAt(new Date());
        selected.setRetirer(sessionController.loggedUser);
        ejbFacade.edit(selected);
        JsfUtil.addErrorMessage("Deleted");
        fillWebUsers();
    }

    Institution previousInstitution;
    
    public List<WebUser> getItems() {
        if(previousInstitution!=sessionController.getInstitution()){
            fillWebUsers();
            previousInstitution = (Institution) sessionController.getInstitution();
        }
        if (items == null) {
            fillWebUsers();
        }
        return items;
    }

    public void fillWebUsers() {
        String sql;
        Map m = new HashMap();
        sql = "select u from WebUser u where u.retired=:r ";
        m.put("r", false);
        if (sessionController.getInstitution() != null) {
            sql = sql + " and u.institution=:ins ";
            m.put("ins", sessionController.getInstitution());
        }
        sql = sql + " order by u.staff.person.name";
        items = ejbFacade.findBySQL(sql, m);
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

    public List<WebUser> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<WebUser> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = WebUser.class)
    public static class WebUserControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            WebUserController controller = (WebUserController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "webUserController");
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
            if (object instanceof WebUser) {
                WebUser o = (WebUser) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), WebUser.class.getName()});
                return null;
            }
        }

    }

}
