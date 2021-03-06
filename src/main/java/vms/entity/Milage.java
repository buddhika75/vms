/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import vms.enums.MilageType;

/**
 *
 * @author Buddhika
 */
@Entity
public class Milage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    Vehicle vehicle;
    @ManyToOne
    ItemUnit itemUnit;
    @ManyToOne
    Staff staff;
    Integer milageValue;
    @Enumerated
    MilageType type;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date milageDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date createdDate;
    @ManyToOne
    WebUser createdBy;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ItemUnit getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(ItemUnit itemUnit) {
        this.itemUnit = itemUnit;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Integer getMilageValue() {
        return milageValue;
    }

    public void setMilageValue(Integer milageValue) {
        this.milageValue = milageValue;
    }

    public MilageType getType() {
        return type;
    }

    public void setType(MilageType type) {
        this.type = type;
    }

    public Date getMilageDate() {
        return milageDate;
    }

    public void setMilageDate(Date milageDate) {
        this.milageDate = milageDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public WebUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(WebUser createdBy) {
        this.createdBy = createdBy;
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
        if (!(object instanceof Milage)) {
            return false;
        }
        Milage other = (Milage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vms.entity.Milage[ id=" + id + " ]";
    }
    
}
