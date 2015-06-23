package vms.controllers;

import vms.entity.Schedule;
import vms.controllers.util.JsfUtil;
import vms.controllers.util.JsfUtil.PersistAction;
import vms.faces.ScheduleFacade;
import java.util.Map;
import java.util.HashMap;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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
import javax.persistence.TemporalType;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import vms.enums.EventOrAppointmentType;

@Named("scheduleController")
@SessionScoped
public class ScheduleController implements Serializable {

    @EJB
    private vms.faces.ScheduleFacade ejbFacade;
    private List<Schedule> items = null;
    List<Schedule> schedules;
    private Schedule selected;
    @Inject
    SessionController sessionController;
    Date fromDate;
    Date toDate;

    public ScheduleController() {
    }

    public Schedule getSelected() {
        return selected;
    }

    public void setSelected(Schedule selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ScheduleFacade getFacade() {
        return ejbFacade;
    }

    public Date getFromDate() {
        if (fromDate == null) {
            fromDate=new Date();
        }
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        if(toDate==null){
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 14);
            toDate = c.getTime();
        }
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
    
    

    public void getSheduleList() {
        String sql;
        Map m =new HashMap();
        sql = "select s from Schedule s "
                + "where s.retired=false "
                + " and s.thisDate between :fd and :td ";
        
        m.put("fd", fromDate);
        m.put("td", toDate);
        
        schedules=ejbFacade.findBySQL(sql, m, TemporalType.TIMESTAMP);
        System.out.println("schedules = " + schedules);
        
    }

    public Schedule prepareCreate() {
        selected = new Schedule();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        selected.setCreateAt(new Date());
        selected.setCreatedBy(sessionController.getLoggedUser());
        selected.setFromDate(selected.getThisDate());
        selected.setToDate(selected.getThisDate());
        selected.setFromMilage(selected.getThisMilage());
        selected.setToMilage(selected.getThisMilage());
        selected.setType(EventOrAppointmentType.ItemUnitScheduleManual);
        persist(PersistAction.CREATE, ("ScheduleCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ("ScheduleUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ("ScheduleDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Schedule> getItems() {
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

    public Schedule getSchedule(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Schedule> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Schedule> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Schedule.class)
    public static class ScheduleControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ScheduleController controller = (ScheduleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "scheduleController");
            return controller.getSchedule(getKey(value));
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
            if (object instanceof Schedule) {
                Schedule o = (Schedule) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Schedule.class.getName()});
                return null;
            }
        }

    }

}
