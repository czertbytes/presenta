package cz.danielhodan.presenta.core.dao.impl;

import cz.danielhodan.presenta.core.dao.PresentationDao;
import cz.danielhodan.presenta.core.domain.Presentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@TransactionConfiguration
public class PresentationDaoImpl extends AbstractDaoImpl<Presentation, Integer> implements PresentationDao {

    private static final Logger log = LoggerFactory.getLogger(MessageDaoImpl.class);

    PresentationDaoImpl() {
        super(Presentation.class);
    }
}
