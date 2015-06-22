/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.controllers;

import com.sun.corba.se.impl.util.Utility;
import com.sun.org.apache.bcel.internal.generic.Select;
import vms.controllers.util.JsfUtil;
import vms.entity.Bill;
import vms.entity.BillDepartmentOrInstitution;
import vms.entity.BillItem;
import vms.entity.Item;
import vms.entity.ItemOrCategory;
import vms.enums.BillItemType;
import vms.enums.BillType;
import vms.enums.ItemOrCategoryType;
import vms.faces.BillFacade;
import vms.faces.BillItemFacade;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.swing.text.Utilities;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author buddhika
 */
@ManagedBean
@SessionScoped
public class FunctionsEventController {

    @EJB
    BillFacade billFacade;

    @EJB
    BillItemFacade billItemFacade;

    List<Bill> functions;

    BillItem selectedBillItem;
    BillItem editingItem;

    List<Item> halls;
    Bill selectedMenu;
    Bill functionEvent;

    List<Bill> bookedEvents;
    List<Bill> bookedHalls;
    List<ItemOrCategory> itemOrCategoryList;
    
    ItemOrCategory itemOrCategory;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date fromDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date toDate;

    public Date getFromDate() {

        return fromDate;

    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public List<Bill> getBookedHalls() {
        return bookedHalls;
    }

    public void setBookedHalls(List<Bill> bookedHalls) {
        this.bookedHalls = bookedHalls;
    }

    public List<ItemOrCategory> getItemOrCategoryList() {
        return itemOrCategoryList;
    }

    public void setItemOrCategoryList(List<ItemOrCategory> itemOrCategoryList) {
        this.itemOrCategoryList = itemOrCategoryList;
    }

    
    
    public List<Bill> getBookedEvents() {
        return bookedEvents;
    }

    public void setBookedEvents(List<Bill> bookedEvents) {
        this.bookedEvents = bookedEvents;
    }

    public void fillBookedHalls() {
        if (functionEvent == null) {
            return;
        }
        String jpql;
        Map m = new HashMap();
        jpql = "select b from Bill b Where b.billType=:bt and b.department=:dep";
        m.put("bt", BillType.FunctionEvent);
        m.put("dep", functionEvent.getDepartment());
        bookedHalls = billFacade.findBySQL(jpql, m);

    }

    public void fillBookedEvents() {
        if (functionEvent == null) {
            return;
        }
        String jpql;
        Map m = new HashMap();
        jpql = "select b from Bill b where b.billDate=:bd and b.billType=:bt";
        m.put("bd", functionEvent.getBillDate());
        m.put("bt", BillType.FunctionEvent);
        bookedEvents = billFacade.findBySQL(jpql, m, TemporalType.DATE);
    }

    public Bill getSelectedMenu() {
        return selectedMenu;
    }

    public void setSelectedMenu(Bill selectedMenu) {
        this.selectedMenu = selectedMenu;
    }

    public Bill getFunctionEvent() {
        if (functionEvent == null) {
            functionEvent = new Bill();
            functionEvent.setBillDate(new Date());
            functionEvent.setBillType(BillType.FunctionEvent);
            functionEvent.setRetired(true);
            billFacade.create(functionEvent);
            System.out.println("saving event for the first time");
            billFacade.edit(functionEvent);
            System.out.println("saing again after creation = ");
        }
        return functionEvent;
    }

    public void setFunctionEvent(Bill functionEvent) {
        this.functionEvent = functionEvent;
    }

    public List<Item> getHalls() {
        return halls;
    }

    public void setHalls(List<Item> halls) {
        this.halls = halls;
    }

    public String createNewEvent() {
        functionEvent = null;
        return "/functions/create_function.xhtml";
    }

    public String createEventBooking(){
        functionEvent = null;
        return "/functions/function_booking.xhtml";
    }
    
    public void fillBillItemsForFunctionEvent() {
        System.out.println("functionEvent = " + functionEvent);
        if (functionEvent == null) {
            return;
        }

        System.out.println("functionEvent.getBillDepartmentOrInstitutions() = " + functionEvent.getBillDepartmentOrInstitutions());

        for (BillDepartmentOrInstitution d : functionEvent.getBillDepartmentOrInstitutions()) {
            System.out.println("d = " + d);
        }

        for (BillItem bi : functionEvent.getBillItems()) {
            bi.setRetired(true);
            billItemFacade.edit(bi);
        }

        System.out.println("functionEvent.getReferenceBill() = " + functionEvent.getReferenceBill());
        System.out.println("functionEvent.getReferenceBill().getBillItems() = " + functionEvent.getReferenceBill().getBillItems());
        for (BillItem bi : functionEvent.getReferenceBill().getBillItems()) {
            System.out.println("bi = " + bi);
            if (bi.isRetired()) {
                continue;
            }
            if (bi.getItemOrCategory().getType() == ItemOrCategoryType.MenuItemCategory) {
                System.out.println("bi = " + bi);
                System.out.println("bi.getItemOrCategory() = " + bi.getItemOrCategory());

                BillItem nbic = new BillItem();
                billItemFacade.create(nbic);
                nbic.setBill(functionEvent);
                nbic.setBillItemType(BillItemType.BillComponent);
                nbic.setItemOrCategory(bi.getItemOrCategory());
                Long l = bi.getLongVal();
                int im = l.intValue();
                int i = 0;
                for (ItemOrCategory ioc : bi.getItemOrCategory().getChildren()) {
                    i++;
                    System.out.println("i = " + i);
                    System.out.println("im = " + im);
                    System.out.println("ioc = " + ioc.getName());
                    BillItem nbi = new BillItem();
                    billItemFacade.create(nbi);
                    nbi.setItemOrCategory(ioc);
                    nbi.setParent(nbic);
                    nbic.getChildren().add(nbi);
                    if (i >= im) {
                        System.out.println("breaking");
                        break;
                    }
                }

                functionEvent.getBillItems().add(nbic);
            } else if (bi.getItemOrCategory().getType() == ItemOrCategoryType.MenuItem) {
                BillItem nbi = new BillItem();
                nbi.setBill(functionEvent);
                nbi.setBillItemType(BillItemType.BillComponent);
                nbi.setItemOrCategory(bi.getItemOrCategory());
                billItemFacade.create(nbi);
                functionEvent.getBillItems().add(nbi);
            }
        }
        System.out.println("functionEvent.getBillItems() = " + functionEvent.getBillItems());
//        billFacade.edit(functionEvent);
    }

    public void saveFunctionEvent() {
//        System.out.println("functionEvent.getBillCategory() = " + functionEvent.getBillCategory());
//        System.out.println("functionEvent.getBillCategory() = " + functionEvent.getBillDepartmentOrInstitutions());
//        System.out.println("functionEvent.getBillCategory() = " + functionEvent.getBillItemOrCategorys());
//        System.out.println("functionEvent.getBillCategory() = " + functionEvent.getBillItems());
//        System.out.println("functionEvent.getBillCategory() = " + functionEvent.getChildren());
//        functionEvent.setRetired(false);
        if (functionEvent == null) {
            JsfUtil.addErrorMessage("Select");
            return;
        }
        if (functionEvent.getId() == null) {
            billFacade.create(functionEvent);
            JsfUtil.addSuccessMessage("New Event Created");
        } else {
            billFacade.edit(functionEvent);
            JsfUtil.addSuccessMessage("Event Updated");
        }
    }

    public void fixFromAndToDates() {
        Calendar f = Calendar.getInstance();

        Calendar t = Calendar.getInstance();

        f.setTime(getFunctionEvent().getBillDate());

        f.set(Calendar.AM_PM, 0);
        f.set(Calendar.MINUTE, 0);
        f.set(Calendar.MILLISECOND, 0);
        f.set(Calendar.SECOND, 0);
        f.set(Calendar.HOUR, 8);
        getFunctionEvent().setBillFrom(f.getTime());

        t.setTime(getFunctionEvent().getBillDate());
        f.set(Calendar.AM_PM, 1);
        t.set(Calendar.MINUTE, 0);
        t.set(Calendar.MILLISECOND, 0);
        t.set(Calendar.HOUR_OF_DAY, 17);
        t.set(Calendar.SECOND, 0);

        getFunctionEvent().setBillTo(f.getTime());

        fillBookedEvents();

    }

    public BillItem getEditingItem() {
        return editingItem;
    }

    public void setEditingItem(BillItem editingItem) {
        this.editingItem = editingItem;
    }

    public void moveUp() {
        if (functionEvent == null || editingItem == null) {
            JsfUtil.addErrorMessage("Noting to move");
            return;
        }
        if (functionEvent.getId() == null || editingItem.getId() == null) {
            JsfUtil.addErrorMessage("Save first to move");
            return;
        }
        BillItem pbi = new BillItem();
        for (BillItem bi : functionEvent.getBillItems()) {
            if (bi.isRetired()) {
                continue;
            }
            if (bi.equals(editingItem)) {
                pbi.setOrderNo(pbi.getOrderNo() + 1);
                bi.setOrderNo(bi.getOrderNo() - 1);
                billItemFacade.edit(bi);
                billItemFacade.edit(pbi);
            }

            pbi = bi;
        }
        billFacade.edit(functionEvent);
        functionEvent = billFacade.find(functionEvent.getId());
    }

    public void moveDown() {
        if (functionEvent == null || editingItem == null) {
            JsfUtil.addErrorMessage("Noting to move");
            return;
        }
        if (functionEvent.getId() == null || editingItem.getId() == null) {
            JsfUtil.addErrorMessage("Save first to move");
            return;
        }
        BillItem pbi = new BillItem();
        for (BillItem bi : functionEvent.getBillItems()) {
            if (bi.isRetired()) {
                continue;
            }
            if (pbi.equals(editingItem)) {
                pbi.setOrderNo(pbi.getOrderNo() + 1);
                bi.setOrderNo(bi.getOrderNo() - 1);
                billItemFacade.edit(bi);
                billItemFacade.edit(pbi);
            }

            pbi = bi;
        }
        billFacade.edit(functionEvent);
        functionEvent = billFacade.find(functionEvent.getId());
    }

    public void removeBi() {
        System.out.println("functionEvent = " + functionEvent);
        System.out.println("editingItem = " + editingItem);
        if (functionEvent == null || editingItem == null) {
            JsfUtil.addErrorMessage("Select one");
            return;
        }
        editingItem.setRetired(true);
        editingItem.setRetiredAt(new Date());
        editingItem.setRetirer(null);
        billItemFacade.edit(editingItem);
        functionEvent = billFacade.find(functionEvent.getId());
    }

    public void addBillItem() {
        System.out.println("adding bill item");
        if (functionEvent == null || selectedBillItem == null || selectedBillItem.getItemOrCategory() == null) {
            JsfUtil.addErrorMessage("Please select item");
            return;
        }
        BillItem bi = selectedBillItem;
        bi.setBill(functionEvent);
        bi.setOrderNo(functionEvent.getBillItems().size() + 1);
        functionEvent.getBillItems().add(bi);
        selectedBillItem = null;
        saveFunctionEvent();
        getSelectedBillItem();
    }

    public BillItem getSelectedBillItem() {
        if (selectedBillItem == null) {
            selectedBillItem = new BillItem();
            selectedBillItem.setBillItemType(BillItemType.BillComponent);
        }
        return selectedBillItem;
    }

    public void setSelectedBillItem(BillItem selectedBillItem) {
        this.selectedBillItem = selectedBillItem;
    }

    /**
     * Creates a new instance of FunctionsController
     */
    public FunctionsEventController() {
    }

    public List<Bill> getFunctions() {
        if (functions == null) {
            fillFunctions();
        }
        return functions;
    }

    public void prepareAdd() {
        functionEvent = new Bill();
//        selected.setBillType(BillType.FunctionMenu);
    }

    public void prepareAddNewEvent() {
        functionEvent = new Bill();
        functionEvent.setBillType(BillType.FunctionMenu);
    }

    public void save() {
        if (functionEvent.getId() == null) {
            billFacade.create(functionEvent);
            JsfUtil.addSuccessMessage("Saved");
        } else {
            billFacade.edit(functionEvent);
            JsfUtil.addSuccessMessage("Updated");
        }
//        saveBillItems(selected.getBillItems());
        fillFunctions();
    }

    public void remove() {
        if (functionEvent == null) {
            JsfUtil.addErrorMessage("Select one to remove");
            return;
        } else {
            functionEvent.setRetired(true);
            functionEvent.setRetirer(null);
            functionEvent.setRetiredAt(new Date());
            billFacade.edit(functionEvent);
            JsfUtil.addSuccessMessage("Removed");
        }
        fillFunctions();
    }

    public void setFunctions(List<Bill> functions) {
        this.functions = functions;
    }

    public void fillFunctions() {
        String jpql;
        Map m = new HashMap();
        jpql = "select b from Bill b where b.retired=false and b.billType=:bt order by b.name";
        m.put("bt", BillType.FunctionMenu);
        functions = billFacade.findBySQL(jpql, m);
    }

    public void searchForm() {
        
        String jpql;
        Map m = new HashMap();
        jpql = "select b from Bill b where b.billDate between :fd and :td";
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        functions = billFacade.findBySQL(jpql, m, TemporalType.TIMESTAMP);

    }

    @Inject
    ItemOrCategoryController itemOrCategoryController;
    
    public void getSelectedCategory(){
        
        if (itemOrCategory == null) {
            JsfUtil.addErrorMessage("Please Select Category.");
            return;
        }
//        itemOrCategory = ((ItemOrCategory)event.getObject());
        System.out.println("Get select Category "+itemOrCategory);
        itemOrCategoryList = itemOrCategoryController.getItemFromSelectedCategory(itemOrCategory);
        System.out.println("Category List "+ itemOrCategoryList);
    }

    public ItemOrCategory getItemOrCategory() {
        return itemOrCategory;
    }

    public void setItemOrCategory(ItemOrCategory itemOrCategory) {
        this.itemOrCategory = itemOrCategory;
    }
}
