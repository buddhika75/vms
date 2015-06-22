/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.entity;

import vms.enums.BillItemType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Buddhika
 */
@Entity
public class BillItem implements Serializable {
    @OneToMany(mappedBy = "parent")
    private List<BillItem> children;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    @ManyToOne
    ItemOrCategory itemOrCategory;
    @ManyToOne
    DepartmentOrInstitution departmentOrInstitution;
    double grossRate;
    double itemDiscountRate;
    double billDiscountRate;
    double netRate;
    
    
    @ManyToOne
    Bill bill;
    @Enumerated(EnumType.STRING)
    BillItemType billItemType;
    @ManyToOne
    BillItem parent;
    
    double qty;
    double freeQty;
    long longVal;
    int orderNo;
    
    @ManyToOne
    BillItem referenceBillItem;
    @ManyToOne
    Bill referenceBill;
    
    @Lob
    String comments;
    
    

    public DepartmentOrInstitution getDepartmentOrInstitution() {
        return departmentOrInstitution;
    }

    public void setDepartmentOrInstitution(DepartmentOrInstitution departmentOrInstitution) {
        this.departmentOrInstitution = departmentOrInstitution;
    }

    public BillItem getReferenceBillItem() {
        return referenceBillItem;
    }

    public void setReferenceBillItem(BillItem referenceBillItem) {
        this.referenceBillItem = referenceBillItem;
    }

    public Bill getReferenceBill() {
        return referenceBill;
    }

    public void setReferenceBill(Bill referenceBill) {
        this.referenceBill = referenceBill;
    }

    
    
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
    
    

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getFreeQty() {
        return freeQty;
    }

    public void setFreeQty(double freeQty) {
        this.freeQty = freeQty;
    }

    public long getLongVal() {
        return longVal;
    }

    public void setLongVal(long longVal) {
        this.longVal = longVal;
    }
    
    

    public List<BillItem> getChildren() {
        if(children==null){
            children= new ArrayList<BillItem>();
        }
        return children;
    }

    public void setChildren(List<BillItem> children) {
        this.children = children;
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

    public ItemOrCategory getItemOrCategory() {
        return itemOrCategory;
    }

    public void setItemOrCategory(ItemOrCategory itemOrCategory) {
        this.itemOrCategory = itemOrCategory;
    }

    public double getGrossRate() {
        return grossRate;
    }

    public void setGrossRate(double grossRate) {
        this.grossRate = grossRate;
    }

    public double getItemDiscountRate() {
        return itemDiscountRate;
    }

    public void setItemDiscountRate(double itemDiscountRate) {
        this.itemDiscountRate = itemDiscountRate;
    }

    public double getBillDiscountRate() {
        return billDiscountRate;
    }

    public void setBillDiscountRate(double billDiscountRate) {
        this.billDiscountRate = billDiscountRate;
    }

    public double getNetRate() {
        return netRate;
    }

    public void setNetRate(double netRate) {
        this.netRate = netRate;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public BillItemType getBillItemType() {
        return billItemType;
    }

    public void setBillItemType(BillItemType billItemType) {
        this.billItemType = billItemType;
    }

    public BillItem getParent() {
        return parent;
    }

    public void setParent(BillItem parent) {
        this.parent = parent;
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
        if (!(object instanceof BillItem)) {
            return false;
        }
        BillItem other = (BillItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hotel.entity.BillItem[ id=" + id + " ]";
    }

}
