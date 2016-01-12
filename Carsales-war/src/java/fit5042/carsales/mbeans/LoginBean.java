/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.mbeans;

import fit5042.carsales.entities.Admin;
import fit5042.carsales.entities.Customer;
import fit5042.carsales.entities.Salesperson;
import fit5042.carsales.entities.User;
import fit5042.carsales.sessionbeans.UserManager;
import fit5042.carsales.web.navigation.Navigator;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author zipv5_000
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }

    @EJB
    private UserManager um;
    private User user;
    private boolean flag;
    private String message;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserManager getUm() {
        return um;
    }

    public void setUm(UserManager um) {
        this.um = um;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public User.UserGroup getUserGroup(){
        if(null == user)
            return User.UserGroup.Unknown;
        if(user instanceof Customer)
            return User.UserGroup.Customer;
        if(user instanceof Admin)
            return User.UserGroup.Admin;
        if(user instanceof Salesperson)
            return User.UserGroup.Salesperson;
        return User.UserGroup.Unknown;
    }
    
    public String login(String email, String password){
        flag = true;
        message = "";
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        try {
            request.login(email, password);
            user = um.findUserByEmail(email);
            
            // Redirect based on user group
            if(getUserGroup() == User.UserGroup.Admin)
                return Navigator.USER_LIST;
            
            if(getUserGroup() == User.UserGroup.Salesperson)
                return Navigator.SALES_LIST;
            
            if(getUserGroup() == User.UserGroup.Customer)
                return Navigator.CAR_LIST;
            
            flag = false;
            message = "Unknown Role";
            return Navigator.INDEX;
        } catch (Exception ex) {
            message = ex.getMessage() + ".Please recheck your email and password.";
            flag = false;
            return Navigator.INDEX;
        }
    }
    public String logout(){        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        user = null;
        flag = true;
        message = "";
        return Navigator.INDEX;
    }
}
