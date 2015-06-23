/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vms.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Buddhika
 */
@Entity
public class ItemUnit implements Serializable {
    
    @OneToMany(mappedBy = "parent")
    private List<ItemUnit> itemUnits;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String name;
    String code;
    String barcode;
    String serialNo;
    @Lob
    String description;
    @ManyToOne
    DepartmentOrInstitution ownerDepartmentOrInstitution;
    @ManyToOne
    Person ownerPerson;
    Double quantity;
    @ManyToOne
    Item item;
    @ManyToOne
    ItemOrCategory make;
    @ManyToOne
    ItemOrCategory model;
    @ManyToOne
    ItemOrCategory countryOfOrigin;
    Integer yearOfManufacturer;
    @ManyToOne
    ItemOrCategory countryOfImport;
    @ManyToOne
    ItemOrCategory colour;

    @ManyToOne
    ItemUnit parent;

    public List<ItemUnit> getItemUnits() {
        if (itemUnits == null) {
            itemUnits = new ArrayList<ItemUnit>();
        }
        return itemUnits;
    }

    public void setItemUnits(List<ItemUnit> itemUnits) {
        this.itemUnits = itemUnits;
    }

    public ItemUnit getParent() {
        return parent;
    }

    public void setParent(ItemUnit parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DepartmentOrInstitution getOwnerDepartmentOrInstitution() {
        return ownerDepartmentOrInstitution;
    }

    public void setOwnerDepartmentOrInstitution(DepartmentOrInstitution ownerDepartmentOrInstitution) {
        this.ownerDepartmentOrInstitution = ownerDepartmentOrInstitution;
    }

    public Person getOwnerPerson() {
        return ownerPerson;
    }

    public void setOwnerPerson(Person ownerPerson) {
        this.ownerPerson = ownerPerson;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ItemOrCategory getMake() {
        return make;
    }

    public void setMake(ItemOrCategory make) {
        this.make = make;
    }

    public ItemOrCategory getModel() {
        return model;
    }

    public void setModel(ItemOrCategory model) {
        this.model = model;
    }

    public ItemOrCategory getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(ItemOrCategory countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public Integer getYearOfManufacturer() {
        return yearOfManufacturer;
    }

    public void setYearOfManufacturer(Integer yearOfManufacturer) {
        this.yearOfManufacturer = yearOfManufacturer;
    }

    public ItemOrCategory getCountryOfImport() {
        return countryOfImport;
    }

    public void setCountryOfImport(ItemOrCategory countryOfImport) {
        this.countryOfImport = countryOfImport;
    }

    public ItemOrCategory getColour() {
        return colour;
    }

    public void setColour(ItemOrCategory colour) {
        this.colour = colour;
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
        if (!(object instanceof ItemUnit)) {
            return false;
        }
        ItemUnit other = (ItemUnit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vms.entity.ItemUnit[ id=" + id + " ]";
    }

}
