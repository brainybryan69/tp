package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Transaction;

/**
 * Summarises the cashflow from all transactions associated with all contacts.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";

    public static final String MESSAGE_SUCCESS = "Total cashflow from all contacts: %1$s";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        assert lastShownList != null : "Filtered person list cannot be null";

        double totalCashflow = 0;
        for (Person person : lastShownList) {
            assert person != null : "Person object cannot be null";
            // Assuming Person class has getTransactions() returning List<Transaction>
            // and Transaction class has getAmount() returning a double.
            assert person.getTransactions() != null : "Person's transactions list cannot be null";
            for (Transaction transaction : person.getTransactions()) {
                assert transaction != null : "Transaction object cannot be null";
                totalCashflow += transaction.getTransactionAmount();
            }
        }

        String formattedCashflow = String.format("%+.2f", totalCashflow);
        if (totalCashflow >= 0) {
            formattedCashflow = "+$" + String.format("%.2f", totalCashflow);
        } else {
            formattedCashflow = "-$" + String.format("%.2f", -totalCashflow);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, formattedCashflow));
    }
}