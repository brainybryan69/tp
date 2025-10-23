package seedu.address.model.followup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link FollowUp} class.
 */
public class FollowUpTest {

    @Test
    public void constructor_nullFollowUpName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FollowUp(null, "high"));
    }

    @Test
    public void constructor_nullUrgency_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FollowUp("Test follow-up", null));
    }

    @Test
    public void constructor_invalidUrgency_throwsIllegalArgumentException() {
        String invalidUrgency = "urgent"; // Not one of HIGH, MEDIUM, LOW
        assertThrows(IllegalArgumentException.class, () -> new FollowUp("Test follow-up", invalidUrgency));
    }

    @Test
    public void constructor_validInputs_createsFollowUp() {
        // Test with HIGH urgency
        FollowUp followUpHigh = new FollowUp("High priority follow-up", "high");
        assertEquals("High priority follow-up", followUpHigh.getFollowUpName());
        assertEquals(FollowUpUrgency.HIGH, followUpHigh.getUrgency());

        // Test with MEDIUM urgency
        FollowUp followUpMedium = new FollowUp("Medium priority follow-up", "MEDIUM");
        assertEquals("Medium priority follow-up", followUpMedium.getFollowUpName());
        assertEquals(FollowUpUrgency.MEDIUM, followUpMedium.getUrgency());

        // Test with LOW urgency
        FollowUp followUpLow = new FollowUp("Low priority follow-up", "Low");
        assertEquals("Low priority follow-up", followUpLow.getFollowUpName());
        assertEquals(FollowUpUrgency.LOW, followUpLow.getUrgency());

        // Test with mixed case
        FollowUp followUpMixedCase = new FollowUp("Mixed case follow-up", "MeDiUm");
        assertEquals("Mixed case follow-up", followUpMixedCase.getFollowUpName());
        assertEquals(FollowUpUrgency.MEDIUM, followUpMixedCase.getUrgency());

        // Test with spaces
        FollowUp followUpWithSpaces = new FollowUp("  Trimmed follow-up  ", "  high  ");
        assertEquals("Trimmed follow-up", followUpWithSpaces.getFollowUpName());
        assertEquals(FollowUpUrgency.HIGH, followUpWithSpaces.getUrgency());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        FollowUp followUp1 = new FollowUp("Test follow-up", "high");
        FollowUp followUp2 = new FollowUp("Test follow-up", "high");

        // Same follow-up name and urgency
        assertTrue(followUp1.equals(followUp2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        FollowUp followUpOriginal = new FollowUp("Original follow-up", "high");

        // Different follow-up name
        FollowUp followUpDifferentName = new FollowUp("Different follow-up", "high");
        assertFalse(followUpOriginal.equals(followUpDifferentName));

        // Different urgency
        FollowUp followUpDifferentUrgency = new FollowUp("Original follow-up", "medium");
        assertFalse(followUpOriginal.equals(followUpDifferentUrgency));

        // Different name and urgency
        FollowUp followUpBothDifferent = new FollowUp("Different follow-up", "low");
        assertFalse(followUpOriginal.equals(followUpBothDifferent));

        // Different object type
        assertFalse(followUpOriginal.equals("Not a FollowUp object"));

        // Null object
        assertFalse(followUpOriginal.equals(null));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        FollowUp followUp1 = new FollowUp("Test follow-up", "high");
        FollowUp followUp2 = new FollowUp("Test follow-up", "high");

        assertEquals(followUp1.hashCode(), followUp2.hashCode());
    }

    @Test
    public void hashCode_differentValues_differentHashCode() {
        FollowUp followUp1 = new FollowUp("First follow-up", "high");
        FollowUp followUp2 = new FollowUp("Second follow-up", "low");

        assertNotEquals(followUp1.hashCode(), followUp2.hashCode());
    }

    @Test
    public void toString_returnsFollowUpName() {
        FollowUp followUp = new FollowUp("Test follow-up", "high");
        assertEquals("Test follow-up", followUp.toString());
    }
}
