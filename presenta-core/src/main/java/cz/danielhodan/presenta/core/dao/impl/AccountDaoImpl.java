package cz.danielhodan.presenta.core.dao.impl;

import cz.danielhodan.presenta.core.common.CoreException;
import cz.danielhodan.presenta.core.dao.AccountDao;
import cz.danielhodan.presenta.core.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
@Transactional
@TransactionConfiguration
public class AccountDaoImpl extends AbstractDaoImpl<Account, Integer> implements AccountDao {

    private static final Logger log = LoggerFactory.getLogger(AccountDaoImpl.class);

    AccountDaoImpl() {
        super(Account.class);
    }

    public Account findByUsername(String username) throws CoreException {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        final Root<Account> entity = criteriaQuery.from(entityClass);

        criteriaQuery.select(entity).where(criteriaBuilder.equal(entity.<String>get("username"), username));

        Account account;
        try {
            account = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            throw new CoreException("There is no account with username '" + username + "'!");
        } catch (NonUniqueResultException e) {
            throw new CoreException("There are more then one accounts with username '" + username + "'!");
        }

        return account;
    }
}
