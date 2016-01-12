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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * User related operation implementation
 *
 * @author zipv5_000
 */
@Stateless
public class UserManagerBean implements UserManager {

    @PersistenceContext
    private EntityManager em;

    /**
     * Find user by id
     *
     * @param email
     * @return
     * @throws Exception
     */
    @Override
    public User findUserByEmail(String email) throws Exception {
        List<User> users = em.createNamedQuery(User.FIND_BY_EMAIL).setParameter("email", email).getResultList();
        if (users.size() > 1) {
            throw new Exception("Corrupted user table.");
        }
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    /**
     * Register new user
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void register(User user) throws Exception {
        User u = findUserByEmail(user.getEmail());
        if (u != null) {
            throw new Exception("User with email <" + user.getEmail() + "> already exists!");
        }
        user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
        em.persist(user);
    }

    /**
     * Update user details
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void updateUser(User user) throws Exception {
        User u = findUserById(user.getId());
        if (u == null) {
            throw new Exception("User <" + user.getId() + "> does not exist!");
        }
        em.merge(user);
    }

    /**
     * Remove user
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void removeUser(Long id) throws Exception {
        User u = findUserById(id);
        if (u == null) {
            throw new Exception("User <" + id + "> does not exist!");
        }
        em.remove(u);
    }

    /**
     * Find users by criteria
     *
     * @param group: user type
     * @param id
     * @param fname
     * @param lname
     * @param email
     * @return
     * @throws Exception
     */
    @Override
    public List<User> findUsers(User.UserGroup group, Long id, String fname, String lname, String email) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Customer.class);
        Root root = cq.from(Customer.class);
        if (group == User.UserGroup.Salesperson) {
            cq = cb.createQuery(Salesperson.class);
            root = cq.from(Salesperson.class);
        }
        if (id != null) {
            cq.where(cb.equal(root.get("id"), id));
        } else {
            cq.where(cb.and(cb.like(root.get("firstName"), "%" + fname + "%"),
                    cb.and(cb.like(root.get("lastName"), "%" + lname + "%"),
                            cb.like(root.get("email"), "%" + email + "%"))));
        }
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }

    /**
     * Find all salepersons
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<Salesperson> findAllSalesperson() throws Exception {
        return em.createNamedQuery(Salesperson.FIND_ALL, Salesperson.class).getResultList();
    }

    /**
     * Find user by id
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public User findUserById(Long id) throws Exception {
        return em.find(User.class, id);
    }

}
