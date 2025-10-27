package seedu.address.model.transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a Transaction in the address book.
 */
public class Transaction {
    /**
     * Represents the type of transaction.
     */
    public enum Type { INCOME, EXPENSE }

    private final String transactionName;
    private final double transactionAmount;
    private final Type type;

    /**
     * Constructs a {@code Transaction}.
     * Type is automatically determined: positive amount = INCOME, negative amount = EXPENSE.
     *
     * @param transactionName A valid transaction name.
     * @param transactionAmount A valid transaction amount (cannot be zero).
     */
    public Transaction(String transactionName, double transactionAmount) {
        this.transactionAmount = transactionAmount;
        this.transactionName = transactionName;
        this.type = transactionAmount > 0 ? Type.INCOME : Type.EXPENSE;
    }

    /**
     * Constructs a {@code Transaction} with explicit type.
     * This constructor is kept for backward compatibility.
     *
     * @param transactionName A valid transaction name.
     * @param transactionAmount A valid transaction amount.
     * @param type A valid transaction type.
     */
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Transaction)) {
            return false;
        }

        Transaction otherTransaction = (Transaction) other;
        return transactionName.equals(otherTransaction.transactionName)
                && Double.compare(transactionAmount, otherTransaction.transactionAmount) == 0
                && type.equals(otherTransaction.type);
    }

    @Override
    public int hashCode() {
        return transactionName.hashCode() + Double.hashCode(transactionAmount) + type.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s: $%.2f (%s)", transactionName, Math.abs(transactionAmount), type);
    }

}
