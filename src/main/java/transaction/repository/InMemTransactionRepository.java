package transaction.repository;

import common.repository.InMemRepository;
import jdk.javadoc.doclet.Reporter;
import transaction.exceptions.TransactionNotFoundException;
import transaction.model.Transaction;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemTransactionRepository extends InMemRepository<Transaction> implements ITransactionRepository {

    private final Vector<Transaction> repository = new Stack<Transaction>();

    private static Integer transactionIdCounter = 0;

    private static int generateTransactionId(){
        return ++transactionIdCounter;
    }

    public Optional<Transaction> create(Transaction newItem){
        System.out.println(newItem.toString());
        if (getAll().stream()
                .anyMatch(transaction ->
                        ( (transaction.getFromAccountId().equals(newItem.getFromAccountId())) &&
                                (transaction.getToAccoundId().equals(newItem.getToAccoundId())) &&
                                (transaction.getTimestamp().equals(newItem.getTimestamp()))
                                )
            )
        ){

            return Optional.empty();
        }


        if (newItem == null){
            throw new IllegalArgumentException("Transaction can not be null");
        }

        Transaction transaction = newItem;
        transaction.setId(generateTransactionId());
        repository.add(transaction);
        return Optional.of(transaction);

    }

    @Override
    public boolean deleteById(Integer id) {
        return repository.removeIf(transaction -> transaction.getId().equals(id));
    }
    @Override

    public Optional<Transaction> update(Transaction newItem) {
        if (deleteById(newItem.getId())){
            repository.add(newItem);
            return Optional.of(newItem);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Transaction> getById(Integer id) {
        for (Transaction transaction: repository){
            if (transaction.getId().equals(id)){
                return Optional.of(transaction);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<Transaction> getAll() {
        Stack<Transaction> stack = new Stack<>();
        stack.addAll(repository);

        return stack;
    }

    @Override
    public Collection<Transaction> findByAllFromAccountId(Integer accountId){
        Collection<Transaction> fromAccount = getAll().stream().filter(transaction -> transaction.getFromAccountId().equals(accountId))
                .collect(Collectors.toList());

//        if (fromAccount.isEmpty()) {
//            System.out.println("No Transactions made");
//            return new Stack<Transaction>();
//        }

        return fromAccount;
    }

    @Override
    public Collection<Transaction> findByAllToAccountId(Integer accountId) {
        Collection<Transaction> toAccount = getAll().stream().filter(transaction -> transaction.getToAccoundId().equals(accountId))
                .collect(Collectors.toList());

        return toAccount;
    }

    @Override
    public Collection<Transaction> findByAllFromAndToAccountId(Integer fromAccountId, Integer toAccountId) {
        Collection<Transaction> transactions = getAll().stream()
                .filter(transaction -> (transaction.getFromAccountId().equals(fromAccountId)))
                .filter(transaction -> (transaction.getToAccoundId().equals(toAccountId)))
                .collect(Collectors.toList());

        return transactions;
    }

    @Override
    public Optional<Transaction> getTransactionFromAndToAccountId(Integer fromAccountId, LocalDateTime timestamp,
                                                                  Integer toAccountId) {
        Optional<Transaction> oneTransaction = getAll().stream()
                .filter(transaction -> (transaction.getFromAccountId().equals(fromAccountId)))
                .filter(transaction -> (transaction.getToAccoundId().equals(toAccountId)))
                .filter(transaction -> transaction.getTimestamp().equals(timestamp))
                .findAny();


        return oneTransaction;
    }
}
