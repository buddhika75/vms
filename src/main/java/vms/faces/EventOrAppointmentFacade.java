/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.faces;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import vms.entity.EventOrAppointment;

/**
 *
 * @author Buddhika
 */
@Stateless
public class EventOrAppointmentFacade extends AbstractFacade<EventOrAppointment> {
    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventOrAppointmentFacade() {
        super(EventOrAppointment.class);
    }
    
}
