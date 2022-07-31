package transaction.service;

import transaction.model.Transaction;

import java.time.LocalDateTime;

public interface ITransactionService {

    Transaction createTransaction(Integer fromAccountId, Integer toAccount, Integer cardId, LocalDateTime timestamp,
                                  String amount);
}
