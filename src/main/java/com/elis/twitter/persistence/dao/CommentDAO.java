package com.elis.twitter.persistence.dao;

import com.elis.twitter.model.Comment;

import java.util.List;

public interface CommentDAO {
    boolean save(Comment comment);
    Comment getById(Long id);
    boolean update(Comment comment);
    boolean delete(Comment comment);
    List<Comment> getAll();
}
