package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOLLOWUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOLLOWUP_URGENCY;
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
 * Represents a command to add a follow-up action to a person in the address book.
 *
 * This command allows users to add follow-up tasks associated with specific contacts,
 * with customizable details such as task description and urgency level.
 *
 * Follow-up actions are stored within the Person object and can be viewed along with
 * the person's contact information.
 *
 * The command requires an index to identify the target person and follow-up details
 * including name and urgency level.
 */
public class AddFollowUpCommand extends Command {

    public static final String COMMAND_WORD = "addfu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a follow up to the person identified. "
             + "by the index number used in the displayed person list.\n"
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_FOLLOWUP_NAME + "FOLLOWUP_NAME "
            + PREFIX_FOLLOWUP_URGENCY + "URGENCY (High, Medium or Low case insensitive)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_FOLLOWUP_NAME + "Get price estimate for aircon repair"
            + PREFIX_FOLLOWUP_URGENCY + "High OR "
            + PREFIX_INDEX + "2 "
            + PREFIX_FOLLOWUP_NAME + "Ask about bread prices"
            + PREFIX_FOLLOWUP_URGENCY + "Medium";

    public static final String MESSAGE_SUCCESS = "Added follow up to Person: %1$s\nFollowUp: %2$s";

    private final Index index;
    private final FollowUp followUp;

    /**
     * Creates an AddFollowUpCommand to add the specified follow-up to a person.
     *
     * @param index The index of the person in the filtered person list to add the follow-up to.
     * @param followUp The follow-up action to add to the specified person.
     */
    public AddFollowUpCommand(Index index, FollowUp followUp) {
        requireNonNull(followUp);
        this.index = index;
        this.followUp = followUp;
    }

    /**
     * Creates and returns a new {@code Person} with the specified follow-up action added.
     *
     * The method preserves all other attributes of the original person (name, phone, email, etc.)
     * while adding a new follow-up to the person's list of follow-ups.
     *
     * @param person The original person to which the follow-up will be added.
     * @param followUp The follow-up action to add to the person.
     * @return A new Person object with all original attributes plus the new follow-up.
     */
    private static Person createPersonWithNewFollowUp(Person person, FollowUp followUp) {
        assert person != null;

        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();

        List<FollowUp> updatedFollowUps = new ArrayList<>(person.getFollowUps());
        updatedFollowUps.add(followUp);

        return new Person(name, phone, email, address, person.getTags(), person.getTransactions(), updatedFollowUps);
    }

    /**
     * Executes the command to add a follow-up to the specified person.
     *
     * The execution process:
     * 1. Validates that the index is within the bounds of the filtered person list.
     * 2. Retrieves the target person from the list.
     * 3. Creates a new person with the follow-up added.
     * 4. Updates the model with the edited person.
     * 5. Returns a success message with details of the person and the added follow-up.
     *
     * @param model The model which the command should operate on.
     * @return A CommandResult containing a success message with the edited person and added follow-up details.
     * @throws CommandException If the index is invalid or if an error occurs during execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createPersonWithNewFollowUp(personToEdit, followUp);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                Messages.format(editedPerson), followUp));
    }

    /**
     * Compares this AddFollowUpCommand with another object for equality.
     *
     * Two AddFollowUpCommand objects are considered equal if they have the same index
     * and follow-up details.
     *
     * @param other The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddFollowUpCommand)) {
            return false;
        }

        AddFollowUpCommand otherAddFollowUpCommand = (AddFollowUpCommand) other;
        return index.equals(otherAddFollowUpCommand.index)
                && followUp.equals(otherAddFollowUpCommand.followUp);
    }

    /**
     * Returns a string representation of this AddFollowUpCommand.
     *
     * The string includes the index and the follow-up details.
     *
     * @return A string representation of this command.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("transaction", followUp)
                .toString();
    }
}

