/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.sessionbeans;

import fit5042.carsales.entities.Car;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author zipv5_000
 */
@Remote
public interface CarManager {
    
    /**
     * Find car with given VIN
     * @param VIN
     * @return
     * @throws Exception 
     */
    Car findCarByVIN(String VIN) throws Exception;
    
     /**
     * Search for all cars in the catalogue by the following combination of criteria:
     * o Model No
     * o Model Name
     * o Make/ Manufacturer
     * o Type
     * @return matched cars
     */
    List<Car> findCarsByCriteria(String make, String modelName, String modelNo, Car.CarType type) throws Exception;
     
    /**
     * Search for cars in the catalogue which is NOT SOLD OUT
     * o Model No
     * o Model Name
     * o Make/ Manufacturer
     * o Type
     * @return matched cars
     */
    List<Car> findAvailableCarsByCriteria(String make, String modelName, String modelNo, Car.CarType type) throws Exception;
    
    /**
     * A customer can only buy car(s) that have at least one in stock.
     * @param VIN
     * @return 
     */
    boolean checkStorage(String VIN) throws Exception;
    
    /**
     * Add an item to the catalogue
     */
    void addCar(Car car) throws Exception;
    
    /**
     * When adding a car to the system, the information of the car can be obtained via web
     * service instead of being entered manually
     * @param VIN
     * @return 
     */
    Car populateCarViaService(String VIN) throws Exception;
    
    /**
     * update the details of an item in the catalogue;
     * @param car 
     */
    void updateCar(Car car) throws Exception;
    /**
     * Delete an item from the catalogue;
     * @param VIN 
     */
    void removeCar(String VIN) throws Exception;
}
