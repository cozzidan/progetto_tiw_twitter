package com.elis.twitter.persistence.dao_impl;

import com.elis.twitter.model.Message;
import com.elis.twitter.model.Thread;
import com.elis.twitter.persistence.DAOFactoryImpl;
import com.elis.twitter.persistence.dao.MessageDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.util.List;

public class MessageDAOImpl implements MessageDAO {
    private static final MessageDAOImpl instance = new MessageDAOImpl();

    private MessageDAOImpl() {

    }

    public static MessageDAOImpl getInstance() {
        return instance;
    }


    @Override
    public boolean save(Message message) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.persist(message);
            entityTransaction.commit();
            return true;
        } catch(RuntimeException e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }

        return false;
    }

    @Override
    public Message getById(Long id) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        Query q = entityManager
                .createQuery("select m from Message m where m.id = :id")
                .setParameter("id", id);
        try {
            return (Message) q.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Message message) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.merge(message);
            entityTransaction.commit();
            return true;
        } catch(RuntimeException e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return false;
    }

    @Override
    public boolean delete(Message message) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.remove(entityManager.find(Message.class, message.getId()));
            entityTransaction.commit();
            return true;
        } catch(RuntimeException e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return false;
    }

    @Override
    public List<Message> getAll() {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        Query q = entityManager.createQuery("select m from Message m");
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Message> getAllDescOrder() {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        Query q = entityManager.createQuery("select m from Message m order by m.date desc");
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    // method used for pagination
    @Override
    public List<Message> getPaginatedMessagesForThread(Thread thread, int firstResult) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        Query q = entityManager
                .createQuery("select m from Message m where m.thread = :thread order by m.date desc, m.id desc")
                .setParameter("thread", thread)
                .setFirstResult(firstResult)
                .setMaxResults(10);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
}
