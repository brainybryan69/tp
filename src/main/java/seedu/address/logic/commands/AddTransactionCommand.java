package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_NAME;
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
 * Adds a transaction to an existing person in the address book.
 */
public class AddTransactionCommand extends Command {

    public static final String COMMAND_WORD = "addtxn";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction to the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_TRANSACTION_NAME + "TRANSACTION_NAME "
            + PREFIX_TRANSACTION_AMOUNT + "AMOUNT (positive for income, negative for expense)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_TRANSACTION_NAME + "Coffee beans "
            + PREFIX_TRANSACTION_AMOUNT + "-150.50 (expense) OR "
            + PREFIX_INDEX + "1 "
            + PREFIX_TRANSACTION_NAME + "Sales revenue "
            + PREFIX_TRANSACTION_AMOUNT + "500.00 (income)";

    public static final String MESSAGE_INVALID_TRANSACTION_NAME = "The transaction name provided is invalid.";

    public static final String MESSAGE_ADD_TRANSACTION_SUCCESS = "Added transaction to Person: %1$s\nTransaction: %2$s";

    private final Index index;
    private final Transaction transaction;

    /**
     * @param index of the person in the filtered person list to add transaction to
     * @param transaction to add to the person
     */
    public AddTransactionCommand(Index index, Transaction transaction) {
        requireNonNull(index);
        requireNonNull(transaction);

        this.index = index;
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_PERSON_INDEX_OUT_OF_BOUNDS);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createPersonWithTransaction(personToEdit, transaction);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_TRANSACTION_SUCCESS,
                Messages.format(editedPerson), transaction));
    }

    /**
     * Creates and returns a {@code Person} with the new transaction added.
     */
    private static Person createPersonWithTransaction(Person person, Transaction transaction) {
        assert person != null;

        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();

        List<Transaction> updatedTransactions = new ArrayList<>(person.getTransactions());
        updatedTransactions.add(transaction);

        return new Person(name, phone, email, address, person.getTags(), updatedTransactions, person.getFollowUps());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddTransactionCommand)) {
            return false;
        }

        AddTransactionCommand otherCommand = (AddTransactionCommand) other;
        return index.equals(otherCommand.index)
                && transaction.equals(otherCommand.transaction);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("transaction", transaction)
                .toString();
    }
}
