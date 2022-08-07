package transaction.service;

import transaction.model.Transaction;
import transaction.repository.ITransactionRepository;
import utils.IPreconditions;

import java.time.LocalDateTime;
import java.util.Collection;
{
public class TransactionService implements  ITransactionService

    private final ITransactionRepository repository;

    public TransactionService(ITransactionRepository repository) {
        this.repository = IPreconditions.checkNotNull(repository, "Transaction Repository cannot be null");
    }

    @Override
    public Transaction createTransaction(Integer fromAccountId, Integer toAccount, Integer cardId, LocalDateTime timestamp, String amount) {
        Transaction transaction = new Transaction.Builder()
                .withFromAccountId(fromAccountId)
                .withToAccountId(toAccount)
                .withCardId(cardId)
                .withTimeStamp(timestamp)
                .build();
        return null;
    }

    @Override
    public Transaction deleteTransaction(Integer transactionId) {
        return null;
    }

    @Override
    public Transaction getTransaction(Integer transactionId) {
        return null;
    }

    @Override
    public Transaction updateTransaction(Integer transactionId) {
        return null;
    }

    @Override
    public Collection<Transaction> findByAllTransactionsFromAccountId(Integer accountId) {
        return null;
    }

    @Override
    public Collection<Transaction> findByAllTransactionsToAccountId(Integer accountId) {
        return null;
    }

    @Override
    public Collection<Transaction> findByAllTransactionsFromAndToAccountId(Integer fromAccountId, Integer toAccountId) {
        return null;
    }

    @Override
    public Collection<Transaction> getTransactionFromAndToAccountId(Integer fromAccountId, LocalDateTime timestamp, Integer toAccountId) {
        return null;
    }
}
