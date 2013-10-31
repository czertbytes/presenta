package cz.danielhodan.presenta.core.service.impl;

import cz.danielhodan.presenta.core.common.CoreException;
import cz.danielhodan.presenta.core.dao.MessageDao;
import cz.danielhodan.presenta.core.domain.Message;
import cz.danielhodan.presenta.core.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    MessageDao messageDao;

    public Message insert(Message account) {
        try {
            return messageDao.insert(account);
        } catch (CoreException e) {
            log.warn("Message insert failed!", e);
            return null;
        }
    }

    public Message findById(Integer id) {
        return messageDao.findById(id);
    }

    public List<Message> findAll() {
        return messageDao.findAll();
    }

    public Message update(Message account) {
        try {
            return messageDao.update(account);
        } catch (CoreException e) {
            log.warn("Message update failed!", e);
            return null;
        }
    }

    public boolean delete(Message account) {
        if (account.getId() != null) {
            return messageDao.delete(account.getId());
        } else {
            log.warn("Message delete failed! Message id can't be null!");
            return false;
        }
    }
}
