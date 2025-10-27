package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.followup.FollowUp;

public class JsonAdaptedFollowUpTest {

    private static final String VALID_NAME = "Call client about renewal";
    private static final String VALID_URGENCY = "HIGH";

    private static final String INVALID_URGENCY = "EXTREME";
    private static final String EMPTY_NAME = "   ";
    private static final FollowUp FOLLOW_UP = new FollowUp(VALID_NAME, VALID_URGENCY);

    @Test
    public void toModelType_validFollowUpDetails_success() throws Exception {
        JsonAdaptedFollowUp adapted = new JsonAdaptedFollowUp(VALID_NAME, VALID_URGENCY);
        FollowUp modelFollowUp = adapted.toModelType();
        assertEquals(VALID_NAME, modelFollowUp.getFollowUpName());
        assertEquals(VALID_URGENCY, modelFollowUp.getUrgency().toString());
    }

    @Test
    public void toModelType_validFollowUpDetailsUsingOtherConstructor_success() throws Exception {
        JsonAdaptedFollowUp adapted = new JsonAdaptedFollowUp(FOLLOW_UP);
        FollowUp modelFollowUp = adapted.toModelType();
        assertEquals(VALID_NAME, modelFollowUp.getFollowUpName());
        assertEquals(VALID_URGENCY, modelFollowUp.getUrgency().toString());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedFollowUp adapted = new JsonAdaptedFollowUp(null, VALID_URGENCY);
        IllegalValueException e = assertThrows(IllegalValueException.class, adapted::toModelType);
        assertTrue(e.getMessage().contains("name"));
    }

    @Test
    public void toModelType_emptyName_throwsIllegalValueException() {
        JsonAdaptedFollowUp adapted = new JsonAdaptedFollowUp(EMPTY_NAME, VALID_URGENCY);
        IllegalValueException e = assertThrows(IllegalValueException.class, adapted::toModelType);
        assertEquals("Transaction name cannot be empty", e.getMessage());
    }

    @Test
    public void toModelType_nullUrgency_throwsIllegalValueException() {
        JsonAdaptedFollowUp adapted = new JsonAdaptedFollowUp(VALID_NAME, null);
        IllegalValueException e = assertThrows(IllegalValueException.class, adapted::toModelType);
        assertTrue(e.getMessage().contains("type"));
    }

    @Test
    public void toModelType_invalidUrgency_throwsIllegalValueException() {
        JsonAdaptedFollowUp adapted = new JsonAdaptedFollowUp(VALID_NAME, INVALID_URGENCY);
        IllegalValueException e = assertThrows(IllegalValueException.class, adapted::toModelType);
        assertTrue(e.getMessage().contains("Invalid urgency type"));
    }
}
