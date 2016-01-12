/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.mbeans;

import fit5042.carsales.entities.Customer;
import fit5042.carsales.entities.Sale;
import fit5042.carsales.entities.Salesperson;
import fit5042.carsales.entities.User;
import fit5042.carsales.sessionbeans.SaleManager;
import fit5042.carsales.sessionbeans.UserManager;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Used to keep sale list data temporarily
 * @author zipv5_000
 */
@ManagedBean(name = "saleListBean")
@ViewScoped
public class SaleListBean implements Serializable{

    /**
     * Creates a new instance of SaleListBean
     */
    public SaleListBean() {
    }
    /**
     * Sale list of users can be retrieved lazily
     */
    @EJB
    private SaleManager sm;
    private List<Sale> sales;
    @ManagedProperty(value = "#{loginBean.user}")
    private User user;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SaleManager getSm() {
        return sm;
    }

    public void setSm(SaleManager sm) {
        this.sm = sm;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Sale.SaleStatus[] getSaleStatuses(){
        return Sale.SaleStatus.values();
    }

    @PostConstruct
    public void init() {
        try {
            sales = sm.retrieveSalesRecord(user.getId());
            message = "";
        } catch (Exception ex) {
            Logger.getLogger(SaleListBean.class.getName()).log(Level.SEVERE, null, ex);
            message = "Retrieve sales failed. Details: " + ex.getMessage();
        }
    }
}
