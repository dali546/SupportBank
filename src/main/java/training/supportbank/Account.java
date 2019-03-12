package training.supportbank;

import java.util.ArrayList;
import java.util.List;

class Account {

    private List<Transaction> transactions;
    private double paidIn = 0, paidOut = 0;
    private String name;

    Account(String name) {
        this.name = name;
        transactions = new ArrayList<>();
    }

    String getName() {
        return name;
    }

    double getBalance() {
        return paidIn - paidOut;
    }

    List<Transaction> getAllTransactions(){
        return transactions;
    }

    void addToPaidIn(Transaction incoming) {
        this.transactions.add(incoming);
        paidIn += incoming.getAmount();
    }

    void addToPaidOut(Transaction outgoing) {
        this.transactions.add(outgoing);
        paidOut += outgoing.getAmount();
    }
}
