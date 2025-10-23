package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.followup.FollowUp;
import seedu.address.model.followup.FollowUpUrgency;

/**
 * Jackson-friendly version of {@link FollowUp}.
 */
public class JsonAdaptedFollowUp {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String followUpName;
    private final String urgency;

    /**
     * Constructs a {@code JsonAdaptedFollowUp} with the given follow-up details.
     *
     * @param followUpName the name or description of the follow-up
     * @param urgency the urgency level of the follow-up (e.g., "HIGH", "MEDIUM", "LOW")
     */
    @JsonCreator
    public JsonAdaptedFollowUp(@JsonProperty("followUpName") String followUpName,
                                  @JsonProperty("urgency") String urgency) {
        this.followUpName = followUpName;
        this.urgency = urgency;
    }

    /**
     * Converts a given {@code FollowUp} into this class for Jackson use.
     *
     * @param source the {@code FollowUp} object to be converted into a JSON-adapted form
     */
    public JsonAdaptedFollowUp(FollowUp source) {
        followUpName = source.getFollowUpName();
        urgency = source.getUrgency().toString();
    }

    /**
     * Converts this JSON-adapted follow-up object into the model's {@link FollowUp} object.
     *
     * @return the corresponding {@code FollowUp} object in the model
     * @throws IllegalValueException if any required field is missing or has invalid data
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
