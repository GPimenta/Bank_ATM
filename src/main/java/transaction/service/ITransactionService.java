package transaction.service;

import transaction.model.Transaction;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ITransactionService {

    Transaction createTransaction(Integer fromAccountId, Integer toAccount, Integer cardId, LocalDateTime timestamp,
                                  String amount);

    Transaction deleteTransaction(Integer transactionId);

    Transaction getTransaction(Integer transactionId);

    Transaction updateTransaction(Integer transactionId);

    Collection<Transaction> findByAllTransactionsFromAccountId(Integer accountId);

    Collection<Transaction> findByAllTransactionsToAccountId(Integer accountId);

    Collection<Transaction> findByAllTransactionsFromAndToAccountId(Integer fromAccountId, Integer toAccountId);

    Collection<Transaction> getTransactionFromAndToAccountId(Integer fromAccountId, LocalDateTime timestamp,
                                                             Integer toAccountId);


}
