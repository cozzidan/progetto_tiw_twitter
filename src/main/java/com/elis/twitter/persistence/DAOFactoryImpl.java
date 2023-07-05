package com.elis.twitter.persistence;

import com.elis.twitter.persistence.dao.CommentDAO;
import com.elis.twitter.persistence.dao.MessageDAO;
import com.elis.twitter.persistence.dao.ThreadDAO;
import com.elis.twitter.persistence.dao.UserDAO;
import com.elis.twitter.persistence.dao_impl.CommentDAOImpl;
import com.elis.twitter.persistence.dao_impl.MessageDAOImpl;
import com.elis.twitter.persistence.dao_impl.ThreadDAOImpl;
import com.elis.twitter.persistence.dao_impl.UserDAOImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DAOFactoryImpl extends DAOFactory {

    // EntityManagerFactory emf -> singleton
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("twitter_db");

    private static final DAOFactoryImpl instance = new DAOFactoryImpl();

    public static DAOFactoryImpl getInstance() {
        return instance;
    }

    private DAOFactoryImpl() {

    }

    @Override
    public UserDAO getUserDAO() {
        return UserDAOImpl.getInstance();
    }

    @Override
    public ThreadDAO getThreadDAO() {
        return ThreadDAOImpl.getInstance();
    }

    @Override
    public MessageDAO getMessageDAO() {
        return MessageDAOImpl.getInstance();
    }

    @Override
    public CommentDAO getCommentDAO() {
        return CommentDAOImpl.getInstance();
    }

    public EntityManager getManager() {
        return emf.createEntityManager();
    }
}
