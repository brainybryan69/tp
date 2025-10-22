package seedu.address.model.followUp;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a follow-up action item for a person in the address book.
 * A follow-up has a name (description of the action required) and an urgency level.
 * Guarantees: immutable; name and urgency are valid as per described constraints.
 */
public class FollowUp {
    /** Error message for invalid urgency. */
    private static final String MESSAGE_CONSTRAINTS = "Urgency should be either high, medium or low(case insensitive)";

    private final String followUpName;
    private final FollowUpUrgency urgency;

     /**
     * Constructs a follow-up with the specified name and urgency.
     *
     * @param followUpName The name or description of the follow-up action.
     * @param urgency The urgency level of the follow-up (must be "high", "medium", or "low", case insensitive).
     * @throws NullPointerException if followUpName or urgency is null.
     * @throws IllegalArgumentException if the urgency is not valid.
     */
    public FollowUp(String followUpName, String urgency) {
        requireNonNull(followUpName);
        requireNonNull(urgency);
        urgency = urgency.trim();
        checkArgument(isValidUrgency(urgency), MESSAGE_CONSTRAINTS);

        this.followUpName = followUpName.trim();
        this.urgency = FollowUpUrgency.valueOf(urgency.toUpperCase());
    }

    /**
     * Gets the name or description of the follow-up.
     *
     * @return The follow-up name.
     */
    public String getFollowUpName() {
        return followUpName;
    }

    /**
     * Gets the urgency level of the follow-up.
     *
     * @return The urgency of the follow-up.
     */
    public FollowUpUrgency getUrgency() {
        return urgency;
    }

    /**
     * Validates if the given string is a valid urgency level.
     *
     * @param urgency The urgency string to validate.
     * @return true if the string is a valid urgency level (high, medium, or low, case insensitive).
     */
    private static boolean isValidUrgency(String urgency) {
        try {
            FollowUpUrgency.valueOf(urgency.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Compares this follow-up with another object for equality.
     * Two follow-ups are equal if they have the same name and urgency level.
     *
     * @param other The object to compare with.
     * @return true if the objects are the same or have the same name and urgency level.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FollowUp)) {
            return false;
        }

        FollowUp otherFollowUp = (FollowUp) other;
        return followUpName.equals(otherFollowUp.followUpName)
                && urgency.equals(otherFollowUp.urgency);
    }

    /**
     * Returns the hash code of this follow-up.
     *
     * @return The hash code value, derived from the name and urgency.
     */
    @Override
    public int hashCode() {
        return followUpName.hashCode() + urgency.hashCode();
    }

    /**
     * Returns a string representation of this follow-up.
     *
     * @return The name of the follow-up.
     */
    @Override
    public String toString() {
        return followUpName;
    }
}
