package com.elis.twitter.persistence;

import com.elis.twitter.persistence.dao.CommentDAO;
import com.elis.twitter.persistence.dao.MessageDAO;
import com.elis.twitter.persistence.dao.ThreadDAO;
import com.elis.twitter.persistence.dao.UserDAO;

public abstract class DAOFactory {
    public abstract UserDAO getUserDAO();
    public abstract ThreadDAO getThreadDAO();
    public abstract MessageDAO getMessageDAO();
    public abstract CommentDAO getCommentDAO();

    public static DAOFactory getDAOFactory() {
        return DAOFactoryImpl.getInstance();
    }
}
