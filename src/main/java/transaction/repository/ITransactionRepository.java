package transaction.repository;

import common.repository.IRepository;
import transaction.exceptions.TransactionNotFoundException;
import transaction.model.Transaction;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface ITransactionRepository extends IRepository<Transaction> {

    Collection<Transaction> findByAllFromAccountId(Integer accountId) throws TransactionNotFoundException;

    Collection<Transaction> findByAllToAccountId(Integer accountId);

    Collection<Transaction> findByAllFromAndToAccountId(Integer fromAccountId, Integer toAccountId);

    Optional<Transaction> getTransactionFromAndToAccountId(Integer fromAccountId, LocalDateTime timestamp, Integer toAccountId);

}
