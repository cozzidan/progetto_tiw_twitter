package com.elis.twitter.persistence.dao_impl;

import com.elis.twitter.model.Comment;
import com.elis.twitter.persistence.DAOFactoryImpl;
import com.elis.twitter.persistence.dao.CommentDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.util.List;

public class CommentDAOImpl implements CommentDAO {
    private static final CommentDAOImpl instance = new CommentDAOImpl();

    private CommentDAOImpl() {

    }

    public static CommentDAOImpl getInstance() {
        return instance;
    }

    @Override
    public boolean save(Comment comment) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.persist(comment);
            entityTransaction.commit();
            return true;
        } catch(RuntimeException e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return false;
    }

    @Override
    public Comment getById(Long id) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        Query q = entityManager
                .createQuery("select c from Comment c where c.id = :id")
                .setParameter("id", id);
        try {
            return (Comment) q.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Comment comment) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.merge(comment);
            entityTransaction.commit();
            return true;
        } catch(RuntimeException e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return false;
    }

    @Override
    public boolean delete(Comment comment) {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.remove(entityManager.find(Comment.class, comment.getId()));
            entityTransaction.commit();
            return true;
        } catch(RuntimeException e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
        return false;
    }

    @Override
    public List<Comment> getAll() {
        EntityManager entityManager = DAOFactoryImpl.getInstance().getManager();
        Query q = entityManager.createQuery("select c from Comment c");
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
}
