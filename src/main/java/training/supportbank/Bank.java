package training.supportbank;

import java.util.ArrayList;
import java.util.List;

import static training.supportbank.Main.LOGGER;

class Bank {

    private final String name;
    private List<Account> accounts;

    Bank(String name) {
        this.name = name;
        accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    List<Account> getAccounts() {
        return accounts;
    }

    void distributeTransactions(List<Transaction> transactions) {
        LOGGER.info("Starting to assign transactions");
        transactions.forEach(this::assign);
    }

    boolean isAccountExist(String name) {
        return accounts.stream().anyMatch(account -> account.getName().equals(name));
    }

    private void assign(Transaction transaction) {
        String from = transaction.getFrom();
        String to = transaction.getTo();
        LOGGER.debug("Assigning Transaction From " + transaction.getFrom()+ " To " + transaction.getTo());
        if (!isAccountExist(from)) createAccount(from);
        if (!isAccountExist(to)) createAccount(to);

        for (Account account : getAccounts()) {
            if (account.getName().equals(from)) account.addToPaidOut(transaction);
            if (account.getName().equals(to)) account.addToPaidIn(transaction);
        }
    }

    private void createAccount(String from) {
        Account account = new Account(from);
        addAccount(account);
    }

    private void addAccount(Account account) {
        accounts.add(account);
    }
}

