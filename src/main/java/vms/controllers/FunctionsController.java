/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.controllers;

import vms.controllers.util.JsfUtil;
import vms.hotel.entity.Bill;
import vms.hotel.entity.BillItem;
import vms.hotel.entity.Item;
import vms.hotel.entity.ItemOrCategory;
import vms.hotel.enums.BillItemType;
import vms.hotel.enums.BillType;
import vms.hotel.enums.ItemOrCategoryType;
import vms.hotel.faces.BillFacade;
import vms.hotel.faces.BillItemFacade;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author buddhika
 */
@ManagedBean
@SessionScoped
public class FunctionsController {

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

    public void fillBillItemsForFunctionEvent() {
        System.out.println("functionEvent = " + functionEvent);
        if (functionEvent.getId() != null) {
            return;
        }
        functionEvent.setBillItems(null);
        System.out.println("functionEvent.getReferenceBill() = " + functionEvent.getReferenceBill());
        System.out.println("functionEvent.getReferenceBill().getBillItems() = " + functionEvent.getReferenceBill().getBillItems());
        for (BillItem bi : functionEvent.getReferenceBill().getBillItems()) {
            System.out.println("bi = " + bi);
            if (bi.isRetired()) {
                continue;
            }
            if (bi.getItemOrCategory().getType() == ItemOrCategoryType.MenuItemCategory) {
                BillItem nbic = new BillItem();
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
                functionEvent.getBillItems().add(nbi);
            }
        }
    }

    public void saveFunctionEvent() {
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
    public FunctionsController() {
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

    public Bill getSelected() {
        return functionEvent;
    }

    public void setSelected(Bill selected) {
        this.functionEvent = selected;
    }

}
