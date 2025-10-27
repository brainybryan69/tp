package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOLLOWUP_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.followup.FollowUp;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Deletes a specific {@code FollowUp} from a {@code Person} identified by their index
 * in the currently displayed person list.
 */
public class DeleteFollowUpCommand extends Command {

    /** Command word to trigger this command from the CLI. */
    public static final String COMMAND_WORD = "deletefu";

    /** Usage message describing command format and example usage. */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a follow up from the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: "
            + PREFIX_INDEX + "PERSON_INDEX "
            + PREFIX_FOLLOWUP_INDEX + "FOLLOWUP_INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_FOLLOWUP_INDEX + "1";

    /** Success message shown after a follow-up is deleted. */
    public static final String MESSAGE_DELETE_FOLLOWUP_SUCCESS =
            "Deleted follow up from Person: %1$s\nFollowUp: %2$s";

    /** Error message shown when a follow-up index is invalid. */
    public static final String MESSAGE_INVALID_FOLLOWUP_INDEX = "The follow up index provided is invalid";

    public static final String MESSAGE_INVALID_PERSON_INDEX = "The person index provided is invalid";

    private final Index personIndex;
    private final Index followUpIndex;

    /**
     * Constructs a {@code DeleteFollowUpCommand}.
     *
     * @param personIndex Index of the person in the filtered person list.
     * @param followUpIndex Index of the follow-up to delete from the person's follow-up list.
     */
    public DeleteFollowUpCommand(Index personIndex, Index followUpIndex) {
        requireNonNull(personIndex);
        requireNonNull(followUpIndex);

        this.personIndex = personIndex;
        this.followUpIndex = followUpIndex;
    }

    /**
     * Executes the delete follow-up command.
     *
     * <p>Removes the specified {@code FollowUp} from the {@code Person} at {@code personIndex}
     * in the filtered person list, and updates the model accordingly.
     *
     * @param model The model which the command should operate on.
     * @return A {@code CommandResult} containing a success message.
     * @throws CommandException If the person index or follow-up index is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());

        if (personToEdit.getFollowUps().isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_INDEX);
        }

        if (followUpIndex.getZeroBased() >= personToEdit.getFollowUps().size()) {
            throw new CommandException(MESSAGE_INVALID_FOLLOWUP_INDEX);
        }

        FollowUp followUpToDelete = personToEdit.getFollowUps().get(followUpIndex.getZeroBased());
        Person editedPerson = createPersonWithoutFollowUp(personToEdit, followUpIndex);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_FOLLOWUP_SUCCESS,
                Messages.format(editedPerson), followUpToDelete));
    }

    /**
     * Creates and returns a new {@code Person} identical to the given {@code person},
     * except with the specified {@code FollowUp} removed.
     *
     * @param person The original person to copy data from.
     * @param followUpIndex Index of the follow-up to remove.
     * @return A new {@code Person} object without the specified follow-up.
     */
    private static Person createPersonWithoutFollowUp(Person person, Index followUpIndex) {
        assert person != null;

        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();

        List<FollowUp> updateFollowUps = new ArrayList<>(person.getFollowUps());
        updateFollowUps.remove(followUpIndex.getZeroBased());

        return new Person(name, phone, email, address, person.getTags(), person.getTransactions(), updateFollowUps);
    }

    /**
     * Checks if another object is equal to this {@code DeleteFollowUpCommand}.
     * Equality is based on the person index and follow-up index.
     *
     * @param other The other object to compare with.
     * @return {@code true} if both commands target the same person and follow-up; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteFollowUpCommand)) {
            return false;
        }

        DeleteFollowUpCommand otherCommand = (DeleteFollowUpCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && followUpIndex.equals(otherCommand.followUpIndex);
    }

    /**
     * Returns a string representation of this command for debugging purposes.
     *
     * @return A string representation containing the person and follow-up indices.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personIndex", personIndex)
                .add("followUpIndex", followUpIndex)
                .toString();
    }
}
