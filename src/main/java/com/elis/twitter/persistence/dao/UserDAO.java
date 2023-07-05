package com.elis.twitter.persistence.dao;

import com.elis.twitter.model.User;

import java.util.List;

public interface UserDAO {
    boolean save(User user);
    User getById(Long id);
    User getByUsername(String username);
    boolean update(User user);
    boolean delete(User user);
    List<User> getAll();
    User login(String username, String password);
}
