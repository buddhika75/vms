/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import vms.enums.CssFontStyle;
import vms.enums.CssFontWeight;
import vms.enums.CssUnit;

/**
 *
 * @author buddhika
 */
@Entity
public class Presentation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    ItemOrCategory itemOrCategory;
    int heightValue;
    CssUnit heightUnit;
    int widthValue;
    CssUnit widthUnit;
    int topValue;
    CssUnit topUnit;
    int leftValue;
    CssUnit leftUnit;
    int fontSizeValue;
    CssUnit fontSizeUnit;
    String fontFamily;
    CssFontStyle fontStyle;
    CssFontWeight fontWeight;
    int fontWeightValue;
    CssUnit fontWeightUnit;
    String colourValue;
    String backgroundColourValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemOrCategory getItemOrCategory() {
        return itemOrCategory;
    }

    public void setItemOrCategory(ItemOrCategory itemOrCategory) {
        this.itemOrCategory = itemOrCategory;
    }

    public int getHeightValue() {
        return heightValue;
    }

    public void setHeightValue(int heightValue) {
        this.heightValue = heightValue;
    }

    public CssUnit getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(CssUnit heightUnit) {
        this.heightUnit = heightUnit;
    }

    public int getWidthValue() {
        return widthValue;
    }

    public void setWidthValue(int widthValue) {
        this.widthValue = widthValue;
    }

    public CssUnit getWidthUnit() {
        return widthUnit;
    }

    public void setWidthUnit(CssUnit widthUnit) {
        this.widthUnit = widthUnit;
    }

    public int getTopValue() {
        return topValue;
    }

    public void setTopValue(int topValue) {
        this.topValue = topValue;
    }

    public CssUnit getTopUnit() {
        return topUnit;
    }

    public void setTopUnit(CssUnit topUnit) {
        this.topUnit = topUnit;
    }

    public int getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(int leftValue) {
        this.leftValue = leftValue;
    }

    public CssUnit getLeftUnit() {
        return leftUnit;
    }

    public void setLeftUnit(CssUnit leftUnit) {
        this.leftUnit = leftUnit;
    }

    public int getFontSizeValue() {
        return fontSizeValue;
    }

    public void setFontSizeValue(int fontSizeValue) {
        this.fontSizeValue = fontSizeValue;
    }

    public CssUnit getFontSizeUnit() {
        return fontSizeUnit;
    }

    public void setFontSizeUnit(CssUnit fontSizeUnit) {
        this.fontSizeUnit = fontSizeUnit;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public CssFontStyle getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(CssFontStyle fontStyle) {
        this.fontStyle = fontStyle;
    }

    public CssFontWeight getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(CssFontWeight fontWeight) {
        this.fontWeight = fontWeight;
    }

    public int getFontWeightValue() {
        return fontWeightValue;
    }

    public void setFontWeightValue(int fontWeightValue) {
        this.fontWeightValue = fontWeightValue;
    }

    public CssUnit getFontWeightUnit() {
        return fontWeightUnit;
    }

    public void setFontWeightUnit(CssUnit fontWeightUnit) {
        this.fontWeightUnit = fontWeightUnit;
    }

    public String getColourValue() {
        return colourValue;
    }

    public void setColourValue(String colourValue) {
        this.colourValue = colourValue;
    }

    public String getBackgroundColourValue() {
        return backgroundColourValue;
    }

    public void setBackgroundColourValue(String backgroundColourValue) {
        this.backgroundColourValue = backgroundColourValue;
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
        if (!(object instanceof Presentation)) {
            return false;
        }
        Presentation other = (Presentation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vms.entity.Presentation[ id=" + id + " ]";
    }
    
}
