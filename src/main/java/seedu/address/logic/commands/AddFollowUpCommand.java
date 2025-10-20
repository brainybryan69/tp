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
import seedu.address.model.followUp.FollowUp;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Adds a person to the address book.
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
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddFollowUpCommand(Index index, FollowUp followUp) {
        requireNonNull(followUp);
        this.index = index;
        this.followUp = followUp;
    }

    /**
     * Creates and returns a {@code Person} with the new transaction added.
     */
    private static Person createPersonWithNewFollowUp(Person person, FollowUp followUp) {
        assert person != null;

        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();

        List<FollowUp> updatedFollowUps= new ArrayList<>(person.getFollowUps());
        updatedFollowUps.add(followUp);

        return new Person(name, phone, email, address, person.getTags(), person.getTransactions(), updatedFollowUps);
    }

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("transaction", followUp)
                .toString();
    }
}

