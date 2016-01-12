/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.sessionbeans;

import fit5042.carsales.entities.Customer;
import fit5042.carsales.entities.Sale;
import fit5042.carsales.entities.Salesperson;
import fit5042.carsales.entities.User;
import java.util.List;
import java.util.Set;
import javax.ejb.Remote;

/**
 * User related operations
 * @author zipv5_000
 */
@Remote
public interface UserManager {
    
    /**
     * Find user by id
     * @param id
     * @return
     * @throws Exception 
     */
    User findUserById(Long id) throws Exception;
    
    /**
     * Find user by Email
     * @param email
     * @return
     * @throws Exception 
     */
    User findUserByEmail(String email) throws Exception;
    /**
     * Register new user
     * @param user
     * @throws Exception 
     */
    void register(User user) throws Exception;
    /**
     * Update user details
     * @param user
     * @throws Exception 
     */
    void updateUser(User user) throws Exception;
    /**
     * Remove user
     * @param id
     * @throws Exception 
     */
    void removeUser(Long id) throws Exception;
    /**
     * Find user by criteria
     * @param group: user type
     * @param id
     * @param fname
     * @param lname
     * @param email
     * @return
     * @throws Exception 
     */
    List<User> findUsers(User.UserGroup group, Long id, String fname, String lname, String email) throws Exception;
    /**
     * Acquire all salespersons
     * @return
     * @throws Exception 
     */
    List<Salesperson> findAllSalesperson() throws Exception;
}
