package vms.controllers;

import com.sun.xml.internal.ws.resources.UtilMessages;
import vms.entity.RunningChart;
import vms.controllers.util.JsfUtil;
import vms.controllers.util.JsfUtil.PersistAction;
import vms.faces.AppointmentFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import vms.enums.EventOrAppointmentType;

@Named("appointmentController")
@SessionScoped
public class RunningChartController implements Serializable {

    @EJB
    private vms.faces.AppointmentFacade ejbFacade;
    private List<RunningChart> items = null;
    private RunningChart selected;
    @Inject
    SessionController sessionController;

    public RunningChartController() {
    }

    public RunningChart getSelected() {
        return selected;
    }

    public void setSelected(RunningChart selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AppointmentFacade getFacade() {
        return ejbFacade;
    }

    public RunningChart prepareCreate() {
        selected = new RunningChart();
        initializeEmbeddableKey();
        return selected;
    }
    
    public boolean errorcheck(){
        if(selected.getFromMilage()==selected.getToMilage()){
            JsfUtil.addErrorMessage("From Milage and To Milage equal");
            return true;
        }
        
        if(selected.getFromMilage()>selected.getToMilage()){
            JsfUtil.addErrorMessage("From Milage is Bigger Than To Milage");
            return true;
        }
        
        return false;
    }
    
    public void createRunningChart() {
        if(errorcheck()){
            return;
        }
        
        selected.setCreatedBy(sessionController.getLoggedUser());
        selected.setCreateAt(new Date());
        double m=selected.getToMilage()-selected.getFromMilage();
        selected.setDoubleValue1(m);
        selected.setType(EventOrAppointmentType.RunnigChartEntry);
        create();
    }
    
    public void manualCreate(){
        selected.setCreateAt(new Date());
        selected.setCreatedBy(sessionController.getLoggedUser());
        selected.setType(EventOrAppointmentType.ItemUnitAppointmentManual);
        create();
    }
    
    public void create() {
        persist(PersistAction.CREATE, "Appointment Created");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "AppointmentUpdated");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "AppointmentDeleted");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RunningChart> getItems() {
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

    public RunningChart getAppointment(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<RunningChart> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RunningChart> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RunningChart.class)
    public static class AppointmentControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RunningChartController controller = (RunningChartController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "appointmentController");
            return controller.getAppointment(getKey(value));
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
            if (object instanceof RunningChart) {
                RunningChart o = (RunningChart) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RunningChart.class.getName()});
                return null;
            }
        }

    }

}
