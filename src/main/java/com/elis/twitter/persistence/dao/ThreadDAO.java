package com.elis.twitter.persistence.dao;

import com.elis.twitter.model.Message;
import com.elis.twitter.model.Thread;
import com.elis.twitter.model.User;

import java.util.List;

public interface ThreadDAO {
    boolean save(Thread thread);
    Thread getById(Long id);
    boolean update(Thread thread);
    boolean delete(Thread thread);
    List<Thread> getAll();
    List<Thread> getAllOther(User user);
}
