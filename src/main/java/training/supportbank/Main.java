package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    static final Logger LOGGER = LogManager.getLogger();
    private static Bank supportBank;

    public static void main(String[] args) throws IOException {
        try {
            LOGGER.info("Application Started.");
            supportBank = new Bank("SupportBank");
            LOGGER.info("Bank Initialised");
            supportBank.distributeTransactions(FileReader.load());
            LOGGER.info(supportBank.getName() + " has completed distributing Transactions.");
            printWelcomeMessage();
            LOGGER.info("Waiting for User input...");
            Scanner scanner = new Scanner(System.in);
            LOGGER.info("Initialised Scanner...");
            int o = getValidOption(scanner);
            LOGGER.debug("User has selected Option " + o);
            loadData(o, scanner);
            LOGGER.info("Program Finished.");
        } catch (Exception e) {
            LOGGER.error("Unhandled exception", e);
            throw e;
        }
    }

    private static void loadData(int o, Scanner scanner) {
        if (o==1) printAllAccounts();
        if (o==2) printAccountDetails(askUserForAccountName(scanner));
    }

    private static void printAccountDetails(String accountName) {
        if (supportBank.isAccountExist(accountName)) {
            supportBank.getAccounts().forEach(account -> {
                if (account.getName().equals(accountName)) {
                    System.out.println("Account Name: " + account.getName());
                    System.out.println("Transaction List");
                    printTransactions(account);
                }
            });
        } else {
            System.out.println("No Account Exists with that name.");
        }
    }

    private static void printTransactions(Account account) {
        account.getAllTransactions().forEach(transaction ->
                System.out.println("From: " + transaction.getFrom() +
                        ". To: " + transaction.getTo() +
                        ". Amount: " + new DecimalFormat("£0.00").format(transaction.getAmount()) +
                        ". Note: " + transaction.getNote() +
                        ". Date: " + transaction.getDate()
                )
        );

    }

    private static String askUserForAccountName(Scanner scanner) {
        System.out.println("Enter Account Name: ");
        return scanner.nextLine();
    }

    private static void printAllAccounts() {
        supportBank.getAccounts().forEach(account ->
                System.out.println("Account Name: " + account.getName() +
                        ". Total Transactions: " + account.getAllTransactions().size() +
                        ". Balance: " + new DecimalFormat("£0.00").format(account.getBalance())
                )
        );
        System.out.println("Total: " + new DecimalFormat("£0.00").format(
                supportBank.getAccounts().stream().mapToDouble(Account::getBalance).sum())
        );

    }

    private static int getValidOption(Scanner scanner) {
        String s;
        do {
            s = scanner.nextLine();
            if (s.matches("[12]")) return Integer.parseInt(s);
            System.out.println("Invalid option. Try Again");
            printOptions();
        } while (!s.matches("[12]"));
        return Integer.parseInt(s);
    }

    private static void printWelcomeMessage() {
        System.out.println(supportBank.getName());
        System.out.println("####################");
        System.out.println("Welcome to the Bank How may I help you?");
        printOptions();
    }

    private static void printOptions() {
        System.out.println("1. List All Accounts");
        System.out.println("2. List Specific Account");
    }

}
