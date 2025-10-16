package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TransactionTest {

    @Test
    public void constructor_positiveAmount_createsIncome() {
        Transaction transaction = new Transaction("Sales", 100.0);
        assertEquals(Transaction.Type.INCOME, transaction.getType());
        assertEquals(100.0, transaction.getTransactionAmount());
    }

    @Test
    public void constructor_negativeAmount_createsExpense() {
        Transaction transaction = new Transaction("Coffee", -50.0);
        assertEquals(Transaction.Type.EXPENSE, transaction.getType());
        assertEquals(-50.0, transaction.getTransactionAmount());
    }

    @Test
    public void constructor_zeroAmount() {
        Transaction transaction = new Transaction("Zero", 0.0);
        assertEquals(Transaction.Type.EXPENSE, transaction.getType());
        assertEquals(0.0, transaction.getTransactionAmount());
    }

    @Test
    public void constructor_withType() {
        Transaction income = new Transaction("Salary", 3000.0, Transaction.Type.INCOME);
        assertEquals(Transaction.Type.INCOME, income.getType());
        assertEquals(3000.0, income.getTransactionAmount());
        assertEquals("Salary", income.getTransactionName());

        Transaction expense = new Transaction("Rent", -1000.0, Transaction.Type.EXPENSE);
        assertEquals(Transaction.Type.EXPENSE, expense.getType());
        assertEquals(-1000.0, expense.getTransactionAmount());
        assertEquals("Rent", expense.getTransactionName());
    }

    @Test
    public void equalsSameTransaction() {
        Transaction transaction1 = new Transaction("Test", 100.0);
        Transaction transaction2 = new Transaction("Test", 100.0);
        assertTrue(transaction1.equals(transaction2));
    }

    @Test
    public void equalsSameObject() {
        Transaction transaction = new Transaction("Test", 100.0);
        assertTrue(transaction.equals(transaction));
    }

    @Test
    public void equalsNull() {
        Transaction transaction = new Transaction("Test", 100.0);
        assertFalse(transaction.equals(null));
    }

    @Test
    public void equalsDifferentClass() {
        Transaction transaction = new Transaction("Test", 100.0);
        assertFalse(transaction.equals(5));
    }

    @Test
    public void equalsDifferentName() {
        Transaction transaction1 = new Transaction("Test1", 100.0);
        Transaction transaction2 = new Transaction("Test2", 100.0);
        assertFalse(transaction1.equals(transaction2));
    }

    @Test
    public void equalsDifferentAmount() {
        Transaction transaction1 = new Transaction("Test", 100.0);
        Transaction transaction2 = new Transaction("Test", 50.0);
        assertFalse(transaction1.equals(transaction2));
    }

    @Test
    public void equalsDifferentType() {
        Transaction transaction1 = new Transaction("Test", 100.0);
        Transaction transaction2 = new Transaction("Test", -100.0);
        assertFalse(transaction1.equals(transaction2));
    }

    @Test
    public void hashCodeForEqualObjects() {
        Transaction transaction1 = new Transaction("Test", 100.0);
        Transaction transaction2 = new Transaction("Test", 100.0);
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    public void hashCodeForUnequalObjects() {
        Transaction transaction1 = new Transaction("Test1", 100.0);
        Transaction transaction2 = new Transaction("Test2", 100.0);
        assertNotEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    public void hashCodeForUnequalObjectAmounts() {
        Transaction transaction1 = new Transaction("Test", 100.0);
        Transaction transaction2 = new Transaction("Test", 200.0);
        assertNotEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    public void hashCodeForUnequalObjectsType() {
        Transaction transaction1 = new Transaction("Test", 100.0, Transaction.Type.INCOME);
        Transaction transaction2 = new Transaction("Test", 100.0, Transaction.Type.EXPENSE);
        assertNotEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    public void correctToString() {
        Transaction income = new Transaction("Sales", 100.50);
        assertEquals("Sales: $100.50 (INCOME)", income.toString());

        Transaction expense = new Transaction("Coffee", -50.25);
        assertEquals("Coffee: $50.25 (EXPENSE)", expense.toString());
    }

    @Test
    public void correctToString2() {
        Transaction income = new Transaction("Bonus", 500);
        assertEquals("Bonus: $500.00 (INCOME)", income.toString());

        Transaction expense = new Transaction("Lunch", -20);
        assertEquals("Lunch: $20.00 (EXPENSE)", expense.toString());
    }
}
