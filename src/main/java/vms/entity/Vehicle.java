/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Buddhika
 */
@Entity
@Inheritance
public class Vehicle extends ItemUnit {

    String engineNo;
    String chassicNo;
    Integer engineCapasity;
    String registrationNo;
    @Lob
    String CurrentOwner;
    @Lob
    String Conditions;
    @ManyToOne
    ItemOrCategory vehicleClass;
    @ManyToOne
    ItemOrCategory taxacionClass;
    @Lob
    String statusWhenRegistered;
    @ManyToOne
    ItemOrCategory fuelType;
    @ManyToOne
    ItemOrCategory typeOfBody;
    @Lob
    String previousOwners;
    Integer seatingCapacity;
    Double weightUnloaded;
    Double weightGross;
    Double tyreSizeFront;
    Double tyreSizeRear;
    Boolean duelTyresFront;
    Boolean duelTyresRear;
    Double lengthInMeters;
    Double widthInMeters;
    Double heightInCm;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dateOfFirstRegistration;
    @Lob
    String taxes;

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getChassicNo() {
        return chassicNo;
    }

    public void setChassicNo(String chassicNo) {
        this.chassicNo = chassicNo;
    }

    public Integer getEngineCapasity() {
        return engineCapasity;
    }

    public void setEngineCapasity(Integer engineCapasity) {
        this.engineCapasity = engineCapasity;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getCurrentOwner() {
        return CurrentOwner;
    }

    public void setCurrentOwner(String CurrentOwner) {
        this.CurrentOwner = CurrentOwner;
    }

    public String getConditions() {
        return Conditions;
    }

    public void setConditions(String Conditions) {
        this.Conditions = Conditions;
    }

    public ItemOrCategory getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(ItemOrCategory vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public ItemOrCategory getTaxacionClass() {
        return taxacionClass;
    }

    public void setTaxacionClass(ItemOrCategory taxacionClass) {
        this.taxacionClass = taxacionClass;
    }

    public String getStatusWhenRegistered() {
        return statusWhenRegistered;
    }

    public void setStatusWhenRegistered(String statusWhenRegistered) {
        this.statusWhenRegistered = statusWhenRegistered;
    }

    public ItemOrCategory getFuelType() {
        return fuelType;
    }

    public void setFuelType(ItemOrCategory fuelType) {
        this.fuelType = fuelType;
    }

    public ItemOrCategory getTypeOfBody() {
        return typeOfBody;
    }

    public void setTypeOfBody(ItemOrCategory typeOfBody) {
        this.typeOfBody = typeOfBody;
    }

    public String getPreviousOwners() {
        return previousOwners;
    }

    public void setPreviousOwners(String previousOwners) {
        this.previousOwners = previousOwners;
    }

    public Integer getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(Integer seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public Double getWeightUnloaded() {
        return weightUnloaded;
    }

    public void setWeightUnloaded(Double weightUnloaded) {
        this.weightUnloaded = weightUnloaded;
    }

    public Double getWeightGross() {
        return weightGross;
    }

    public void setWeightGross(Double weightGross) {
        this.weightGross = weightGross;
    }

    public Double getTyreSizeFront() {
        return tyreSizeFront;
    }

    public void setTyreSizeFront(Double tyreSizeFront) {
        this.tyreSizeFront = tyreSizeFront;
    }

    public Double getTyreSizeRear() {
        return tyreSizeRear;
    }

    public void setTyreSizeRear(Double tyreSizeRear) {
        this.tyreSizeRear = tyreSizeRear;
    }

    public Boolean getDuelTyresFront() {
        return duelTyresFront;
    }

    public void setDuelTyresFront(Boolean duelTyresFront) {
        this.duelTyresFront = duelTyresFront;
    }

    public Boolean getDuelTyresRear() {
        return duelTyresRear;
    }

    public void setDuelTyresRear(Boolean duelTyresRear) {
        this.duelTyresRear = duelTyresRear;
    }

    public Double getLengthInMeters() {
        return lengthInMeters;
    }

    public void setLengthInMeters(Double lengthInMeters) {
        this.lengthInMeters = lengthInMeters;
    }

    public Double getWidthInMeters() {
        return widthInMeters;
    }

    public void setWidthInMeters(Double widthInMeters) {
        this.widthInMeters = widthInMeters;
    }

    public Double getHeightInCm() {
        return heightInCm;
    }

    public void setHeightInCm(Double heightInCm) {
        this.heightInCm = heightInCm;
    }

    public Date getDateOfFirstRegistration() {
        return dateOfFirstRegistration;
    }

    public void setDateOfFirstRegistration(Date dateOfFirstRegistration) {
        this.dateOfFirstRegistration = dateOfFirstRegistration;
    }

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }
    
    

}
