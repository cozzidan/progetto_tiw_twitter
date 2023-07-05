package com.elis.twitter.persistence.dao_impl;

import com.elis.twitter.model.Thread;
import com.elis.twitter.model.User;
import com.elis.twitter.persistence.DAOFactoryImpl;
import com.elis.twitter.persistence.dao.ThreadDAO;
import jakarta.persistence.*;

import java.util.List;

public class ThreadDAOImpl implements ThreadDAO {
    private static final ThreadDAOImpl instance = new ThreadDAOImpl();

    private ThreadDAOImpl() { }

    public static ThreadDAOImpl getInstance() {
        return instance;
    }

    @Override
    public boolean save(Thread thread) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.persist(thread);
            entityTransaction.commit();
            return true;
        } catch(RuntimeException e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return false;
    }

    @Override
    public Thread getById(Long id) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        Query q = entityManager
                .createQuery("select t from Thread t where t.id = :id")
                .setParameter("id", id);
        try {
            return (Thread) q.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Thread thread) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.merge(thread);
            entityTransaction.commit();
            return true;
        } catch(RuntimeException e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return false;
    }

    @Override
    public boolean delete(Thread thread) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.remove(entityManager.find(Thread.class, thread.getId()));
            entityTransaction.commit();
            return true;
        } catch(RuntimeException e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return false;
    }

    @Override
    public List<Thread> getAll() {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        Query q = entityManager.createQuery("select t from Thread t order by date desc, id desc");
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Thread> getAllOther(User user) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        Query q = entityManager
                .createQuery("select t from Thread t where t.user != :user order by date desc, id desc")
                .setParameter("user", user);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
}
