package cz.danielhodan.presenta.core.service.impl;

import cz.danielhodan.presenta.core.common.CoreException;
import cz.danielhodan.presenta.core.dao.AccountDao;
import cz.danielhodan.presenta.core.domain.Account;
import cz.danielhodan.presenta.core.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    AccountDao accountDao;

    public Account insert(Account account) {
        try {
            return accountDao.insert(account);
        } catch (CoreException e) {
            log.warn("Account insert failed!", e);
            return null;
        }
    }

    public Account findById(Integer id) {
        return accountDao.findById(id);
    }

    public List<Account> findAll() {
        return accountDao.findAll();
    }

    public Account update(Account account) {
        try {
            return accountDao.update(account);
        } catch (CoreException e) {
            log.warn("Account update failed!", e);
            return null;
        }
    }

    public boolean delete(Account account) {
        if (account.getId() != null) {
            return accountDao.delete(account.getId());
        } else {
            log.warn("Account delete failed! Account id can't be null!");
            return false;
        }
    }

    public Account findByUsername(String username) {
        try {
            return accountDao.findByUsername(username);
        } catch (CoreException e) {
            log.warn("Account find by username failed!", e);
            return null;
        }
    }
}
