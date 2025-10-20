package seedu.address.model.followUp;

import seedu.address.model.tag.TagType;
import seedu.address.model.transaction.Transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class FollowUp {
    /** Error message for invalid urgency. */
    private static final String MESSAGE_CONSTRAINTS = "Urgency should be either high, medium or low(case insensitive)";

    private final String followUpName;
    private final FollowUpUrgency urgency;

    public FollowUp(String followUpName, String urgency) {
        requireNonNull(followUpName);
        requireNonNull(urgency);
        urgency = urgency.trim();
        checkArgument(isValidUrgency(urgency), MESSAGE_CONSTRAINTS);

        this.followUpName = followUpName.trim();
        this.urgency = FollowUpUrgency.valueOf(urgency.toUpperCase());
    }

    public String getFollowUpName() {
        return followUpName;
    }

    public FollowUpUrgency getUrgency() {
        return urgency;
    }

    private static boolean isValidUrgency(String urgency) {
        try {
            FollowUpUrgency.valueOf(urgency.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

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

    @Override
    public int hashCode() {
        return followUpName.hashCode() + urgency.hashCode();
    }

    @Override
    public String toString() {
        return followUpName;
//        return String.format("%s", followUpName);
    }
}
