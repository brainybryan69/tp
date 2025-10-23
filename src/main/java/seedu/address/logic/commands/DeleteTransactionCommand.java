package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_NUMBER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.transaction.Transaction;

/**
 * Deletes a transaction from an existing person in the address book.
 */
public class DeleteTransactionCommand extends Command {

    public static final String COMMAND_WORD = "deletetxn";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a transaction from the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: "
            + PREFIX_INDEX + "PERSON_INDEX "
            + PREFIX_TRANSACTION_NUMBER + "TRANSACTION_INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_TRANSACTION_NUMBER + "1";

    public static final String MESSAGE_DELETE_TRANSACTION_SUCCESS =
            "Deleted transaction from Person: %1$s\nTransaction: %2$s";
    public static final String MESSAGE_INVALID_TRANSACTION_INDEX = "The transaction index provided is invalid";

    private final Index personIndex;
    private final Index transactionIndex;

    /**
     * @param personIndex of the person in the filtered person list to delete transaction from
     * @param transactionIndex of the transaction in the person's transaction list to delete
     */
    public DeleteTransactionCommand(Index personIndex, Index transactionIndex) {
        requireNonNull(personIndex);
        requireNonNull(transactionIndex);

        this.personIndex = personIndex;
        this.transactionIndex = transactionIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());

        if (transactionIndex.getZeroBased() >= personToEdit.getTransactions().size()) {
            throw new CommandException(MESSAGE_INVALID_TRANSACTION_INDEX);
        }

        Transaction transactionToDelete = personToEdit.getTransactions().get(transactionIndex.getZeroBased());
        Person editedPerson = createPersonWithoutTransaction(personToEdit, transactionIndex);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_TRANSACTION_SUCCESS,
                Messages.format(editedPerson), transactionToDelete));
    }

    /**
     * Creates and returns a {@code Person} with the specified transaction removed.
     */
    private static Person createPersonWithoutTransaction(Person person, Index transactionIndex) {
        assert person != null;

        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();

        List<Transaction> updatedTransactions = new ArrayList<>(person.getTransactions());
        updatedTransactions.remove(transactionIndex.getZeroBased());

        return new Person(name, phone, email, address, person.getTags(), updatedTransactions, person.getFollowUps());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteTransactionCommand)) {
            return false;
        }

        DeleteTransactionCommand otherCommand = (DeleteTransactionCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && transactionIndex.equals(otherCommand.transactionIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personIndex", personIndex)
                .add("transactionIndex", transactionIndex)
                .toString();
    }
}
