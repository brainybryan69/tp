package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_SUCCESS = "Successfully archived %d contacts.";

    public static final String MESSAGE_EMPTY_LIST =
            "The addressbook is empty and there is nothing to archive!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> persons = model.getAddressBook().getPersonList();

        if (persons.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }
        model.addPersons(persons);
        model.setAddressBook(new AddressBook());
        return new CommandResult("Archive Success!");
    }
}
