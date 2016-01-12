/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.mbeans;

import fit5042.carsales.entities.Car;
import fit5042.carsales.entities.Customer;
import fit5042.carsales.entities.User;
import fit5042.carsales.sessionbeans.CarManager;
import fit5042.carsales.web.navigation.Navigator;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author zipv5_000
 */
@ManagedBean(name = "carListBean")
@SessionScoped
public class CarListBean implements Serializable{

    /**
     * Search Criteria
     */
    private String make;
    private String modelName;
    private String modelNo;
    private Car.CarType type;

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

    public Car.CarType getType() {
        return type;
    }

    public void setType(Car.CarType type) {
        this.type = type;
    }
    
    public Car.CarType[] getCarTypes(){
        return Car.CarType.values();
    }

    /**
     * Creates a new instance of CarListBean
     */
    public CarListBean() {
    }
    @EJB
    private CarManager cm;
    private List<Car> cars;    
    @ManagedProperty(value = "#{loginBean.user}")
    private User loginUser;

    public CarManager getCm() {
        return cm;
    }

    public void setCm(CarManager cm) {
        this.cm = cm;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
    /**
     * Acquire cars based on login user: 
     * customer can only see available cars
     * salesperson can see all cars, even been sold
     */
    public void searchCars() {
        flag = true;
        message ="";
        try {
            if (loginUser instanceof Customer) {
                cars = cm.findAvailableCarsByCriteria(make, modelName, modelNo, type);
            } else {
                cars = cm.findCarsByCriteria(make, modelName, modelNo, type);
            }
        } catch (Exception ex) {
            Logger.getLogger(CarListBean.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
            message = "Acquiring cars failed. Details: " + ex.getMessage();
        }
    }
    /**
     * Current select car
     */
    private Car car;       
    /**
     * Operation status
     */
    private String message;
    private boolean flag;

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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    /**
     * Show car details
     * @param car
     * @return 
     */
    public String showCar(Car car){
        this.car = car;
        return Navigator.CAR_DETAILS;
    }
    /**
     * Start create new car record
     * @return 
     */
    public String newCar(){
        car = new Car();
        return Navigator.NEW_CAR;
    }
    /**
     * Acquire car info from web services
     */
    public void findinAPI(){
        message = "";
        flag = true;
        try {
            Car c = cm.populateCarViaService(car.getVIN());
            if(null == c){
                message = "No matched car found in API.";
                flag = false;
                return;
            }
            car = c;
        } catch (Exception ex) {
            Logger.getLogger(CarListBean.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
            flag = false;
        }
    }
    /**
     * Insert new car record
     * @return 
     */
    public String addCar(){
        message = "";
        flag = true;
        try {
            cm.addCar(car);
            return Navigator.CAR_DETAILS;
        } catch (Exception ex) {
            Logger.getLogger(CarListBean.class.getName()).log(Level.SEVERE, null, ex);
            message = "Add car failed. Details: " + ex.getMessage();
            flag = false;
            return "";
        }
    }
    /**
     * Check whether the details of a car can be modified or removed.
     * Sold cars cannot be changed or removed.
     * @return 
     */
    public boolean isChangableorRemovable(){
        try {
            flag = true;
            message = "";
            return cm.checkStorage(car.getVIN());
        } catch (Exception ex) {
            Logger.getLogger(CarListBean.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
            message = "Checking storage failed. Details: " + ex.getMessage();
            return false;
        }
    }
    
    /**
     * Update car details
     * @return 
     */
    public String updateCar(){
        message = "";
        flag = true;
        try {           
            if(!isChangableorRemovable()){
                flag = false;
                message = "Car has related sales, cannot be modified";
                return "";
            }
            cm.updateCar(car);
            return Navigator.CAR_DETAILS;
        } catch (Exception ex) {
            Logger.getLogger(CarListBean.class.getName()).log(Level.SEVERE, null, ex);
            message = "Update car failed. Details: " + ex.getMessage();
            flag = false;
            return "";
        }
    }
    /**
     * Remove car from catalog
     * @return 
     */
    public String removeCar(){
        message = "";
        flag = true;
        try {
            if(!isChangableorRemovable()){
                flag = false;
                message = "Car has related sales, cannot be removed.";
                return "";
            }
            cm.removeCar(car.getVIN());
            cars.remove(car);
            return Navigator.CAR_LIST;
        } catch (Exception ex) {
            Logger.getLogger(CarListBean.class.getName()).log(Level.SEVERE, null, ex);
            message = "Remove car failed. Details: " + ex.getMessage();
            flag = false;
            return "";
        }
    }
    
    /**
     * Return car details
     * @return 
     */
    public String cancelEdit(){
        message ="";
        flag =true;
        return Navigator.CAR_DETAILS;
    }
    /**
     * Return car list
     * @return 
     */
    public String cancelNew(){
        message ="";
        flag =true;
        return Navigator.CAR_LIST;
    }
}
