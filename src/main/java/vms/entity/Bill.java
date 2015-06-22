/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.entity;

import vms.enums.BillCategory;
import vms.enums.BillType;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;

/**
 *
 * @author Buddhika
 */
@Entity
public class Bill implements Serializable {

    @OneToMany(mappedBy = "bill",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<BillItemOrCategory> billItemOrCategorys;
    
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BillDepartmentOrInstitution> billDepartmentOrInstitutions;

    @OneToMany(mappedBy = "perent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bill> children;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("orderNo")
    private List<BillItem> billItems;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String name;

    @ManyToOne
    Institution institution;
    @ManyToOne
    Department department;
    @ManyToOne
    Person person;

    @ManyToOne
    Institution formInstitution;
    @ManyToOne
    Department formDepartment;
    @ManyToOne
    Person formPerson;

    @ManyToOne
    Institution toInstitution;
    @ManyToOne
    Department toDepartment;
    @ManyToOne
    Person toPerson;

    @Enumerated(EnumType.STRING)
    BillType billType;
    @Enumerated(EnumType.STRING)
    BillCategory billCategory;

    @ManyToOne
    Bill perent;

    double grossTotal;
    double billDiscount;
    double billItemDiscount;
    double netTotal;

    double expensesTotal;
    double serviceTotal;
    double taxTotal;

    double grandNetTotal;
    int noofPerson;
    
    double dblValue;
    long longValue;
    @Lob
    String description;
    @Lob
    String comments;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date billFrom;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date billTo;

    //Created Properties
    @ManyToOne
    WebUser creater;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date createdAt;
    //Retairing properties
    boolean retired;
    @ManyToOne
    WebUser retirer;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date retiredAt;
    String retireComments;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date billDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date billTime;
    @ManyToOne
    ItemOrCategory itemOrCategory;
    @ManyToOne
    Bill referenceBill;
    @ManyToOne(cascade = CascadeType.ALL)
    BillItem referenceBillItem;

    public List<BillDepartmentOrInstitution> getBillDepartmentOrInstitutions() {
        if (billDepartmentOrInstitutions == null) {
            billDepartmentOrInstitutions = new ArrayList<BillDepartmentOrInstitution>();
        }
        return billDepartmentOrInstitutions;
    }

    public void setBillDepartmentOrInstitutions(List<BillDepartmentOrInstitution> billDepartmentOrInstitutions) {
        this.billDepartmentOrInstitutions = billDepartmentOrInstitutions;
    }

    public BillItem getReferenceBillItem() {
        return referenceBillItem;
    }

    public void setReferenceBillItem(BillItem referenceBillItem) {
        this.referenceBillItem = referenceBillItem;
    }

    public List<BillItemOrCategory> getBillItemOrCategorys() {
        if (billItemOrCategorys == null) {
            billItemOrCategorys = new ArrayList<BillItemOrCategory>();
        }
        return billItemOrCategorys;
    }

    public void setBillItemOrCategorys(List<BillItemOrCategory> billItemOrCategorys) {
        this.billItemOrCategorys = billItemOrCategorys;
    }

    public Bill getReferenceBill() {
        return referenceBill;
    }

    public void setReferenceBill(Bill referenceBill) {
        this.referenceBill = referenceBill;
    }

    public ItemOrCategory getItemOrCategory() {
        return itemOrCategory;
    }

    public void setItemOrCategory(ItemOrCategory itemOrCategory) {
        this.itemOrCategory = itemOrCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Bill> getChildren() {
        if (children == null) {
            children = new ArrayList<Bill>();
        }
        return children;
    }

    public void setChildren(List<Bill> children) {
        this.children = children;
    }

    public Bill getPerent() {
        return perent;
    }

    public void setPerent(Bill perent) {
        this.perent = perent;
    }

    public double getDblValue() {
        return dblValue;
    }

    public void setDblValue(double dblValue) {
        this.dblValue = dblValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getBillFrom() {
        return billFrom;
    }

    public void setBillFrom(Date billFrom) {
        this.billFrom = billFrom;
    }

    public Date getBillTo() {
        return billTo;
    }

    public void setBillTo(Date billTo) {
        this.billTo = billTo;
    }

    public List<BillItem> getBillItems() {
        if (billItems == null) {
            billItems = new ArrayList<BillItem>();
        }
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }

    public double getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(double grossTotal) {
        this.grossTotal = grossTotal;
    }

    public int getNoofPerson() {
        return noofPerson;
    }

    public void setNoofPerson(int noofPerson) {
        this.noofPerson = noofPerson;
    }

    
    public double getBillDiscount() {
        return billDiscount;
    }

    public void setBillDiscount(double billDiscount) {
        this.billDiscount = billDiscount;
    }

    public double getBillItemDiscount() {
        return billItemDiscount;
    }

    public void setBillItemDiscount(double billItemDiscount) {
        this.billItemDiscount = billItemDiscount;
    }

    public double getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(double netTotal) {
        this.netTotal = netTotal;
    }

    public double getExpensesTotal() {
        return expensesTotal;
    }

    public void setExpensesTotal(double expensesTotal) {
        this.expensesTotal = expensesTotal;
    }

    public double getServiceTotal() {
        return serviceTotal;
    }

    public void setServiceTotal(double serviceTotal) {
        this.serviceTotal = serviceTotal;
    }

    public double getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(double taxTotal) {
        this.taxTotal = taxTotal;
    }

    public double getGrandNetTotal() {
        return grandNetTotal;
    }

    public void setGrandNetTotal(double grandNetTotal) {
        this.grandNetTotal = grandNetTotal;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Institution getFormInstitution() {
        return formInstitution;
    }

    public void setFormInstitution(Institution formInstitution) {
        this.formInstitution = formInstitution;
    }

    public Department getFormDepartment() {
        return formDepartment;
    }

    public void setFormDepartment(Department formDepartment) {
        this.formDepartment = formDepartment;
    }

    public Person getFormPerson() {
        return formPerson;
    }

    public void setFormPerson(Person formPerson) {
        this.formPerson = formPerson;
    }

    public Institution getToInstitution() {
        return toInstitution;
    }

    public void setToInstitution(Institution toInstitution) {
        this.toInstitution = toInstitution;
    }

    public Department getToDepartment() {
        return toDepartment;
    }

    public void setToDepartment(Department toDepartment) {
        this.toDepartment = toDepartment;
    }

    public Person getToPerson() {
        return toPerson;
    }

    public void setToPerson(Person toPerson) {
        this.toPerson = toPerson;
    }

    public BillType getBillType() {
        return billType;
    }

    public void setBillType(BillType billType) {
        this.billType = billType;
    }

    public BillCategory getBillCategory() {
        return billCategory;
    }

    public void setBillCategory(BillCategory billCategory) {
        this.billCategory = billCategory;
    }

    public WebUser getCreater() {
        return creater;
    }

    public void setCreater(WebUser creater) {
        this.creater = creater;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public WebUser getRetirer() {
        return retirer;
    }

    public void setRetirer(WebUser retirer) {
        this.retirer = retirer;
    }

    public Date getRetiredAt() {
        return retiredAt;
    }

    public void setRetiredAt(Date retiredAt) {
        this.retiredAt = retiredAt;
    }

    public String getRetireComments() {
        return retireComments;
    }

    public void setRetireComments(String retireComments) {
        this.retireComments = retireComments;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Date getBillTime() {
        return billTime;
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bill)) {
            return false;
        }
        Bill other = (Bill) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hotel.entity.Bill[ id=" + id + " ]";
    }

}
