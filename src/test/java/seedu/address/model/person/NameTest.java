package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains disallowed special characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Name.isValidName("John S/O Peter")); // with slash (issue #153)
        assertTrue(Name.isValidName("Mary D/O Jane")); // with slash
        assertTrue(Name.isValidName("Anne-Marie")); // with hyphen
        assertTrue(Name.isValidName("O'Brien")); // with apostrophe
        assertTrue(Name.isValidName("Jean-Paul D'Arcy")); // with hyphen and apostrophe
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }

    @Test
    public void equalsIgnoreCase() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equalsIgnoreCase(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equalsIgnoreCase(name));

        // null -> returns false
        assertFalse(name.equalsIgnoreCase(null));

        // different types -> returns false
        assertFalse(name.equalsIgnoreCase(5.0f));

        // different values -> returns false
        assertFalse(name.equalsIgnoreCase(new Name("Other Valid Name")));

        assertTrue(name.equalsIgnoreCase(new Name(("valid name"))));
    }
}
