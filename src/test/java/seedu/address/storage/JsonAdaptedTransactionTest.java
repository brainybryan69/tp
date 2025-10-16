package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.Transaction;

public class JsonAdaptedTransactionTest {

    private static final String VALID_NAME = "Test Transaction";
    private static final double VALID_AMOUNT = 100.0;
    private static final String VALID_TYPE = "INCOME";

    private static final Transaction VALID_TRANSACTION = new Transaction(VALID_NAME, VALID_AMOUNT,
            Transaction.Type.INCOME);

    @Test
    public void validTransactionDetails_returnsTransaction() throws Exception {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(VALID_TRANSACTION);
        assertEquals(VALID_TRANSACTION, transaction.toModelType());
    }

    @Test
    public void nullName_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(null, VALID_AMOUNT, VALID_TYPE);
        String expectedMessage = String.format(JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT, "name");
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void emptyName_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(" ", VALID_AMOUNT, VALID_TYPE);
        String expectedMessage = "Transaction name cannot be empty";
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void zeroAmount_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(VALID_NAME, 0, VALID_TYPE);
        String expectedMessage = "Transaction amount cannot be zero";
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void nullType_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(VALID_NAME, VALID_AMOUNT, null);
        String expectedMessage = String.format(JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT, "type");
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void invalidType_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(VALID_NAME, VALID_AMOUNT, "INVALID_TYPE");
        String expectedMessage = "Invalid transaction type: INVALID_TYPE";
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void validTransaction_success() throws IllegalValueException {
        JsonAdaptedTransaction jsonAdaptedTransaction = new JsonAdaptedTransaction(VALID_TRANSACTION);
        assertEquals(VALID_NAME, jsonAdaptedTransaction.toModelType().getTransactionName());
        assertEquals(VALID_AMOUNT, jsonAdaptedTransaction.toModelType().getTransactionAmount());
        assertEquals(VALID_TYPE, jsonAdaptedTransaction.toModelType().getType().toString());
    }
}
