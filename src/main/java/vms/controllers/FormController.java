/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import vms.entity.ItemOrCategory;
import vms.enums.ItemOrCategoryType;

/**
 *
 * @author User
 */
@Named(value = "formController")
@SessionScoped
public class FormController implements Serializable {

    /**
     * Creates a new instance of FormController
     */
    public FormController() {
    }

    @Inject
    ItemOrCategoryController itemOrCategoryController;

    List<ItemOrCategory> forms;

    public List<ItemOrCategory> getForms() {
        if(forms==null){
            forms=itemOrCategoryController.getItemFromSelectedType(ItemOrCategoryType.Form);
        }
        return forms;
    }

    public void setForms(List<ItemOrCategory> forms) {
        this.forms = forms;
    }

   

}
