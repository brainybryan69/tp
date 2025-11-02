package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Archives all contacts currently stored in the address book.
 * <p>
 * When executed, this command transfers every {@link Person} from the active address book
 * into the archived contacts list, then clears the active address book. If the address book
 * is empty, a {@link CommandException} is thrown to signal that there are no contacts to archive.
 * <p>
 * This command returns a {@link CommandResult} with a message indicating how many contacts
 * were successfully archived.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives all contacts in the address book.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Successfully archived %d contacts.";

    public static final String MESSAGE_EMPTY_LIST =
            "The addressbook is empty and there is nothing to archive!";

    /**
     * Executes the archive command by moving all {@link Person} objects in the current
     * address book into the archived contacts list, then clearing the address book.
     *
     * @param model The {@link Model} containing the address book and archive data.
     * @return A {@link CommandResult} containing a success message with the number of archived contacts.
     * @throws CommandException If the address book contains no contacts to archive.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> persons = model.getAddressBook().getPersonList();
        int size = persons.size();

        if (persons.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }
        model.addArchivedPersons(persons);
        model.setAddressBook(new AddressBook());
        return new CommandResult(String.format(MESSAGE_SUCCESS, size));
    }
}
