package cz.danielhodan.presenta.core.dao;

import cz.danielhodan.presenta.core.common.CoreException;
import cz.danielhodan.presenta.core.domain.Account;

public interface AccountDao extends AbstractDao<Account, Integer> {

    Account findByUsername(String username) throws CoreException;
}
