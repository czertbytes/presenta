package cz.danielhodan.presenta.core.service.impl;

import cz.danielhodan.presenta.core.common.CoreException;
import cz.danielhodan.presenta.core.dao.PresentationDao;
import cz.danielhodan.presenta.core.domain.Presentation;
import cz.danielhodan.presenta.core.service.PresentationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PresentationServiceImpl implements PresentationService {

    private static final Logger log = LoggerFactory.getLogger(PresentationServiceImpl.class);

    @Autowired
    PresentationDao presentationDao;

    public Presentation insert(Presentation account) {
        try {
            return presentationDao.insert(account);
        } catch (CoreException e) {
            log.warn("Presentation insert failed!", e);
            return null;
        }
    }

    public Presentation findById(Integer id) {
        return presentationDao.findById(id);
    }

    public List<Presentation> findAll() {
        return presentationDao.findAll();
    }

    public Presentation update(Presentation account) {
        try {
            return presentationDao.update(account);
        } catch (CoreException e) {
            log.warn("Presentation update failed!", e);
            return null;
        }
    }

    public boolean delete(Presentation account) {
        if (account.getId() != null) {
            return presentationDao.delete(account.getId());
        } else {
            log.warn("Presentation delete failed! Presentation id can't be null!");
            return false;
        }
    }
}
