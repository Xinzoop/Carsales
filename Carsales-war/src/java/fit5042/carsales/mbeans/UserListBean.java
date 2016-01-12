/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.mbeans;

import fit5042.carsales.entities.Admin;
import fit5042.carsales.entities.Customer;
import fit5042.carsales.entities.Sale;
import fit5042.carsales.entities.Salesperson;
import fit5042.carsales.entities.User;
import fit5042.carsales.sessionbeans.SaleManager;
import fit5042.carsales.sessionbeans.UserManager;
import fit5042.carsales.web.navigation.Navigator;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * User management related data bean
 * @author zipv5_000
 */
@ManagedBean(name = "userListBean")
@SessionScoped
public class UserListBean implements Serializable {
    
    /**
     * Search Criteria
     */
    private Long id;
    private String fname;
    private String lname;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Creates a new instance of SalespersonListBean
     */
    public UserListBean() {
    }

    @EJB
    private UserManager um;
    @EJB
    private SaleManager sm;
    /**
     * Current all user list
     */
    private List<User> users;
    @ManagedProperty(value = "#{loginBean.user}")
    private User loginUser;
    /**
     * Current selected user
     */
    private User user;   
    /**
     * Keep operation status
     */
    private String message;
    private boolean flag;
    /**
     * Retain selected customer purchase records
     */
    private List<Sale> sales;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
    
    public UserManager getUm() {
        return um;
    }

    public void setUm(UserManager um) {
        this.um = um;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
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
    
    public String getTitle(){
        if(loginUser instanceof Admin)
            return "Salesperson List";
        return "Customer List";
    }
    /**
     * Acquire user list based on current login user type
     */
    public void findUsers(){
        try {
            if(loginUser instanceof Admin)
                users = um.findUsers(User.UserGroup.Salesperson, id, fname, lname, email);
            else if(loginUser instanceof Salesperson)
                users = um.findUsers(User.UserGroup.Customer, id, fname, lname, email);
        } catch (Exception ex) {
            Logger.getLogger(UserListBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Acquire customer purchase records
     */
    public void retrievePurchase(){
        try {
            flag = true;
            message = "";
            sales = sm.retrieveSalesRecord(user.getId());
        } catch (Exception ex) {
            Logger.getLogger(UserListBean.class.getName()).log(Level.SEVERE, null, ex);            
            flag = false;
            message = "Check user sales failed. Details: " + ex.getMessage();
        }
    }
    /**
     * Start create user
     * @return 
     */
    public String newUser(){
        if(loginUser instanceof Admin)
            user = new Salesperson();
        else
            user = new Customer();
        return Navigator.NEW_USER;
    }
    /**
     * Show user details
     * @param user
     * @return 
     */
    public String showUser(User user){
        this.user = user;
        if(null != sales)
            sales.clear();
        return Navigator.USER_DETAILS;
    }
    /**
     * Register new User
     * @return 
     */
    public String register() {
        message = "";
        flag = true;
        try {
            um.register(user);
            return Navigator.USER_LIST;
        } catch (Exception ex) {
            Logger.getLogger(UserListBean.class.getName()).log(Level.SEVERE, null, ex);
            message = "Add user failed. Details: " + ex.getMessage();
            flag = false;
            return "";
        }
    }

    /**
     * Update user to database
     * @return 
     */
    public String update() {
        message = "";
        flag = true;
        try {
            um.updateUser(user);
            return Navigator.USER_DETAILS;
        } catch (Exception ex) {
            Logger.getLogger(UserListBean.class.getName()).log(Level.SEVERE, null, ex);
            message = "Update user details failed. Details: " + ex.getMessage();
            flag = false;
            return "";
        }
    }

    /**
     * If user can be removed. User cannot be removed if there is related sales.
     * @return 
     */
    public boolean  removable(){
        try {            
            flag = true;
            message = "";
            return sm.retrieveSalesRecord(user.getId()).size() <= 0;
        } catch (Exception ex) {
            Logger.getLogger(UserListBean.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
            message = "Check user sales failed. Details: " + ex.getMessage();
            return false;
        }
    }
    
    /**
     * Remove user from database
     * @return 
     */
    public String remove() {
        message = "";
        flag = true;
        try {
            if(!removable()){
                flag = false;
                message = "User has related sales, cannot be removed";
                return "";
            }
            um.removeUser(user.getId());
            users.remove(user);
            return Navigator.USER_LIST;
        } catch (Exception ex) {
            Logger.getLogger(UserListBean.class.getName()).log(Level.SEVERE, null, ex);
            message = "Delete user failed. Details: " + ex.getMessage();
            flag = false;
            return "";
        }
    }
    /**
     * Return user details
     * @return 
     */
    public String cancelEdit(){
        message ="";
        flag =true;
        return Navigator.USER_DETAILS;
    }
    /**
     * Return user list
     * @return 
     */
    public String cancelNew(){
        message ="";
        flag =true;
        return Navigator.USER_LIST;
    }
}
