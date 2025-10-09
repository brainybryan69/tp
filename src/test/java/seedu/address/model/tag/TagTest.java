package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void isValidTagType() {
        String validTagType1 = "supplier";
        String validTagType2 = "landlord";
        String validTagType3 = "delivery";
        String validTagType4 = "customer";
        String validTagType5 = "regulatory";
        String validTagType6 = "finances";
        String validTagType7 = "utility";
        String validTagType8 = "employee";
        String validTagType9 = "others";

        assertTrue(Tag.isValidTagType(validTagType1));
        assertTrue(Tag.isValidTagType(validTagType2));
        assertTrue(Tag.isValidTagType(validTagType3));
        assertTrue(Tag.isValidTagType(validTagType4));
        assertTrue(Tag.isValidTagType(validTagType5));
        assertTrue(Tag.isValidTagType(validTagType6));
        assertTrue(Tag.isValidTagType(validTagType7));
        assertTrue(Tag.isValidTagType(validTagType8));
        assertTrue(Tag.isValidTagType(validTagType9));
    }

    @Test
    public void isInvalidTagType() {
        String invalidTagName = "";
        assertFalse(Tag.isValidTagName(invalidTagName));

    }


}
