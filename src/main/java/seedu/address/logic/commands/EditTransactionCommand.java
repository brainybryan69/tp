package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_NUMBER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Transaction;

/**
 * Edits a transaction of an existing person in the address book.
 */
public class EditTransactionCommand extends Command {

    public static final String COMMAND_WORD = "editTxn";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a transaction of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + PREFIX_INDEX + "PERSON_INDEX (must be a positive integer) "
            + PREFIX_TRANSACTION_NUMBER + "TRANSACTION_INDEX (must be a positive integer) "
            + "[" + PREFIX_TRANSACTION_NAME + "TRANSACTION_NAME] "
            + "[" + PREFIX_TRANSACTION_AMOUNT + "AMOUNT]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_INDEX + "1 "
            + PREFIX_TRANSACTION_NUMBER + "1 "
            + PREFIX_TRANSACTION_NAME + "Coffee "
            + PREFIX_TRANSACTION_AMOUNT + "-5.0";

    public static final String MESSAGE_EDIT_TRANSACTION_SUCCESS = "Edited transaction for Person: %1$s\nTransaction: %2$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_INVALID_TRANSACTION_INDEX = "The transaction index provided is invalid.";

    private final Index personIndex;
    private final Index transactionIndex;
    private final EditTransactionDescriptor editTransactionDescriptor;

    /**
     * @param personIndex of the person in the filtered person list to edit
     * @param transactionIndex of the transaction to edit
     * @param editTransactionDescriptor details to edit the transaction with
     */
    public EditTransactionCommand(Index personIndex, Index transactionIndex,
                                  EditTransactionDescriptor editTransactionDescriptor) {
        requireNonNull(personIndex);
        requireNonNull(transactionIndex);
        requireNonNull(editTransactionDescriptor);

        this.personIndex = personIndex;
        this.transactionIndex = transactionIndex;
        this.editTransactionDescriptor = editTransactionDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());
        List<Transaction> transactions = personToEdit.getTransactions();

        if (transactionIndex.getZeroBased() >= transactions.size()) {
            throw new CommandException(MESSAGE_INVALID_TRANSACTION_INDEX);
        }

        Transaction transactionToEdit = transactions.get(transactionIndex.getZeroBased());
        Transaction editedTransaction = createEditedTransaction(transactionToEdit, editTransactionDescriptor);

        List<Transaction> updatedTransactions = new ArrayList<>(personToEdit.getTransactions());
        updatedTransactions.set(transactionIndex.getZeroBased(), editedTransaction);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), updatedTransactions, personToEdit.getFollowUps());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_EDIT_TRANSACTION_SUCCESS,
                Messages.format(editedPerson), editedTransaction));
    }

    /**
     * Creates and returns a {@code Transaction} with the details of {@code transactionToEdit}
     * edited with {@code editTransactionDescriptor}.
     */
    private static Transaction createEditedTransaction(Transaction transactionToEdit,
                                                       EditTransactionDescriptor editTransactionDescriptor) {
        assert transactionToEdit != null;

        String updatedName = editTransactionDescriptor.getName().orElse(transactionToEdit.getTransactionName());
        double updatedAmount = editTransactionDescriptor.getAmount().orElse(transactionToEdit.getTransactionAmount());

        return new Transaction(updatedName, updatedAmount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditTransactionCommand)) {
            return false;
        }

        EditTransactionCommand otherCommand = (EditTransactionCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && transactionIndex.equals(otherCommand.transactionIndex)
                && editTransactionDescriptor.equals(otherCommand.editTransactionDescriptor);
    }

    /**
     * Stores the details to edit the transaction with. Each non-empty field value will replace the
     * corresponding field value of the transaction.
     */
    public static class EditTransactionDescriptor {
        private String name;
        private Double amount;

        public EditTransactionDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditTransactionDescriptor(EditTransactionDescriptor toCopy) {
            setName(toCopy.name);
            setAmount(toCopy.amount);
        }



        public void setName(String name) {
            this.name = name;
        }

        public java.util.Optional<String> getName() {
            return java.util.Optional.ofNullable(name);
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public java.util.Optional<Double> getAmount() {
            return java.util.Optional.ofNullable(amount);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditTransactionDescriptor)) {
                return false;
            }

            EditTransactionDescriptor otherDescriptor = (EditTransactionDescriptor) other;
            return getName().equals(otherDescriptor.getName())
                    && getAmount().equals(otherDescriptor.getAmount());
        }
    }
}
