/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zipv5_000
 */
@Entity
@Table(name = "CAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Car.findAll", query = "SELECT c FROM Car c")})
public class Car implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "VIN")
    private String VIN;
    @Size(max = 1024)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 255)
    @Column(name = "MAKE")
    private String make;
    @Size(max = 255)
    @Column(name = "MODELNAME")
    private String modelName;
    @Size(max = 255)
    @Column(name = "MODELNO")
    private String modelNo;
    @Size(max = 255)
    @Column(name = "PREVIEWURL")
    private String previewURL;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRICE")
    private Double price;
    @Size(max = 255)
    @Column(name = "THUMBNAIL")
    private String thumbnail;
    @Size(max = 255)
    @Column(name = "CAR_TYPE")
    private String type;

    public Car() {
    }

    public Car(String vin) {
        this.VIN = vin;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String vin) {
        this.VIN = vin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (VIN != null ? VIN.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Car)) {
            return false;
        }
        Car other = (Car) object;
        if ((this.VIN == null && other.VIN != null) || (this.VIN != null && !this.VIN.equals(other.VIN))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fit5042.carsales.Car[ vin=" + VIN + " ]";
    }
    
}
