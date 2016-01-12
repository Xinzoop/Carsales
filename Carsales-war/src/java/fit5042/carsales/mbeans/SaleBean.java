/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.mbeans;

import fit5042.carsales.entities.Car;
import fit5042.carsales.entities.Customer;
import fit5042.carsales.entities.Sale;
import fit5042.carsales.entities.Salesperson;
import fit5042.carsales.entities.User;
import fit5042.carsales.sessionbeans.CarManager;
import fit5042.carsales.sessionbeans.SaleManager;
import fit5042.carsales.sessionbeans.UserManager;
import fit5042.carsales.web.navigation.Navigator;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author zipv5_000
 */
@ManagedBean(name = "saleBean")
@ViewScoped
public class SaleBean implements Serializable {

    private Long spid;

    /**
     * Creates a new instance of SaleBean
     */
    public SaleBean() {
        flag = false;
    }
    @EJB
    private SaleManager sm;
    private Sale sale;
    private String message;
    private boolean flag;
    @ManagedProperty(value = "#{loginBean.user}")
    private User user;
    private Salesperson sp;
    @EJB
    private CarManager cm;
    @EJB
    private UserManager um;
    private Car car;
    /**
     * Available salespersons. Customer must select a salesperson to make an
     * order.
     */
    private List<Salesperson> salespersons;
    /**
     * Current selected salesperson
     */
    private Long selectedSalespersonId;

    public CarManager getCm() {
        return cm;
    }

    public void setCm(CarManager cm) {
        this.cm = cm;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public UserManager getUm() {
        return um;
    }

    public void setUm(UserManager um) {
        this.um = um;
    }

    public List<Salesperson> getSalespersons() {
        return salespersons;
    }

    public void setSalespersons(List<Salesperson> salespersons) {
        this.salespersons = salespersons;
    }

    public Long getSelectedSalespersonId() {
        return selectedSalespersonId;
    }

    public void setSelectedSalespersonId(Long selectedSalespersonId) {
        this.selectedSalespersonId = selectedSalespersonId;
    }

    public Long getSpid() {
        return spid;
    }

    public void setSpid(Long spid) {
        this.spid = spid;
    }

    public SaleManager getSm() {
        return sm;
    }

    public void setSm(SaleManager sm) {
        this.sm = sm;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Salesperson getSp() {
        return sp;
    }

    public void setSp(Salesperson sp) {
        this.sp = sp;
    }

    public String buy() {
        message = "";
        flag = true;
        try {
            if (!(user instanceof Customer)) {
                throw new Exception("Only customers can buy cars.");
            }
            if (null == car) {
                throw new Exception("Unable to retrieve car entity.");
            }
            
            if(!sm.checkCustomerPayment(user.getId())){
                message = "Sorry, you have unpaid orders.";
                flag = false;
                return "";
            }
            
            if(!cm.checkStorage(car.getVIN())){
                message = "Sorry, the car has just sold out.";
                flag = false;
                return "";
            }

            sale.setCar(car);
            sale.setSalesperson(findSalesperson(selectedSalespersonId));
            sale.setCustomer((Customer)user);
            sm.buy(sale);
            message = "Congratulations! Our salsperson will contact you soon.";
            return Navigator.CAR_LIST;
        } catch (Exception ex) {
            Logger.getLogger(SaleBean.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
            flag = false;
            return "";
        }
    }
    
    public String confirmPayment(){
        message = "";
        flag = true;
        try {
            sm.confirmPayment(sale.getId());
            return Navigator.SALES_LIST;
        } catch (Exception ex) {
            Logger.getLogger(SaleBean.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
            flag = false;
            return "";
        }
    }
    /**
     * Salesperson can withdraw sale record
     */
    public String withdrawSale(){
        message = "";
        flag = true;
        try {
            sm.removeSale(sale.getId());
            return Navigator.SALES_LIST;
        } catch (Exception ex) {
            Logger.getLogger(SaleBean.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
            flag = false;
            return "";
        }
    }

    @PostConstruct
    public void init() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            String sid = context.getExternalContext().getRequestParameterMap().get("sid");
            if (null == sid || sid.isEmpty()) {
                sale = new Sale();
                // Require salespersons for new sales
                salespersons = um.findAllSalesperson();
                if (salespersons.size() > 0) {
                    selectedSalespersonId = salespersons.get(0).getId();
                }
            } else {
                sale = sm.findSaleById(Long.valueOf(sid));
            }

            String vin = context.getExternalContext().getRequestParameterMap().get("vin");
            if (null != vin && !vin.isEmpty()) {
                car = cm.findCarByVIN(vin);
            }
        } catch (Exception ex) {
            Logger.getLogger(SaleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get selected salesperson
     *
     * @param id
     * @return
     */
    private Salesperson findSalesperson(Long id) {
        for (Salesperson sp : salespersons) {
            if (Objects.equals(sp.getId(), id)) {
                return sp;
            }
        }
        return null;
    }
}
