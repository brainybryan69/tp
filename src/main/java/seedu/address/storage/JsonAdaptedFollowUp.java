package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.followUp.FollowUp;
import seedu.address.model.followUp.FollowUpUrgency;
import seedu.address.model.transaction.Transaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonAdaptedFollowUp {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String followUpName;
    private final String urgency;

    /**
     * Constructs a {@code JsonAdaptedTransaction} with the given transaction details.
     */
    @JsonCreator
    public JsonAdaptedFollowUp(@JsonProperty("followUpName") String followUpName,
                                  @JsonProperty("urgency") String urgency) {
        this.followUpName = followUpName;
        this.urgency = urgency;
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     */
    public JsonAdaptedFollowUp(FollowUp source) {
        followUpName = source.getFollowUpName();
        urgency = source.getUrgency().toString();
    }

    /**
     * Converts this Json-friendly adapted FollowUp object into the model's {@code Transaction} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted transaction.
     */
    public FollowUp toModelType() throws IllegalValueException {
        if (followUpName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }

        if (followUpName.trim().isEmpty()) {
            throw new IllegalValueException("Transaction name cannot be empty");
        }

        if (urgency == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "type"));
        }

        FollowUpUrgency urgencyTest;
        try {
            urgencyTest = FollowUpUrgency.valueOf(urgency.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Invalid urgency type: " + urgency);
        }

        return new FollowUp(followUpName, urgency);
    }
}
