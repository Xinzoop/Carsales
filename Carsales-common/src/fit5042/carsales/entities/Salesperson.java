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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * Salesperson: can manage cars and users apart from basic functions of users
 * @author zipv5_000
 */
@Entity
@DiscriminatorValue(value = "Salesperson")
@NamedQueries(@NamedQuery(name = Salesperson.FIND_ALL, query = "SELECT p FROM Salesperson p"))
public class Salesperson extends User{
    private static final long serialVersionUID = 1L;
    public final static String FIND_ALL = "Salesperson.findAll";
        
    /**
     * A customer can have unlimited number of sales
     */
    @OneToMany(mappedBy = "salesperson", fetch = FetchType.LAZY)
    private Set<Sale> sales;

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
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
        if (!(object instanceof Salesperson)) {
            return false;
        }
        Salesperson other = (Salesperson) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fit5042.carsale.entities.Salesperson[ id=" + id + " ]";
    }
    
}
