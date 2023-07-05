package com.elis.twitter.persistence.dao_impl;

import com.elis.twitter.model.User;
import com.elis.twitter.persistence.DAOFactoryImpl;
import com.elis.twitter.persistence.dao.UserDAO;
import jakarta.persistence.*;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final UserDAOImpl instance = new UserDAOImpl();

    private UserDAOImpl() {

    }

    public static UserDAOImpl getInstance() {
        return instance;
    }

    @Override
    public boolean save(User user) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.persist(user);
            entityTransaction.commit();
            return true;
        } catch(RuntimeException e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }

        return false;
    }

    @Override
    public User getById(Long id) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        Query q = entityManager
                .createQuery("select u from User u where u.id = :id")
                .setParameter("id", id);
        try {
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getByUsername(String username) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        Query q = entityManager
                .createQuery("select u from User u where u.username = :username")
                .setParameter("username", username);
        try {
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(User user) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.merge(user);
            entityTransaction.commit();
            return true;
        } catch(RuntimeException e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return false;
    }

    @Override
    public boolean delete(User user) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.remove(entityManager.find(User.class, user.getId()));
            entityTransaction.commit();
            return true;
        } catch(RuntimeException e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        Query q = entityManager.createQuery("select u from User u");
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User login(String username, String password) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        Query q = entityManager
                .createQuery("select u from User u where u.username = :username and u.password = :password")
                .setParameter("username", username)
                .setParameter("password", password);
        try {
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
}
