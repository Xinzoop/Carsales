/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * The Car Sales System maintains a catalogue of cars, which can be bought by customers and 
 * viewed/ edited by the Car Sales System users.
 * @author zipv5_000
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Car.FIND_ALL, query = "SELECT c FROM Car c"),
    @NamedQuery(name = Car.FIND_BY_VIN, query = "SELECT c FROM Car c WHERE c.VIN=:vin")})
public class Car implements Serializable {
    private static final long serialVersionUID = 1L;
    public final static String FIND_ALL = "Car.findAll";
    public final static String FIND_BY_VIN = "Car.findByVIN";
    /**
     * A car must be in one of the three categories, either Sedan, 4 wheel drive or Truck.
     */
    public enum CarType{ Sedan, FWD, Truck}
    
    /**
     * Vehicle Identification number VIN (A unique identifier of a particular car)
     */
    @Id    
    @NotNull(message = "Please enter VIN(Vehicle Identification Number).")
    @Pattern(regexp = "^[^\\WIOQioq]{17}$", message = "Must be 17 long and no 'i,o,q'.")
    private String VIN;
    /**
     * Model No(A unique identifier of the car)
     */
    @NotNull(message = "Please enter model number.")
    private String modelNo;
    /**
     * Model name
     */
    @NotNull(message = "Please enter model name.")
    private String modelName;
    /**
     * Make/ Manufacturer
     */
    @NotNull(message = "Please enter make or manufacturer.")
    private String make;
    /**
     * Type (Sedan, 4 wheel drive or Truck)
     */
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "car_type")
    private CarType type;
    /**
     * Thumbnail: image URL
     */
    private String thumbnail;
    /**
     * Description
     */
    @Column(length = 1024)
    private String description;
    /**
     * Preview URL
     */
    private String previewURL;
    /**
     * Price
     */
    @NotNull(message = "Please enter the price.")
    private double price;
    /**
     * Related sale records; Sale can be canceled.
     */
     @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private Set<Sale> sales;

    public double getPrice() {
        return price;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (VIN != null ? VIN.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the VIN fields are not set
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
        return "fit5042.carsale.entities.Car[ id=" + VIN + " ]";
    }
    
}
