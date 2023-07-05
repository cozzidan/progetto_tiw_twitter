package com.elis.twitter.persistence.dao;

import com.elis.twitter.model.Message;
import com.elis.twitter.model.Thread;

import java.util.List;

public interface MessageDAO {
    boolean save(Message message);
    Message getById(Long id);
    boolean update(Message message);
    boolean delete(Message message);
    List<Message> getAll();
    List<Message> getAllDescOrder();
    List<Message> getPaginatedMessagesForThread(Thread thread, int firstResult);
}
