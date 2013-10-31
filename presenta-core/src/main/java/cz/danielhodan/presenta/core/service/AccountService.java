package cz.danielhodan.presenta.core.service;

import cz.danielhodan.presenta.core.domain.Account;

import java.util.List;

public interface AccountService {

    Account insert(Account account);

    Account findById(Integer id);

    List<Account> findAll();

    Account findByUsername(String username);

    Account update(Account account);

    boolean delete(Account account);
}
