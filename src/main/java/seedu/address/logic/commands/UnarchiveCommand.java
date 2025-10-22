package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


import java.util.List;

import static java.util.Objects.requireNonNull;

public class UnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_SUCCESS = "Successfully unarchived %d contacts.";

    public static final String MESSAGE_EMPTY_LIST =
            "The archive is empty and there is nothing to unarchive!";

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
