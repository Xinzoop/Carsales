/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales;

import fit5042.carsales.entities.Car;
import fit5042.carsales.gui.CarCatalogGUIImpl;
import fit5042.carsales.sessionbeans.CarManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author zipv5_000
 */
public class CarsalesApp implements ActionListener {

    @EJB
    private static CarManager cm;
    private CarCatalogGUIImpl gui;

    private void initView() {
        gui = new CarCatalogGUIImpl(this);

    }

    public static void main(String[] args) {
        CarsalesApp app = new CarsalesApp();
        app.initView();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == gui.getSearchButton()) {
            searchCars();
        } else {
            System.exit(1);
        }
    }

    private void searchCars() {
        try {
            String make = gui.getMake();
            String modelName = gui.getModelName();
            String modelNo = gui.getModelNo();
            Car.CarType type = gui.getCarType();
            List<Car>  cars = cm.findCarsByCriteria(make, modelName, modelNo, type);
            //System.out.println("make:"+make + ",modelName:"+ modelName + ",modelNo:" + modelNo + ",type:" + type + ",CARS:" + cars.size());
            gui.showCars(cars);
        } catch (Exception ex) {
            Logger.getLogger(CarsalesApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
