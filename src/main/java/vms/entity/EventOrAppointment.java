/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import vms.enums.EventOrAppointmentType;

/**
 *
 * @author Buddhika
 */
@Entity
public class EventOrAppointment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date fromDate;
    Integer fromMilage;
    Integer toMilage;
    @ManyToOne
    DepartmentOrInstitution fromDepartmentOrInstitution;
    @ManyToOne
    DepartmentOrInstitution toDepartmentOrInstitution;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date toDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date thisDate;
    Integer thisMilage;
    String name;
    @Lob
    String description;
    @OneToOne
    EventOrAppointment event;
    @OneToOne(mappedBy = "event")
    EventOrAppointment appointment;
    @ManyToOne
    WebUser createdBy;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date createAt;
    @Lob
    String createrComments;
    Boolean cancelled;
    Boolean completed;
    @ManyToOne
    WebUser cancelledBy;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date cancelledAt;
    @Lob
    String calcellerComments;
    Integer dailyFrequency;
    Integer monthlyFrequency;
    Integer yearlyFrequency;
    Integer milageFrequency;
    
    @ManyToOne
    ItemOrCategory category;
    @Enumerated(EnumType.STRING)
    EventOrAppointmentType type;
    
    @ManyToOne
    ItemOrCategory forItem;
    @ManyToOne
    ItemOrCategory forCategory;
    @ManyToOne
    ItemUnit forItemUnit;
    
    Double doubleValue1;
    Double doubleValue2;
    Integer intValue1;
    Integer intValue2;
    
    String stringValue1;
    String stringValue2;
    String stringValue3;
    

    public Integer getThisMilage() {
        return thisMilage;
    }

    public void setThisMilage(Integer thisMilage) {
        this.thisMilage = thisMilage;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventOrAppointmentType getType() {
        return type;
    }

    public void setType(EventOrAppointmentType type) {
        this.type = type;
    }

    
    
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

    public Integer getToMilage() {
        return toMilage;
    }

    public void setToMilage(Integer toMilage) {
        this.toMilage = toMilage;
    }

    public DepartmentOrInstitution getFromDepartmentOrInstitution() {
        return fromDepartmentOrInstitution;
    }

    public void setFromDepartmentOrInstitution(DepartmentOrInstitution fromDepartmentOrInstitution) {
        this.fromDepartmentOrInstitution = fromDepartmentOrInstitution;
    }

    public DepartmentOrInstitution getToDepartmentOrInstitution() {
        return toDepartmentOrInstitution;
    }

    public void setToDepartmentOrInstitution(DepartmentOrInstitution toDepartmentOrInstitution) {
        this.toDepartmentOrInstitution = toDepartmentOrInstitution;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getThisDate() {
        return thisDate;
    }

    public void setThisDate(Date thisDate) {
        this.thisDate = thisDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventOrAppointment getEvent() {
        return event;
    }

    public void setEvent(EventOrAppointment event) {
        this.event = event;
    }

    public EventOrAppointment getAppointment() {
        return appointment;
    }

    public void setAppointment(EventOrAppointment appointment) {
        this.appointment = appointment;
    }

    public WebUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(WebUser createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreaterComments() {
        return createrComments;
    }

    public void setCreaterComments(String createrComments) {
        this.createrComments = createrComments;
    }

    public WebUser getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(WebUser cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public Date getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(Date cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getCalcellerComments() {
        return calcellerComments;
    }

    public void setCalcellerComments(String calcellerComments) {
        this.calcellerComments = calcellerComments;
    }

    public Integer getDailyFrequency() {
        return dailyFrequency;
    }

    public void setDailyFrequency(Integer dailyFrequency) {
        this.dailyFrequency = dailyFrequency;
    }

    public Integer getMonthlyFrequency() {
        return monthlyFrequency;
    }

    public void setMonthlyFrequency(Integer monthlyFrequency) {
        this.monthlyFrequency = monthlyFrequency;
    }

    public Integer getYearlyFrequency() {
        return yearlyFrequency;
    }

    public void setYearlyFrequency(Integer yearlyFrequency) {
        this.yearlyFrequency = yearlyFrequency;
    }

    public Integer getMilageFrequency() {
        return milageFrequency;
    }

    public void setMilageFrequency(Integer milageFrequency) {
        this.milageFrequency = milageFrequency;
    }

    public ItemOrCategory getCategory() {
        return category;
    }

    public void setCategory(ItemOrCategory category) {
        this.category = category;
    }

    public ItemOrCategory getForItem() {
        return forItem;
    }

    public void setForItem(ItemOrCategory forItem) {
        this.forItem = forItem;
    }

    public ItemOrCategory getForCategory() {
        return forCategory;
    }

    public void setForCategory(ItemOrCategory forCategory) {
        this.forCategory = forCategory;
    }

    public ItemUnit getForItemUnit() {
        return forItemUnit;
    }

    public void setForItemUnit(ItemUnit forItemUnit) {
        this.forItemUnit = forItemUnit;
    }

    public Double getDoubleValue1() {
        return doubleValue1;
    }

    public void setDoubleValue1(Double doubleValue1) {
        this.doubleValue1 = doubleValue1;
    }

    public Double getDoubleValue2() {
        return doubleValue2;
    }

    public void setDoubleValue2(Double doubleValue2) {
        this.doubleValue2 = doubleValue2;
    }

    public Integer getIntValue1() {
        return intValue1;
    }

    public void setIntValue1(Integer intValue1) {
        this.intValue1 = intValue1;
    }

    public Integer getIntValue2() {
        return intValue2;
    }

    public void setIntValue2(Integer intValue2) {
        this.intValue2 = intValue2;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getStringValue1() {
        return stringValue1;
    }

    public void setStringValue1(String stringValue1) {
        this.stringValue1 = stringValue1;
    }

    public String getStringValue2() {
        return stringValue2;
    }

    public void setStringValue2(String stringValue2) {
        this.stringValue2 = stringValue2;
    }

    public String getStringValue3() {
        return stringValue3;
    }

    public void setStringValue3(String stringValue3) {
        this.stringValue3 = stringValue3;
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
        if (!(object instanceof EventOrAppointment)) {
            return false;
        }
        EventOrAppointment other = (EventOrAppointment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vms.entity.EventOrAppointment[ id=" + id + " ]";
    }
    
}
