package cz.danielhodan.presenta.core.service;

import cz.danielhodan.presenta.core.domain.Message;

import java.util.List;

public interface MessageService {

    Message insert(Message message);

    Message findById(Integer id);

    List<Message> findAll();

    Message update(Message message);

    boolean delete(Message message);
}
