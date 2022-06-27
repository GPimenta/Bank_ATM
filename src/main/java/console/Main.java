package console;

import accounts.repository.IAccountRepository;
import accounts.repository.InMemAccountRepository;
import accounts.service.AccountService;
import accounts.service.IAccountService;
import cards.repository.ICardRepository;
import cards.repository.InMemCardRepositoryImpl;
import cards.service.CardService;
import cards.service.ICardService;
import costumers.exceptions.CustomerConflictException;
import costumers.exceptions.CustomerNotFoundException;
import costumers.repository.ICustomerRepository;
import costumers.repository.InMemCustomerRepositoryImpl;
import costumers.service.CustomerService;
import costumers.service.ICustomerService;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Main {
    public static void main(String[] args) throws CustomerConflictException, CustomerNotFoundException {

        List<Integer> secondaryOwnersId = List.of(1, 2, 3);

        ICustomerRepository customerRepository = new InMemCustomerRepositoryImpl();
        IAccountRepository accountRepository = new InMemAccountRepository();
        ICardRepository cardRepository = new InMemCardRepositoryImpl();


        ICustomerService customerService = new CustomerService(customerRepository);
        IAccountService accountService = new AccountService(accountRepository);
        ICardService cardService = new CardService(cardRepository);

        customerService.createCustomer("Goncalo", "259569038", "goncalo@gmail.com", LocalDate.of(1989, Month.AUGUST, 3));
        customerService.createCustomer("Nixx", "123456789", "nix@gmail.com", LocalDate.of(2010, Month.JUNE, 10));
        customerService.createCustomer("Leandro", "987654321", "leandro@gmail.com", LocalDate.of(2000, Month.JANUARY, 20));
        customerService.createCustomer("Barbichas", "192837465", "barbichas@gmail.com", LocalDate.of(1996, Month.MARCH, 12));

//        customerService.getAllCustomer().forEach(customer -> System.out.println(customer));
//        customerService.getAllCustomer().forEach(System.out::println);



    }
}
