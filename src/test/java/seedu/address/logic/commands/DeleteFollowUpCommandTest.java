package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.followUp.FollowUp;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Contains integration and unit tests for {@code DeleteFollowUpCommand}.
 */
public class DeleteFollowUpCommandTest {

    private Model model;
    private Person samplePerson;
    private FollowUp firstFollowUp;
    private FollowUp secondFollowUp;

    @BeforeEach
    public void setUp() {
        // Create sample follow-ups
        firstFollowUp = new FollowUp("Call client about proposal", "2024-10-10");
        secondFollowUp = new FollowUp("Send project quotation", "2024-10-15");

        // Create sample person with two follow-ups
        samplePerson = new Person(
                new Name("Alice Tan"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("123, Jurong West Ave 6"),
                new HashSet<>(), // tags
                new ArrayList<>(), // transactions
                new ArrayList<>(List.of(firstFollowUp, secondFollowUp))
        );

        // Initialize model with sample person
        model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(samplePerson);
    }

    @Test
    public void execute_validIndexes_success() {
        Index personIndex = Index.fromOneBased(1);
        Index followUpIndex = Index.fromOneBased(1);

        DeleteFollowUpCommand command = new DeleteFollowUpCommand(personIndex, followUpIndex);

        Person expectedPerson = new Person(
                samplePerson.getName(),
                samplePerson.getPhone(),
                samplePerson.getEmail(),
                samplePerson.getAddress(),
                samplePerson.getTags(),
                samplePerson.getTransactions(),
                new ArrayList<>(List.of(secondFollowUp)) // first follow-up removed
        );

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
        expectedModel.addPerson(expectedPerson);

        String expectedMessage = String.format(DeleteFollowUpCommand.MESSAGE_DELETE_FOLLOWUP_SUCCESS,
                Messages.format(expectedPerson), firstFollowUp);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index invalidPersonIndex = Index.fromOneBased(5); // out of bounds
        Index followUpIndex = Index.fromOneBased(1);

        DeleteFollowUpCommand command = new DeleteFollowUpCommand(invalidPersonIndex, followUpIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidFollowUpIndex_throwsCommandException() {
        Index personIndex = Index.fromOneBased(1);
        Index invalidFollowUpIndex = Index.fromOneBased(5); // out of bounds

        DeleteFollowUpCommand command = new DeleteFollowUpCommand(personIndex, invalidFollowUpIndex);

        assertCommandFailure(command, model, DeleteFollowUpCommand.MESSAGE_INVALID_FOLLOWUP_INDEX);
    }

    @Test
    public void equals() {
        DeleteFollowUpCommand deleteFirstFollowUp = new DeleteFollowUpCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        DeleteFollowUpCommand deleteSecondFollowUp = new DeleteFollowUpCommand(Index.fromOneBased(1), Index.fromOneBased(2));

        // same object → returns true
        assertTrue(deleteFirstFollowUp.equals(deleteFirstFollowUp));

        // same values → returns true
        DeleteFollowUpCommand deleteFirstFollowUpCopy = new DeleteFollowUpCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        assertTrue(deleteFirstFollowUp.equals(deleteFirstFollowUpCopy));

        // different types → returns false
        assertFalse(deleteFirstFollowUp.equals(1));

        // null → returns false
        assertFalse(deleteFirstFollowUp.equals(null));

        // different follow-up index → returns false
        assertFalse(deleteFirstFollowUp.equals(deleteSecondFollowUp));
    }

    @Test
    public void toStringMethod() {
        Index personIndex = Index.fromOneBased(1);
        Index followUpIndex = Index.fromOneBased(1);
        DeleteFollowUpCommand command = new DeleteFollowUpCommand(personIndex, followUpIndex);
        String expected = DeleteFollowUpCommand.class.getCanonicalName()
                + "{personIndex=" + personIndex + ", followUpIndex=" + followUpIndex + "}";
        assertEquals(expected, command.toString());
    }
}


