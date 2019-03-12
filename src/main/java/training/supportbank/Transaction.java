package training.supportbank;

import java.time.LocalDate;

class Transaction{
    private LocalDate date;
    private String from,to,note;
    private double amount;

    Transaction(LocalDate date, String from, String to, String note, double amount) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.note = note;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    String getFrom() {
        return from;
    }

    String getTo() {
        return to;
    }

    public String getNote() {
        return note;
    }

    double getAmount() {
        return amount;
    }

}
