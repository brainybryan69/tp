package seedu.address.model.transaction;

public class Transaction {
    public enum Type { INCOME, EXPENSE }

    private final String transactionName;
    private final double transactionAmount;
    private final Type type;

    public Transaction(String transactionName, double transactionAmount, Type type) {
        this.transactionAmount = transactionAmount;
        this.transactionName = transactionName;
        this.type = type;
    }

    public String getTransactionName() {
        return this.transactionName;
    }

    public double getTransactionAmount() {
        return this.transactionAmount;
    }

    public Type getType() {
        return this.type;
    }

}
