package transaction.repository;

import common.repository.IRepository;
import transaction.model.Transaction;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface ITransactionRepository extends IRepository<Transaction> {

    Collection<Transaction> findByAllFromAccountId(Integer accountId);

    Collection<Transaction> findByToAllAccountId(Integer accountId);

    Collection<Transaction> findByAllFromAndToAccountId(Integer fromAccountId, Integer toAccountId);

    Optional<Transaction> getTransactionFromToAccountId(Integer fromAccountId, LocalDateTime timestamp, Integer toAccountId);

}
