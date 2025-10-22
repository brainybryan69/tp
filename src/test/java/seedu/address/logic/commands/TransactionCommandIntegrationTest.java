package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Transaction;

/**
 * Integration tests for transaction commands.
 */
public class TransactionCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalAddressBook());
    }

    @Test
    public void execute_addTransaction_success() throws Exception {
        Index personIndex = Index.fromOneBased(1);
        Transaction transaction = new Transaction("Coffee beans", -150.50);
        AddTransactionCommand command = new AddTransactionCommand(personIndex, transaction);

        Person personBefore = model.getFilteredPersonList().get(personIndex.getZeroBased());
        int transactionCountBefore = personBefore.getTransactions().size();

        CommandResult result = command.execute(model);

        Person personAfter = model.getFilteredPersonList().get(personIndex.getZeroBased());
        int transactionCountAfter = personAfter.getTransactions().size();

        assertEquals(transactionCountBefore + 1, transactionCountAfter);
        assertEquals(transaction, personAfter.getTransactions().get(transactionCountAfter - 1));
    }

    @Test
    public void execute_deleteTransaction_success() throws Exception {
        // First add a transaction
        Index personIndex = Index.fromOneBased(1);
        Transaction transaction = new Transaction("Test transaction", -100.0);
        AddTransactionCommand addCommand = new AddTransactionCommand(personIndex, transaction);
        addCommand.execute(model);

        // Now delete it
        Person personBefore = model.getFilteredPersonList().get(personIndex.getZeroBased());
        int transactionCountBefore = personBefore.getTransactions().size();

        Index transactionIndex = Index.fromOneBased(transactionCountBefore);
        DeleteTransactionCommand deleteCommand = new DeleteTransactionCommand(personIndex, transactionIndex);
        CommandResult result = deleteCommand.execute(model);

        Person personAfter = model.getFilteredPersonList().get(personIndex.getZeroBased());
        int transactionCountAfter = personAfter.getTransactions().size();

        assertEquals(transactionCountBefore - 1, transactionCountAfter);
    }

    @Test
    public void execute_addTransactionInvalidIndex_throwsCommandException() {
        Index invalidIndex = Index.fromOneBased(999);
        Transaction transaction = new Transaction("Test", 100.0);
        AddTransactionCommand command = new AddTransactionCommand(invalidIndex, transaction);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_deleteTransactionInvalidPersonIndex_throwsCommandException() {
        Index invalidPersonIndex = Index.fromOneBased(999);
        Index transactionIndex = Index.fromOneBased(1);
        DeleteTransactionCommand command = new DeleteTransactionCommand(invalidPersonIndex, transactionIndex);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_deleteTransactionInvalidTransactionIndex_throwsCommandException() throws Exception {
        // First add a transaction
        Index personIndex = Index.fromOneBased(1);
        Transaction transaction = new Transaction("Test", -100.0);
        AddTransactionCommand addCommand = new AddTransactionCommand(personIndex, transaction);
        addCommand.execute(model);

        // Try to delete with invalid transaction index
        Index invalidTransactionIndex = Index.fromOneBased(999);
        DeleteTransactionCommand deleteCommand = new DeleteTransactionCommand(personIndex, invalidTransactionIndex);

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }
}
