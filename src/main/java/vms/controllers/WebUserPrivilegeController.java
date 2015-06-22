/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.controllers;

import com.sun.corba.se.impl.util.Utility;
import vms.controllers.util.JsfUtil;
import vms.entity.WebUser;
import vms.entity.WebUserPrivilege;
import vms.enums.Privilege;
import vms.faces.WebUserFacade;
import vms.faces.WebUserPrivilegeFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.management.Query;

/**
 *
 * @author Rohan
 */
@Named(value = "webUserPrivilegeController")
@SessionScoped
public class WebUserPrivilegeController implements Serializable {

    /**
     * Creates a new instance of WebUserPrivilegeController
     */
    public WebUserPrivilegeController() {
    }

    WebUser selected;
    List<Privilege> selectedUserPrivileges;
    List<Privilege> selectedAvailablePrivileges;
    Privilege selectedUserPrivilege;
    Privilege selectedAvailablePrivilege;
    List<WebUser> webusers;

    @EJB
    WebUserPrivilegeFacade webUserPrivilegeFacade;
    @EJB
    WebUserFacade webUserFacade;

    SessionController sessionController;

    public void changeUser() {
        if (selected == null) {
            return;
        }
        fillSelectedUserPrivileges();
    }

    private void fillSelectedUserPrivileges() {
        String jpql;
        Map m = new HashMap();
        jpql = "select p from WebUserPrivilege p where p.webUser=:wu and p.retired=false order by p.name";
        m.put("wu", selected);
        List<WebUserPrivilege> lst = webUserPrivilegeFacade.findBySQL(jpql, m);
        selectedUserPrivileges = new ArrayList<Privilege>();
        selectedAvailablePrivileges = new ArrayList<Privilege>();
        Collections.addAll(selectedAvailablePrivileges, Privilege.values());
        System.out.println("selectedAvailablePrivileges = " + selectedAvailablePrivileges);
        System.out.println("lst = " + lst.size());
        for (WebUserPrivilege p : lst) {
            System.out.println("p = " + p);
            selectedUserPrivileges.add(p.getPrivilege());
            selectedAvailablePrivileges.remove(p.getPrivilege());
        }
        System.out.println("selectedUserPrivileges = " + selectedUserPrivileges);
        System.out.println("selectedAvailablePrivileges = " + selectedAvailablePrivileges);
    }

    public void addPrivilege() {
        System.out.println("***In***");
        System.out.println("Add");
        System.out.println("selected = " + selected);
        System.out.println("selectedUserPrivilege = " + selectedUserPrivilege);
        if (selected == null) {
            JsfUtil.addSuccessMessage("Select User");
            return;
        }
        if (selectedUserPrivilege == null) {
            JsfUtil.addSuccessMessage("Select Privilege");
            return;
        }
        WebUserPrivilege p = new WebUserPrivilege();
        p.setWebUser(selected);
        p.setPrivilege(selectedUserPrivilege);
        webUserPrivilegeFacade.create(p);
        selectedUserPrivileges.add(selectedUserPrivilege);
        selectedAvailablePrivileges.remove(selectedUserPrivilege);
        JsfUtil.addSuccessMessage("Successfully Added");
        System.out.println("***Out***");
    }

    public List<WebUser> fillWebUsers(String qry) {
        String sql;
        sql = " select u from WebUser u where u.retired=false "
                + " and upper(u.staff.person.name) like '%" + qry.toUpperCase() + "%' "
                + " order by u.staff.person.name ";
        webusers = webUserFacade.findBySQL(sql);
        return webusers;
    }

    public void removePrivilege() {
        System.out.println("***In***");
        System.out.println("Remove");
        System.out.println("selected = " + selected);
        System.out.println("selectedAvailablePrivilege = " + selectedAvailablePrivilege);
        if (selected == null) {
            return;
        }
        if (selectedAvailablePrivilege == null) {
            return;
        }
        String jpql;
        Map m = new HashMap();
        jpql = "select p from WebUserPrivilege p where p.webUser=:wu "
                + " and p.retired=false "
                + " and p.privilege=:p"
                + " order by p.name";
        m.put("wu", selected);
        m.put("p", selectedAvailablePrivilege);
        List<WebUserPrivilege> lst = webUserPrivilegeFacade.findBySQL(jpql, m);
        System.out.println("lst.size() = " + lst.size());
        for (WebUserPrivilege wp : lst) {
            wp.setRetired(true);
//            wp.setRetirer("");
            wp.setRetiredAt(new Date());
            webUserPrivilegeFacade.edit(wp);

        }
        selectedUserPrivileges.remove(selectedAvailablePrivilege);
        selectedAvailablePrivileges.add(selectedAvailablePrivilege);
        JsfUtil.addSuccessMessage("Successfully removed");
        System.out.println("***Out***");
    }

    public WebUser getSelected() {
        return selected;
    }

    public void setSelected(WebUser selected) {
        this.selected = selected;
    }

    public List<Privilege> getSelectedUserPrivileges() {
        if (selectedUserPrivileges==null) {
            selectedUserPrivileges=new ArrayList<Privilege>();
        }
        return selectedUserPrivileges;
    }

    public void setSelectedUserPrivileges(List<Privilege> selectedUserPrivileges) {
        this.selectedUserPrivileges = selectedUserPrivileges;
    }

    public List<Privilege> getSelectedAvailablePrivileges() {
        if (selectedAvailablePrivileges==null) {
            selectedAvailablePrivileges=new ArrayList<Privilege>();
        }
        return selectedAvailablePrivileges;
    }

    public void setSelectedAvailablePrivileges(List<Privilege> selectedAvailablePrivileges) {
        this.selectedAvailablePrivileges = selectedAvailablePrivileges;
    }

    public Privilege getSelectedUserPrivilege() {
        return selectedUserPrivilege;
    }

    public void setSelectedUserPrivilege(Privilege selectedUserPrivilege) {
        this.selectedUserPrivilege = selectedUserPrivilege;
    }

    public Privilege getSelectedAvailablePrivilege() {
        return selectedAvailablePrivilege;
    }

    public void setSelectedAvailablePrivilege(Privilege selectedAvailablePrivilege) {
        this.selectedAvailablePrivilege = selectedAvailablePrivilege;
    }

    public List<WebUser> getWebusers() {
        return webusers;
    }

    public void setWebusers(List<WebUser> webusers) {
        this.webusers = webusers;
    }

}
