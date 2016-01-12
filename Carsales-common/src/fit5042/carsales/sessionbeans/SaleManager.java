/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.sessionbeans;

import fit5042.carsales.entities.Sale;
import java.util.List;
import java.util.Set;
import javax.ejb.Remote;

/**
 * Sale record related operations
 * @author zipv5_000
 */
@Remote
public interface SaleManager {
    
    /**
     * A customer cannot buy another car if he/she has not paid off all
     * purchases on their account
     *
     * @param id:the user to check
     * @return
     */
    boolean checkCustomerPayment(Long id) throws Exception;

    /**
     * Customers can buy car(s) using the system.
     *
     * @param sale
     */
    void buy(Sale sale) throws Exception;

    /**
     * Salesperson is able to confirm the payment of a sale has been made.
     *
     * @param sale
     */
    void confirmPayment(Long id) throws Exception;
    
    /**
     * Salesperson remove sale record
     * @param sale
     * @throws Exception 
     */
    void removeSale(Long id) throws Exception;
    
    /**
     * Find sale
     * @param id
     * @return
     * @throws Exception 
     */
    Sale findSaleById(Long id) throws Exception;
        
    /**
     * Get user related sale records
     * @param id
     * @return
     * @throws Exception 
     */
    List<Sale> retrieveSalesRecord(Long id) throws Exception;
}
