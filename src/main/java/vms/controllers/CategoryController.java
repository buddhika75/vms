package vms.controllers;

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
import vms.controllers.util.JsfUtil;
import vms.controllers.util.JsfUtil.PersistAction;
import vms.entity.Category;
import vms.enums.ItemOrCategoryType;
import vms.faces.CategoryFacade;

@ManagedBean(name = "categoryController")
@SessionScoped
public class CategoryController implements Serializable {

    @EJB
    private vms.faces.CategoryFacade ejbFacade;
    private List<Category> items = null;
    private List<Category> models = null;
    private List<Category> makes = null;
    private List<Category> vehicleCategory = null;
    private List<Category> taxCategory = null;
    private List<Category> country = null;
    private List<Category> colour = null;
    private List<Category> schedule = null;
    private List<Category> event = null;
    private List<Category> activity = null;
    private List<Category> province = null;
    private List<Category> vehicleComponent = null;
            
    private Category selected;

    public CategoryController() {
    }

    public Category getSelected() {
        return selected;
    }

    public void setSelected(Category selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CategoryFacade getFacade() {
        return ejbFacade;
    }

    public List<Category> getModels() {
        if(models == null){
            models = getCategoryListOfType(ItemOrCategoryType.Model);
        }
        return models;
    }

    public void setModels(List<Category> models) {
        this.models = models;
    }

    public List<Category> getMakes() {
        if(makes == null){
            makes=getCategoryListOfType(ItemOrCategoryType.Make);
        }
        return makes;
    }

    
    public void setMakes(List<Category> makes) {
        this.makes = makes;
    }

    public List<Category> getVechical() {
        if(vehicleCategory == null){
            vehicleCategory=getCategoryListOfType(ItemOrCategoryType.VehicleCategory);
        }
        return vehicleCategory;
    }

    public void setVechical(List<Category> vechical) {
        this.vehicleCategory = vechical;
    }

    public List<Category> getTax() {
        if(taxCategory == null){
            taxCategory=getCategoryListOfType(ItemOrCategoryType.TaxCategory);
        }
        return taxCategory;
    }

    public void setTax(List<Category> tax) {
        this.taxCategory = tax;
    }

    public List<Category> getCountry() {
        if(country == null){
            country=getCategoryListOfType(ItemOrCategoryType.Country);
        }
        return country;
    }

    public void setCountry(List<Category> country) {
        this.country = country;
    }

    public List<Category> getColour() {
        if(colour == null){
            colour=getCategoryListOfType(ItemOrCategoryType.Colour);
        }
        return colour;
    }

    public void setColour(List<Category> colour) {
        this.colour = colour;
    }

    public List<Category> getSchedule() {
        if(schedule == null){
            schedule=getCategoryListOfType(ItemOrCategoryType.Schedule);
        }
        return schedule;
    }

    public void setSchedule(List<Category> schedule) {
        this.schedule = schedule;
    }

    public List<Category> getEvent() {
        if(event == null){
            event=getCategoryListOfType(ItemOrCategoryType.Event);
        }
        return event;
    }

    public void setEvent(List<Category> event) {
        this.event = event;
    }

    public List<Category> getActivity() {
        if(activity == null){
            activity=getCategoryListOfType(ItemOrCategoryType.Activity);
        }
        return activity;
    }

    public void setActivity(List<Category> activity) {
        this.activity = activity;
    }

    public List<Category> getProvince() {
        if(province == null){
            province=getCategoryListOfType(ItemOrCategoryType.Province);
        }
        return province;
    }

    public void setProvince(List<Category> province) {
        this.province = province;
    }

    public List<Category> getVehicleComponent() {
        if(vehicleComponent == null){
            vehicleComponent=getCategoryListOfType(ItemOrCategoryType.VehicleComponent);
        }
        return vehicleComponent;
    }

    public void setVehicleComponent(List<Category> vehicleComponent) {
        this.vehicleComponent = vehicleComponent;
    }
    
    

    public Category prepareCreate() {
        selected = new Category();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Created");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void createMake(){
        selected.setType(ItemOrCategoryType.Make);
        create();
        makes = null;
    }
    
    public void createModel(){
        selected.setType(ItemOrCategoryType.Model);
        create();
        models = null;
    }
    
    public void createVechicalCategory(){
        selected.setType(ItemOrCategoryType.VehicleCategory);
        create();
        vehicleCategory = null;
    }
    
    public void createTaxCategory(){
        selected.setType(ItemOrCategoryType.TaxCategory);
        create();
        taxCategory = null;
    }
    
    public void createCountry(){
        selected.setType(ItemOrCategoryType.Country);
        create();
        country = null;
    }
    
    public void createColour(){
        selected.setType(ItemOrCategoryType.Colour);
        create();
        colour = null;
    }
    
    public void createSchedule(){
        selected.setType(ItemOrCategoryType.Schedule);
        create();
        schedule = null;
    }
    
    public void createEvent(){
        selected.setType(ItemOrCategoryType.Event);
        create();
        event = null;
    }
    
    public void createActivity(){
        selected.setType(ItemOrCategoryType.Activity);
        create();
        activity = null;
    }
    
    public void createProvince(){
        selected.setType(ItemOrCategoryType.Province);
        create();
        province = null;
    }
    
    public void createVehicleComponent(){
        selected.setType(ItemOrCategoryType.VehicleComponent);
        create();
        vehicleComponent = null;
    }
    
    public void update() {
        persist(PersistAction.UPDATE, "Updated");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Deleted");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Category> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<Category> getCategoryListOfType(ItemOrCategoryType type){
        Map m = new HashMap();
        String sql = "select c from Category c where c.type=:type order by c.name";
        m.put("type", type);
        return getFacade().findBySQL(sql, m);
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

    public List<Category> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Category> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Category.class)
    public static class CategoryControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CategoryController controller = (CategoryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "categoryController");
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
            if (object instanceof Category) {
                Category o = (Category) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Category.class.getName()});
                return null;
            }
        }

    }

}
