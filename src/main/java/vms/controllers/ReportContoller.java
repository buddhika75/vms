/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;

import javax.persistence.TemporalType;
import javax.xml.crypto.Data;
import vms.entity.EventOrAppointment;
import vms.entity.Vehicle;
import vms.enums.EventOrAppointmentType;
import vms.faces.EventOrAppointmentFacade;

/**
 *
 * @author buddhika
 */
@Named(value = "reportContoller")
@SessionScoped
public class ReportContoller implements Serializable {

    /**
     * Creates a new instance of ReportContoller
     */
    Date fromDate;
    Date toDate;
    Vehicle vehicle;
    @EJB
    EventOrAppointmentFacade eventOrAppointmentFacde;
    List<EventOrAppointment> eventOrAppointmentList;

    public ReportContoller() {
    }

    public Date getFromDate() {
        if (fromDate == null) {
            fromDate = new Date();
        }
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        if (toDate == null) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 14);
            toDate = c.getTime();
        }
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public EventOrAppointmentFacade getEventOrAppointmentFacde() {
        return eventOrAppointmentFacde;
    }

    public void setEventOrAppointmentFacde(EventOrAppointmentFacade eventOrAppointmentFacde) {
        this.eventOrAppointmentFacde = eventOrAppointmentFacde;
    }

    public List<EventOrAppointment> getEventOrAppointmentList() {
        return eventOrAppointmentList;
    }

    public void setEventOrAppointmentList(List<EventOrAppointment> eventOrAppointmentList) {
        this.eventOrAppointmentList = eventOrAppointmentList;
    }

    public void createSchedule() {
        eventOrAppointmentList = findEventOrAppointmentList(EventOrAppointmentType.ItemUnitScheduleManual);
    }

    public void createRunningChart() {
        eventOrAppointmentList = findEventOrAppointmentList(EventOrAppointmentType.RunnigChartEntry);
    }

    public void createEvent() {
        eventOrAppointmentList = findEventOrAppointmentList(EventOrAppointmentType.ItemUnitEventManual);
    }
    
    

    public List<EventOrAppointment> findEventOrAppointmentList(EventOrAppointmentType eventOrAppointmentType) {
        String sql;
        Map m = new HashMap();
        
        sql = "select s from EventOrAppointment s "
                //                + "where s.retired=false "
                + " where s.thisDate between :fd and :td "
                + " and s.type=:type ";

        m.put("fd", fromDate);
        m.put("td", toDate);
        m.put("type", eventOrAppointmentType);

        if (vehicle != null) {
            sql += " and s.forItemUnit=:veh ";
            m.put("veh", vehicle);
        }
System.out.println("m = " + m);
        System.out.println("sql = " + sql);
        return eventOrAppointmentFacde.findBySQL(sql, m, TemporalType.TIMESTAMP);

    }

//    private List<EventOrAppointment> findEventOrAppointmentList(EventOrAppointmentType eventOrAppointmentType) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

}
