/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.sessionbeans;

import fit5042.carsales.entities.Car;
import fit5042.carsales.entities.Sale;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Car catalog
 *
 * @author zipv5_000
 */
@Stateless
public class CarManagerBean implements CarManager {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Car findCarByVIN(String VIN) throws Exception {
        return em.find(Car.class, VIN);
    }

    /**
     * Search for all cars in the catalogue by the following combination of criteria:
     * @param make
     * @param modelName
     * @param modelNo
     * @param type
     * @return
     * @throws Exception 
     */
    @Override
    public List<Car> findCarsByCriteria(String make, String modelName, String modelNo, Car.CarType type) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Car.class);
        Root root = cq.from(Car.class);
        cq.where(
                cb.and(cb.equal(root.get("type"), type),
                        cb.and(cb.like(root.get("make"), "%" + make + "%"),
                                cb.and(cb.like(root.get("modelName"), "%" + modelName + "%"),
                                        cb.like(root.get("modelNo"), "%" + modelNo + "%")
                                        
                                        
                                )
                        )
                )
        );
        cq.select(root);
        return (List<Car>) em.createQuery(cq).getResultList();
    }

    @Override
    public boolean checkStorage(String VIN) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Car.class);
        Root root = cq.from(Car.class);
        Join sales = root.join("sales", JoinType.LEFT);
        cq.where(
                cb.and(cb.equal(root.get("VIN"), VIN),
                        cb.and(cb.or(cb.isNull(sales.get("saleStatus")),
                                        cb.and(
                                                cb.notEqual(sales.get("saleStatus"), Sale.SaleStatus.Inprogress),
                                                cb.notEqual(sales.get("saleStatus"), Sale.SaleStatus.Completed))
                                )
                        )
                )
        );
        cq.select(root);
        System.out.println("" + em.createQuery(cq).getResultList().size());
        return em.createQuery(cq).getResultList().size() == 1;
    }

    @Override
    public void addCar(Car car) throws Exception {
        Car c = findCarByVIN(car.getVIN());
        if (null != c) {
            throw new Exception("Car with VIN <" + car.getVIN() + "> already exists.");
        }
        em.persist(car);
    }

    @Override
    public Car populateCarViaService(String VIN) throws Exception {
        CarsalesServiceClient client = new CarsalesServiceClient();
        return  client.find(Car.class, VIN);
    }

    @Override
    public void updateCar(Car car) throws Exception {
        Car c = findCarByVIN(car.getVIN());
        if (null == c) {
            throw new Exception("Car with VIN <" + car.getVIN() + "> does not exists.");
        }
        em.merge(car);
    }

    @Override
    public void removeCar(String VIN) throws Exception {
        Car c = findCarByVIN(VIN);
        if (null == c) {
            throw new Exception("Car with VIN <" + VIN + "> does not exist.");
        }
        em.remove(c);
    }

    /**
     * Search for cars in the catalogue which is NOT SOLD OUT
     * @param make
     * @param modelName
     * @param modelNo
     * @param type
     * @return
     * @throws Exception 
     */
    @Override
    public List<Car> findAvailableCarsByCriteria(String make, String modelName, String modelNo, Car.CarType type) throws Exception {
         CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Car.class);
        Root root = cq.from(Car.class);
        // Left join sale, check car storage status
        Join sales = root.join("sales", JoinType.LEFT);
        cq.where(
                cb.and(cb.equal(root.get("type"), type),
                        cb.and(cb.like(root.get("make"), "%" + make + "%"),
                                cb.and(cb.like(root.get("modelName"), "%" + modelName + "%"),
                                        cb.and(cb.like(root.get("modelNo"), "%" + modelNo + "%"),
                                                cb.and(cb.or(cb.isNull(sales.get("saleStatus")),
                                                                cb.and(
                                                                        cb.notEqual(sales.get("saleStatus"), Sale.SaleStatus.Inprogress),
                                                                        cb.notEqual(sales.get("saleStatus"), Sale.SaleStatus.Completed))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        cq.select(root);
        return (List<Car>) em.createQuery(cq).getResultList();
    }
  
    static class CarsalesServiceClient {

        private WebTarget webTarget;
        private Client client;
        private static final String BASE_URI = "http://localhost:8080/Carsales-svc/APIs";

        public CarsalesServiceClient() {
            client = javax.ws.rs.client.ClientBuilder.newClient();
            webTarget = client.target(BASE_URI).path("Car");
        }

        public String getServiceDescription() throws ClientErrorException {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
        }

        public <T> T find(Class<T> responseType, String VIN) throws ClientErrorException {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{VIN}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        }

        public void close() {
            client.close();
        }
    }
}
