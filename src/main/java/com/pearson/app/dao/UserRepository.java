package com.pearson.app.dao;

import com.pearson.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


/**
 *
 * Repository class for the User entity
 *
 */
@Repository
public class UserRepository implements UserDAOInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOInterface.class);

    @PersistenceContext
    private EntityManager em;

    /**
     * create a new user
     * @param user
     */
    public void addUser(User user) {
        em.persist(user);
        LOGGER.debug("Added new user[{}]", user.toString());
    }

    /**
     * list all users
     * @return  list users, or an empty collection if no match found
     */
    public List<User> listUsers() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // the actual search query that returns one page of results
        CriteriaQuery<User> searchQuery = cb.createQuery(User.class);
        Root<User> searchRoot = searchQuery.from(User.class);
        searchQuery.select(searchRoot);
        List<User> users = em.createQuery(searchQuery).getResultList();
        LOGGER.debug("Found {} Users", (users != null) ? users.size() : 0);
        return users;
    }

    /**
     * finds a user given its id
     * @param id - the id of the searched user
     * @return  a matching user, or null if no user found.
     */
    public User getUserById(Integer id) {
        User user = em.find(User.class, id);
        LOGGER.debug("Found matching Id[{}] ->  User[{}]", id, (user != null) ? user.toString() : "None");
        return user;
    }

    /**
     * finds a user given its username
     * @param username - the username of the searched user
     * @return  a matching user, or null if no user found.
     */
    public User getUserByUsername(String username) {
        List<User> users = em.createNamedQuery(User.FIND_BY_USERNAME, User.class)
                .setParameter("username", username)
                .getResultList();
        LOGGER.debug("Found matching Username[{}] -> User[{}]", username, (users != null && users.size() == 1) ? users.get(0).getUsername() : "None");
        return users.size() == 1 ? users.get(0) : null;
    }


    /**
     * save changes made to a user, or insert it if its new
     * @param user
     */
    public void updateUser(User user) {
        em.merge(user);
        LOGGER.debug("Updated User[{}]", user.toString());
    }


    /**
     * Delete a User, given its identifier
     * @param id - the id of the User to be deleted
     */
    public void removeUser(Long id) {
        User user = em.find(User.class, id);
        em.remove(user);
        LOGGER.debug("Removed User[{}]", user.toString());
    }


    /**
     * checks if a username is still available in the database
     * @param username - the username to be checked for availability
     * @return true if the username is still available
     */
    public boolean isUsernameAvailable(String username) {
        List<User> users = em.createNamedQuery(User.FIND_BY_USERNAME, User.class)
                .setParameter("username", username)
                .getResultList();
        LOGGER.debug("[{}] users found matching Username[{}]", (users != null & users.size() > 0) ? users.size() : 0, username);

        return users.size() == 0;
    }

}
