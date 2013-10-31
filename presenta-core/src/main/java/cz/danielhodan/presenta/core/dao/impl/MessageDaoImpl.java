package cz.danielhodan.presenta.core.dao.impl;

import cz.danielhodan.presenta.core.dao.MessageDao;
import cz.danielhodan.presenta.core.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@TransactionConfiguration
public class MessageDaoImpl extends AbstractDaoImpl<Message, Integer> implements MessageDao {

    private static final Logger log = LoggerFactory.getLogger(MessageDaoImpl.class);

    MessageDaoImpl() {
        super(Message.class);
    }
}
