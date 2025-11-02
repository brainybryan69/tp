package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Restores all archived contacts back into the active address book.
 * <p>
 * When executed, this command transfers every {@link Person} from the archive
 * back into the active address book, then clears the archive. If the archive
 * is empty, a {@link CommandException} is thrown to signal that there are no
 * contacts available to unarchive.
 * <p>
 * This command returns a {@link CommandResult} with a message indicating how
 * many contacts were successfully restored.
 */
public class UnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Restores all archived contacts back to the address book.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Successfully unarchived %d contacts.";

    public static final String MESSAGE_EMPTY_LIST =
            "The archive is empty and there is nothing to unarchive!";

    /**
     * Executes the unarchive command by moving all {@link Person} objects from
     * the archive back into the active address book, then clearing the archive.
     *
     * @param model The {@link Model} containing the address book and archive data.
     * @return A {@link CommandResult} containing a success message with the number
     *         of unarchived contacts.
     * @throws CommandException If the archive contains no contacts to restore.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> persons = model.getArchive().getPersonList();
        int size = persons.size();

        if (persons.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }
        model.addPersons(persons);
        model.setArchive(new AddressBook());
        return new CommandResult(String.format(MESSAGE_SUCCESS, size));
    }
}
