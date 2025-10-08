package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.Transaction;

/**
 * Jackson-friendly version of {@link Transaction}.
 */
class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String transactionName;
    private final double transactionAmount;
    private final String type;

    /**
     * Constructs a {@code JsonAdaptedTransaction} with the given transaction details.
     */
    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("transactionName") String transactionName,
                                   @JsonProperty("transactionAmount") double transactionAmount,
                                   @JsonProperty("type") String type) {
        this.transactionName = transactionName;
        this.transactionAmount = transactionAmount;
        this.type = type;
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     */
    public JsonAdaptedTransaction(Transaction source) {
        transactionName = source.getTransactionName();
        transactionAmount = source.getTransactionAmount();
        type = source.getType().toString();
    }

    /**
     * Converts this Jackson-friendly adapted transaction object into the model's {@code Transaction} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted transaction.
     */
    public Transaction toModelType() throws IllegalValueException {
        if (transactionName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }

        if (transactionName.trim().isEmpty()) {
            throw new IllegalValueException("Transaction name cannot be empty");
        }

        if (transactionAmount == 0) {
            throw new IllegalValueException("Transaction amount cannot be zero");
        }

        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "type"));
        }

        Transaction.Type transactionType;
        try {
            transactionType = Transaction.Type.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Invalid transaction type: " + type);
        }

        return new Transaction(transactionName, transactionAmount, transactionType);
    }

}
