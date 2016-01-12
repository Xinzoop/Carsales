/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.sessionbeans;

import fit5042.carsales.entities.Car;
import fit5042.carsales.entities.Sale;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Sale record related operations
 * @author zipv5_000
 */
@Stateless
public class SaleManagerBean implements SaleManager {

    @PersistenceContext
    private EntityManager em;
    
    /**
     * A customer cannot buy another car if he/she has not paid off all
     * purchases on their account
     *
     * @param id:the user to check
     * @return
     */
    @Override
    public boolean checkCustomerPayment(Long id) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Sale.class);
        Root root = cq.from(Sale.class);
        cq.where(cb.and(cb.equal(root.get("customer").get("id"), id),
                cb.equal(root.get("saleStatus"), Sale.SaleStatus.Inprogress)));
        cq.select(root);
        return em.createQuery(cq).getResultList().size() <= 0;
    }

    /**
     * Customers can buy car(s) using the system.
     *
     * @param sale
     */
    @Override
    public void buy(Sale sale) throws Exception {
        sale.setBuyAt(new Date());
        em.persist(sale);
    }
    /**
     * Salesperson is able to confirm the payment of a sale has been made.
     *
     * @param sale
     */
    @Override
    public void confirmPayment(Long id) throws Exception {
        Sale s = findSaleById(id);
        if (null == s) {
            throw new Exception("Sale with id <" + id + "> does not exist.");
        }
        s.setConfirmAt(new Date());
        s.setSaleStatus(Sale.SaleStatus.Completed);
        em.merge(s);
    }
    /**
     * Salesperson remove sale record
     * @param sale
     * @throws Exception 
     */
    @Override
    public void removeSale(Long id) throws Exception {
        Sale s = findSaleById(id);
        if (null == s) {
            throw new Exception("Sale with id <" + id + "> does not exist.");
        }
        em.remove(s);
    }

    /**
     * Find sale
     * @param id
     * @return
     * @throws Exception 
     */
    @Override
    public Sale findSaleById(Long id) throws Exception {
        return em.find(Sale.class, id);
    }
    
        /**
     * Get user related sale records
     * @param id
     * @return
     * @throws Exception 
     */
    @Override
    public List<Sale> retrieveSalesRecord(Long id) throws Exception{
         CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Sale.class);
        Root root = cq.from(Sale.class);
        cq.where(cb.or(cb.equal(root.get("customer").get("id"), id),
                cb.equal(root.get("salesperson").get("id"), id)));
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }
}
