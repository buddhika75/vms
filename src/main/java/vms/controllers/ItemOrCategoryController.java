package vms.controllers;

import vms.hotel.entity.ItemOrCategory;
import vms.controllers.util.JsfUtil;
import vms.controllers.util.JsfUtil.PersistAction;
import vms.hotel.faces.ItemOrCategoryFacade;

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

@ManagedBean(name = "itemOrCategoryController")
@SessionScoped
public class ItemOrCategoryController implements Serializable {

    @EJB
    private vms.hotel.faces.ItemOrCategoryFacade ejbFacade;
    private List<ItemOrCategory> items = null;
    private ItemOrCategory selected;

    public ItemOrCategoryController() {
    }

    public ItemOrCategory getSelected() {
        return selected;
    }

    public void setSelected(ItemOrCategory selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ItemOrCategoryFacade getFacade() {
        return ejbFacade;
    }

    public ItemOrCategory prepareCreate() {
        selected = new ItemOrCategory();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ItemOrCategoryCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ItemOrCategoryUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ItemOrCategoryDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ItemOrCategory> getItems() {
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

    public List<ItemOrCategory> completeItems(String qry){
        String j;
        j="select c from ItemOrCategory c where c.retired=false and lower(c.name) like :qry order by c.name";
        Map m = new HashMap();
        m.put("qry", "%" + qry.toLowerCase() + "%");
        return getFacade().findBySQL(j, m);   
    }
    
    public List<ItemOrCategory> getCategory(){
        String sql = "select c from ItemOrCategory c where c.parent is null ";
        return getFacade().findBySQL(sql);
    }
    
    public List<ItemOrCategory> getItemFromSelectedCategory(ItemOrCategory category){
        Map m = new HashMap();
        String sql = "select c from ItemOrCategory c where c.parent=:category ";
        System.out.println("Category"+category);
        System.out.println("Item from selected Category"+sql);
        m.put("category", category);
        return getFacade().findBySQL(sql, m);
    }

    
    public List<ItemOrCategory> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ItemOrCategory> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ItemOrCategory.class)
    public static class ItemOrCategoryControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ItemOrCategoryController controller = (ItemOrCategoryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "itemOrCategoryController");
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
            if (object instanceof ItemOrCategory) {
                ItemOrCategory o = (ItemOrCategory) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ItemOrCategory.class.getName()});
                return null;
            }
        }

    }

}
