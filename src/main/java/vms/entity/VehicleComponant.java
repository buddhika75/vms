/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Buddhika
 */
@Entity
@Inheritance
public class VehicleComponant extends ItemUnit {
    @Temporal(javax.persistence.TemporalType.DATE)
    Date fromDate;
    Integer fromMilage;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date toDate;
    Integer toMilage;
    @OneToOne
    ItemUnit precededBy;
    @OneToOne(mappedBy = "precededBy")
    private VehicleComponant succeededBy;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Integer getFromMilage() {
        return fromMilage;
    }

    public void setFromMilage(Integer fromMilage) {
        this.fromMilage = fromMilage;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getToMilage() {
        return toMilage;
    }

    public void setToMilage(Integer toMilage) {
        this.toMilage = toMilage;
    }

    public ItemUnit getPrecededBy() {
        return precededBy;
    }

    public void setPrecededBy(ItemUnit precededBy) {
        this.precededBy = precededBy;
    }

    public VehicleComponant getSucceededBy() {
        return succeededBy;
    }

    public void setSucceededBy(VehicleComponant succeededBy) {
        this.succeededBy = succeededBy;
    }

    
    

    
}
