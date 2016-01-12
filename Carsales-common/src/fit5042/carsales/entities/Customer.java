/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.entities;

import java.util.Set;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * Customer: can buy car apart from basic functions of user
 * @author zipv5_000
 */
@Entity
@DiscriminatorValue(value = "Customer")
public class Customer extends User {
    private static final long serialVersionUID = 1L;
    /**
     * A customer can have unlimited number of sales
     */
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Sale> purchases;

    public Set<Sale> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<Sale> purchases) {
        this.purchases = purchases;
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
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fit5042.carsale.entities.Customer[ id=" + id + " ]";
    }
    
}
