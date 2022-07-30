package transaction.repository;

import common.repository.InMemRepository;
import transaction.model.Transaction;

import java.time.LocalDateTime;
import java.util.*;

public class InMemTransactionRepository extends InMemRepository<Transaction> implements ITransactionRepository {

    private final Vector<Transaction> repository = new Stack<Transaction>();

    private static Integer transactionIdCounter = 0;

    private static int generateTransactionId(){
        return ++transactionIdCounter;
    }

    public Optional<Transaction> create(Transaction newItem){
        if (getAll().stream()
                .anyMatch(transaction ->
                        (transaction.getFromAccountId().equals(newItem.getFromAccountId()) &&
                        (transaction.getTimestamp().equals(newItem.getTimestamp()) &&
                        transaction.getToAccoundId().equals(newItem.getToAccoundId())
                        )))
        )
        if (newItem == null){
            throw new IllegalArgumentException("Transaction can not be null");
        }
        Transaction transaction = newItem;
        newItem.setId(generateTransactionId());
        repository.add(transaction);
        return Optional.of(transaction);

    }


    @Override
    public Collection<Transaction> findByAllFromAccountId(Integer accountId) {
        return null;
    }

    @Override
    public Collection<Transaction> findByToAllAccountId(Integer accountId) {
        return null;
    }

    @Override
    public Collection<Transaction> findByAllFromAndToAccountId(Integer fromAccountId, Integer toAccountId) {
        return null;
    }

    @Override
    public Optional<Transaction> getTransactionFromToAccountId(Integer fromAccountId, LocalDateTime timestamp,
                                                               Integer toAccountId) {
        return Optional.empty();
    }
}
