/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.gui;

import fit5042.carsales.entities.Car;
import java.util.List;
import javax.swing.JButton;

/**
 *
 * @author zipv5_000
 */
public interface CarsalesGUI {
    String getMake() throws Exception;
    String getModelName() throws Exception;
    String getModelNo() throws Exception;
    Car.CarType getCarType() throws Exception;
    
    JButton getSearchButton();
    JButton getCloseButton();
    
    void showCars(List<Car> carList);
    void showMessage(String msg);
}
