package transaction.service;

import accounts.exceptions.AccountNotFoundException;
import transaction.exceptions.TransactionNotFoundException;
import transaction.exceptions.TransactonConflictException;
import transaction.model.Transaction;
import transaction.repository.ITransactionRepository;
import utils.IPreconditions;

import java.time.LocalDateTime;
import java.util.Collection;

public class TransactionService implements  ITransactionService{

    private final ITransactionRepository repository;

    public TransactionService(ITransactionRepository repository) {
        this.repository = IPreconditions.checkNotNull(repository, "Transaction Repository cannot be null");
    }

    @Override
    public Transaction createTransaction(Integer fromAccountId, Integer toAccountId, Integer cardId, LocalDateTime timestamp,
                                         String amount) throws TransactonConflictException {
        Transaction transaction = new Transaction.Builder()
                .withFromAccountId(fromAccountId)
                .withToAccountId(toAccountId)
                .withCardId(cardId)
                .withTimeStamp(timestamp)
                .withAmount(amount)
                .build();

        return repository.create(transaction).orElseThrow(() -> new TransactonConflictException("Conflict on creating" +
                " transaction with from Account Id: '%d' to Account Id: %d", fromAccountId, toAccountId));
    }

    @Override
    public void deleteTransaction(Integer transactionId) throws TransactionNotFoundException {
        if(!repository.deleteById(transactionId)){
            throw new TransactionNotFoundException("Transaction with Id: '%d' not found to delete", transactionId);
        }
    }

    @Override
    public Transaction getTransaction(Integer transactionId) throws TransactionNotFoundException {
        return repository.getById(transactionId).orElseThrow(() -> new TransactionNotFoundException("Transaction" +
                " with Id: '%d' not found to get Account", transactionId));
    }

//    @Override
//    public Transaction updateTransaction(Integer transactionId) throws TransactionNotFoundException {
//        Transaction transaction = getTransaction(transactionId);
//        repository.update(tra)
//        return null;
//    }

    @Override
    public Collection<Transaction> findByAllTransactionsFromAccountId(Integer accountId) {
        if (repository.findByAllFromAccountId(accountId).isEmpty())
            System.out.println("No transaction made from this account");
        return repository.findByAllFromAccountId(accountId);
    }

    @Override
    public Collection<Transaction> findByAllTransactionsToAccountId(Integer accountId) {
        if(repository.findByAllToAccountId(accountId).isEmpty())
            System.out.println("No transaction made to this account");
        return repository.findByAllToAccountId(accountId);
    }

    @Override
    public Collection<Transaction> findByAllTransactionsFromAndToAccountId(Integer fromAccountId, Integer toAccountId) {
        if (repository.findByAllFromAndToAccountId(fromAccountId, toAccountId).isEmpty())
            System.out.println("No transaction made from " + fromAccountId + " to " + toAccountId);
        return repository.findByAllFromAndToAccountId(fromAccountId, toAccountId);
    }

    @Override
    public Transaction getTransactionFromAndToAccountId(Integer fromAccountId, LocalDateTime timestamp, Integer toAccountId)
            throws TransactionNotFoundException {

        return repository.getTransactionFromAndToAccountId(fromAccountId, timestamp, toAccountId).orElseThrow(() ->
                new TransactionNotFoundException("Transaction from %i, to %i, at %s ", fromAccountId,  toAccountId,
                        timestamp.toString()));
    }
}
