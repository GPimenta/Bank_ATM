package console;

import accounts.exceptions.*;
import accounts.repository.InMemAccountRepository;
import accounts.service.AccountService;
import accounts.service.IAccountService;
import cards.exceptions.CardConflictException;
import cards.repository.InMemCardRepositoryImpl;
import cards.service.CardService;
import cards.service.ICardService;
import costumers.exceptions.CustomerConflictException;
import costumers.exceptions.CustomerNotFoundException;
import costumers.repository.InMemCustomerRepositoryImpl;
import costumers.service.CustomerService;
import costumers.service.ICustomerService;
import customerswithaccount.exceptions.CustomerWithAccountNotFoundException;
import customerswithaccount.model.CustomerWithAccount;
import customerswithaccount.repository.CustomerWithAccountRepository;
import customerswithaccount.service.CustomerWithAccountService;
import customerswithaccount.service.ICustomerWithAccountService;
import transaction.exceptions.TransactonConflictException;
import transaction.repository.InMemTransactionRepository;
import transaction.service.ITransactionService;
import transaction.service.TransactionService;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws CustomerConflictException, CustomerNotFoundException, AccountConflictException, CardConflictException, CustomerWithAccountNotFoundException, AccountNoFundsException, AccountVoidWithdrawException, AccountVoidDepositException, TransactonConflictException, AccountNotFoundException {
        ICustomerService customerService = new CustomerService(new InMemCustomerRepositoryImpl());
        IAccountService accountService = new AccountService(new InMemAccountRepository());
        ICardService cardService = new CardService(new InMemCardRepositoryImpl());
        ITransactionService transactionService = new TransactionService(new InMemTransactionRepository());
        ICustomerWithAccountService customerWithAccountService = new CustomerWithAccountService(new CustomerWithAccountRepository(), customerService, accountService, cardService, transactionService);

        int end = 0;
        CustomerWithAccount customerWithAccount = null;

        Scanner scanner = new Scanner(System.in);


        startText();
        while (end != 1) {
            firstMenu();
            try {
                switch (scanner.nextInt()){
                    case 1:
                        String[] details = customerDetails(scanner);
                        createAccountMenu();
                        switch (scanner.nextInt()) {
                            case 1: customerWithAccount = customerWithAccountService.createCustomerWithAccount(details[0], details[1], details[2], LocalDate.of(Integer.parseInt(details[3]), Integer.parseInt(details[4]), Integer.parseInt(details[5]))); break;
                            case 2: customerWithAccount = customerWithAccountService.createCustomerWithAccountDebitCard(details[0], details[1], details[2], LocalDate.of(Integer.parseInt(details[3]), Integer.parseInt(details[4]), Integer.parseInt(details[5]))); break;
                            case 3: customerWithAccount = customerWithAccountService.createCustomerWithAccountCreditCard(details[0], details[1], details[2], LocalDate.of(Integer.parseInt(details[3]), Integer.parseInt(details[4]), Integer.parseInt(details[5]))); break;
                            case 4: customerWithAccount = customerWithAccountService.createCustomerWithAccountCards(details[0], details[1], details[2], LocalDate.of(Integer.parseInt(details[3]), Integer.parseInt(details[4]), Integer.parseInt(details[5]))); break;
                            default: invalidNumber(); break;
                        } addMoney();
                            switch (scanner.nextInt()){
                                case 1: customerWithAccountService.depositMoney(customerWithAccount.getAccount().getId(), addMoneyToAccount(scanner)); break;
                                case 2: break;
                                default: invalidNumber(); break;
                            } break;
                    case 2: deleteAccount(); customerWithAccountService.deleteCustomerWithAccount(customerWithAccountService.findCustomerWithAccountThroughAccountNumber(scanner.next()).getAccount().getAccountNumber()); break;
                    case 3:
                        modifyAccountMenu();
                        switch (scanner.nextInt()) {
                            case 1: accountNumber(); customerWithAccountService.addDebitCardToCustomerWithAccount(customerWithAccountService.findCustomerWithAccountThroughAccountNumber(scanner.next()).getAccount().getAccountNumber()); break;
                            case 2: accountNumber(); customerWithAccountService.addCreditCardToCustomerWithAccount(customerWithAccountService.findCustomerWithAccountThroughAccountNumber(scanner.next()).getAccount().getAccountNumber()); break;
                            case 3: accountNumber(); customerWithAccountService.addCardsToCustomerWithAccount(customerWithAccountService.findCustomerWithAccountThroughAccountNumber(scanner.next()).getAccount().getAccountNumber()); break;
                            case 4: cardsMenu();
                                    switch (scanner.nextInt()){
                                        case 1: accountNumber(); customerWithAccountService.deleteDebitCardToCustomerWithAccount(scanner.next()); break;
                                        case 2: accountNumber(); customerWithAccountService.deleteCreditCardToCustomerWithAccount(scanner.next()); break;
                                        case 3: accountNumber(); customerWithAccountService.deleteAllCards(scanner.next()); break;
                                        default: invalidNumber(); break;
                                    } break;
                            case 5: final String[] secondaryOwner = secondaryOwner(scanner);
                                    secondaryOwnersMenu();
                                    switch (scanner.nextInt()){
                                        case 1: customerWithAccountService.addSecondaryOwners(secondaryOwner[0], secondaryOwner[1]); break;
                                        case 2: customerWithAccountService.deleteSecondaryOwners(secondaryOwner[0], secondaryOwner[1]); break;
                                    } break;
                            default: invalidNumber(); break;
                        } break;
                    case 4: String[] credentials = credentialsCardTransferMoney(scanner); customerWithAccountService.transferMoneyWithCard(credentials[0], credentials[1], credentials[2], Double.parseDouble(credentials[3])); break;
                    case 5: customerWithAccountService.showAllCustomersWithAccount(); break;
                    case 6: end = 1; System.out.println("Exiting Bank"); break;
                    default: invalidNumber(); break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Press enter again please");
                scanner.nextLine();
            }
        }
    }

    private static void invalidNumber() {
        System.out.println("Invalid number");
    }

    private static void startText(){
        System.out.println("WELCOME");
        System.out.println("Starting Bank");
    }

    private static void firstMenu(){
        System.out.println("\n******\t 1 - Create an Account  \t******");
        System.out.println("******\t 2 - Delete an Account    \t******");
        System.out.println("******\t 3 - Modify an Account    \t******");
        System.out.println("******\t 4 - Enter ATM            \t******");
        System.out.println("******\t 5 - Check all accounts   \t******");
        System.out.println("******\t 6 - Exit                 \t******\n");
    }

    private static void createAccountMenu(){
        System.out.println("******\t 1 - Add only an Account                \t******");
        System.out.println("******\t 2 - Account with Debit Card            \t******");
        System.out.println("******\t 3 - Account with Credit Card           \t******");
        System.out.println("******\t 4 - Account with Debit and Credit Card \t******");
    }

    private static void addMoney(){
        System.out.println("******\t 1 - Add Money to your Account         \t******");
        System.out.println("******\t 2 - Continue without money on Account \t******");
    }
    private static Double addMoneyToAccount(Scanner scanner) {
        System.out.println("How much money do wish to provide");
        return scanner.nextDouble();
    }

    //Dont like this solution. Tuplet seems a better approach
    private static String[] customerDetails(Scanner scanner) {
        System.out.println("Please provide your full name");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.println("\nPlease provide your tax id");
//        scanner.nextLine();
        String taxId = scanner.nextLine();
        System.out.println("Please provide your email");
//        scanner.nextLine();
        String email = scanner.nextLine();
        System.out.println("Regarding your birthday, provide the year of birth");
//        scanner.nextLine();
        String year = scanner.nextLine();
        System.out.println("Regarding your birthday, provide the month of birth");
//        scanner.nextLine();
        String month = scanner.nextLine();
        System.out.println("Regarding your birthday, provide the day of birth");
//        scanner.nextLine();
        String day = scanner.nextLine();

        return new String[]{name, taxId, email, year, month, day};
    }

    private static void deleteAccount(){
        System.out.println("Please provide account number to delete");
    }
    private static void modifyAccountMenu(){
        System.out.println("******\t 1 - Add Debit Card             \t******");
        System.out.println("******\t 2 - Add Credit Card            \t******");
        System.out.println("******\t 3 - Add Debit and Credit Cards \t******");
        System.out.println("******\t 4 - Delete Card                \t******");
        System.out.println("******\t 5 - Modify Secondary owner     \t******");
    }

    //Dont like this solution. Tuplet seems a better approach
    private static String[] credentialsCardTransferMoney(Scanner scanner){
        System.out.println("Please give the number of your card");
        scanner.nextLine();
        String cardNumber = scanner.nextLine();
        System.out.println("Please share your pin number");
        String pin = scanner.nextLine();
        System.out.println("Please indicate the account to whom you wish to transfer the money");
        String toAccount = scanner.nextLine();
        System.out.println("Please indicate how much do you want to transfer");
        String money = scanner.nextLine();

        return new String[]{cardNumber, pin, toAccount, money};
    }

    private static void accountNumber(){
        System.out.println("Please provide your account number");
    }

    private static void secondaryOwnersMenu(){
        System.out.println("******\t 1 - Add Secondary Owner    \t******");
        System.out.println("******\t 2 - Delete Secondary Owner \t******");
    }

    private static String[] secondaryOwner(Scanner scanner){
        accountNumber();
        scanner.nextLine();
        String account = scanner.nextLine();
        System.out.println("Indicate the taxId of the client you wish be secondary owner");
        String taxId = scanner.nextLine();
        return new String[]{account, taxId};
    }

    private static void cardsMenu(){
        System.out.println("******\t 1 - Delete DebitCard  \t******");
        System.out.println("******\t 2 - Delete CreditCard \t******");
        System.out.println("******\t 3 - Delete all Cards  \t******");
    }

}

