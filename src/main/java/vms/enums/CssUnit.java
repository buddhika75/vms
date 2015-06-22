/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.enums;

/**
 *
 * @author User
 */
public enum CssUnit {
    em, 
    ex,
    percent,
    px,
    cm,
    mm,
    in,
    pt,
    pc;
    
    public String getLabel(){
        if(this==percent){
            return "%";
        }else{
            return this.toString();
        }
    }
    
}
