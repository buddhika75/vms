package vms.controllers;

import vms.entity.Item;
import vms.controllers.util.JsfUtil;
import vms.controllers.util.JsfUtil.PersistAction;
import vms.enums.ItemOrCategoryType;
import vms.faces.ItemFacade;

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

@ManagedBean(name = "itemController")
@SessionScoped
public class ItemController implements Serializable {

    @EJB
    private vms.faces.ItemFacade ejbFacade;
    private List<Item> items = null;
    private List<Item> vehicleComponent = null;
    private Item selected;

    public ItemController() {
    }


    public Item getSelected() {
        return selected;
    }

    public void setSelected(Item selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ItemFacade getFacade() {
        return ejbFacade;
    }

    public Item prepareCreate() {
        selected = new Item();
        initializeEmbeddableKey();
        return selected;
    }

    
    public void createVehicleComponent(){
        selected.setType(ItemOrCategoryType.VehicleComponent);
        create();
        vehicleComponent = null;
    }
     
    public List<Item> getItemListOfType(ItemOrCategoryType type){
        Map m =new HashMap();
        String sql = "select i from Item i where i.type=:type order by i.name";
        m.put("type", type);
        return getFacade().findBySQL(sql, m);
    }
    
    public void create() {
        persist(PersistAction.CREATE, "ItemCreated");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
       
            
    public void update() {
        persist(PersistAction.UPDATE, ("ItemUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ("ItemDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Item> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<Item> getVehicleComponent() {
        if(vehicleComponent == null){
            vehicleComponent=getItemListOfType(ItemOrCategoryType.VehicleComponent);
        }
        return vehicleComponent;
    }

    public void setVehicleComponent(List<Item> vehicleComponent) {
        this.vehicleComponent = vehicleComponent;
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

    public List<Item> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Item> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Item.class)
    public static class ItemControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ItemController controller = (ItemController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "itemController");
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
            if (object instanceof Item) {
                Item o = (Item) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Item.class.getName()});
                return null;
            }
        }

    }

}
