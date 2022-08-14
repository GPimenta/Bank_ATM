package transaction.service;

import transaction.exceptions.TransactionNotFoundException;
import transaction.exceptions.TransactonConflictException;
import transaction.model.Transaction;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ITransactionService {

    Transaction createTransaction(Integer fromAccountId, Integer toAccount, Integer cardId, LocalDateTime timestamp,
                                  String amount) throws TransactonConflictException;

    void deleteTransaction(Integer transactionId) throws TransactionNotFoundException;

    Transaction getTransaction(Integer transactionId) throws TransactionNotFoundException;

//    Transaction updateTransaction(Integer transactionId, Integer fromAccountId, Integer toAccountId) throws TransactionNotFoundException;

    Collection<Transaction> findByAllTransactionsFromAccountId(Integer accountId);

    Collection<Transaction> findByAllTransactionsToAccountId(Integer accountId);

    Collection<Transaction> findByAllTransactionsFromAndToAccountId(Integer fromAccountId, Integer toAccountId);

    Transaction getTransactionFromAndToAccountId(Integer fromAccountId, LocalDateTime timestamp,
                                                             Integer toAccountId) throws TransactionNotFoundException;


}
