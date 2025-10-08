package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    public void equals_sameTransaction_returnsTrue() {
        Transaction transaction1 = new Transaction("Test", 100.0);
        Transaction transaction2 = new Transaction("Test", 100.0);
        assertTrue(transaction1.equals(transaction2));
    }

    @Test
    public void equals_differentAmount_returnsFalse() {
        Transaction transaction1 = new Transaction("Test", 100.0);
        Transaction transaction2 = new Transaction("Test", 50.0);
        assertFalse(transaction1.equals(transaction2));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Transaction transaction1 = new Transaction("Test", 100.0);
        Transaction transaction2 = new Transaction("Test", -100.0);
        assertFalse(transaction1.equals(transaction2));
    }

    @Test
    public void toString_formatsCorrectly() {
        Transaction income = new Transaction("Sales", 100.50);
        assertEquals("Sales: $100.50 (INCOME)", income.toString());

        Transaction expense = new Transaction("Coffee", -50.25);
        assertEquals("Coffee: $50.25 (EXPENSE)", expense.toString());
    }
}
