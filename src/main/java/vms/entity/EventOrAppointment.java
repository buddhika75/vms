/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

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
    @Temporal(javax.persistence.TemporalType.DATE)
    Date fromDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date toDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date thisDate;
    @OneToOne
    EventOrAppointment event;
    @OneToOne(mappedBy = "event")
    EventOrAppointment appointment;
    @ManyToOne
    WebUser createdBy;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date createAt;
    @Lob
    String createrComments;
    @ManyToOne
    WebUser cancelledBy;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date cancelledAt;
    @Lob
    String calcellerComments;
    
    

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
