package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.model.transaction.Transaction;

public class AddTransactionCommandParserTest {

    private AddTransactionCommandParser parser = new AddTransactionCommandParser();

    @Test
    public void parse_validArgs_returnsAddTransactionCommand() {
        // Positive amount = INCOME
        Transaction incomeTransaction = new Transaction("Sales revenue", 150.50);
        AddTransactionCommand incomeCommand = new AddTransactionCommand(INDEX_FIRST_PERSON, incomeTransaction);
        assertParseSuccess(parser, " i/1 n/Sales revenue a/150.50", incomeCommand);

        // Negative amount = EXPENSE
        Transaction expenseTransaction = new Transaction("Coffee beans", -150.50);
        AddTransactionCommand expenseCommand = new AddTransactionCommand(INDEX_FIRST_PERSON, expenseTransaction);
        assertParseSuccess(parser, " i/1 n/Coffee beans a/-150.50", expenseCommand);
    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddTransactionCommand.MESSAGE_USAGE);
        // missing person index
        assertParseFailure(parser, " n/Coffee beans a/150.50", expectedMessage);

        // missing transaction name
        assertParseFailure(parser, " i/1 a/150.50", expectedMessage);

        // missing amount
        assertParseFailure(parser, " i/1 n/Coffee beans", expectedMessage);

        // all fields missing
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddTransactionCommand.MESSAGE_USAGE);

        // invalid person index
        assertParseFailure(parser, " i/a n/Coffee a/150.50", ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid amount (non-numeric)
        assertParseFailure(parser, " i/1 n/Coffee a/abc", ParserUtil.MESSAGE_INVALID_TRANSACTION_FORMAT);

        // zero amount
        assertParseFailure(parser, " i/1 n/Coffee a/0", ParserUtil.MESSAGE_INVALID_TRANSACTION_AMOUNT);
        assertParseFailure(parser, " i/1 n/Coffee a/0.001", ParserUtil.MESSAGE_INVALID_TRANSACTION_AMOUNT);
        assertParseFailure(parser, " i/1 n/Coffee a/0.00000001", ParserUtil.MESSAGE_INVALID_TRANSACTION_AMOUNT);

        // empty transaction name
        assertParseFailure(parser, " i/1 n/ a/10", expectedMessage);

        // whitespace only transaction name
        assertParseFailure(parser, " i/1 n/   a/10", expectedMessage);
    }
}
