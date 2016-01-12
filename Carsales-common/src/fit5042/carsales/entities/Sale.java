/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Sale: customers make purchases of cars
 * @author zipv5_000
 */
@Entity
public class Sale implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * ID number of sale
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * The customer who bought the car
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    /**
     * Salesperson
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "salesperson_id")
    private Salesperson salesperson;
    /**
     * Date when the sale is made
     */
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date buyAt;
    
    /**
     * Mark the status of one sale
     */
    public enum SaleStatus {Inprogress, Completed};    
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "sale_status")
    private SaleStatus saleStatus = SaleStatus.Inprogress;
    
    /**
     * Date of confirmation, either be paid or canceled;
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmAt;
    /**
     * The car that the sale include
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "VIN")
    private Car car;

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Date getConfirmAt() {
        return confirmAt;
    }

    public void setConfirmAt(Date confirmAt) {
        this.confirmAt = confirmAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Salesperson getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(Salesperson salesperson) {
        this.salesperson = salesperson;
    }

    public Date getBuyAt() {
        return buyAt;
    }

    public void setBuyAt(Date buyAt) {
        this.buyAt = buyAt;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
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
        if (!(object instanceof Sale)) {
            return false;
        }
        Sale other = (Sale) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fit5042.carsale.entities.Sale[ id=" + id + " ]";
    }
}
