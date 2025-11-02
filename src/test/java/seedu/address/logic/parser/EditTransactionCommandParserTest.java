package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TRANSACTION_AMOUNT_DESC_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.TRANSACTION_NAME_DESC_COFFEE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.commands.EditTransactionCommand;
import seedu.address.logic.commands.EditTransactionCommand.EditTransactionDescriptor;
import seedu.address.testutil.EditTransactionDescriptorBuilder;

public class EditTransactionCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTransactionCommand.MESSAGE_USAGE);

    private static final String INVALID_NAME_MESSAGE = AddTransactionCommand.MESSAGE_INVALID_TRANSACTION_NAME;

    private EditTransactionCommandParser parser = new EditTransactionCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no person index specified
        assertParseFailure(parser, " " + "t/1" + TRANSACTION_NAME_DESC_COFFEE, MESSAGE_INVALID_FORMAT);

        // no transaction index specified
        assertParseFailure(parser, " " + "i/1" + TRANSACTION_NAME_DESC_COFFEE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, " i/1 t/1", EditTransactionCommand.MESSAGE_NOT_EDITED);

        // no person index and no field specified
        assertParseFailure(parser, " " + "t/1", MESSAGE_INVALID_FORMAT);

        // no transaction index and no field specified
        assertParseFailure(parser, " " + "i/1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative person index
        assertParseFailure(parser, " i/-5 t/1" + TRANSACTION_NAME_DESC_COFFEE,
                ParserUtil.MESSAGE_INVALID_INDEX);

        // zero person index
        assertParseFailure(parser, " i/0 t/1" + TRANSACTION_NAME_DESC_COFFEE,
                ParserUtil.MESSAGE_INVALID_INDEX);

        // negative transaction index
        assertParseFailure(parser, " i/1 t/-5" + TRANSACTION_NAME_DESC_COFFEE,
                ParserUtil.MESSAGE_INVALID_INDEX);

        // zero transaction index
        assertParseFailure(parser, " i/1 t/0" + TRANSACTION_NAME_DESC_COFFEE,
                ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix in preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = "i/" + targetIndex.getOneBased()
                + " t/1" + TRANSACTION_NAME_DESC_COFFEE + TRANSACTION_AMOUNT_DESC_COFFEE;

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().withName("Coffee")
                .withAmount(-2.5).build();
        EditTransactionCommand expectedCommand = new EditTransactionCommand(targetIndex,
                Index.fromOneBased(1), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = " i/" + targetIndex.getOneBased() + " t/1" + TRANSACTION_NAME_DESC_COFFEE;
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().withName("Coffee").build();
        EditTransactionCommand expectedCommand = new EditTransactionCommand(targetIndex,
                Index.fromOneBased(1), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amount
        userInput = " i/" + targetIndex.getOneBased() + " t/1" + TRANSACTION_AMOUNT_DESC_COFFEE;
        descriptor = new EditTransactionDescriptorBuilder().withAmount(-2.5).build();
        expectedCommand = new EditTransactionCommand(targetIndex, Index.fromOneBased(1), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // multiple names
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = " i/" + targetIndex.getOneBased() + " t/1" + TRANSACTION_NAME_DESC_COFFEE
                + TRANSACTION_NAME_DESC_COFFEE;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_TRANSACTION_NAME));

        // multiple amounts
        userInput = " i/" + targetIndex.getOneBased() + " t/1" + TRANSACTION_AMOUNT_DESC_COFFEE
                + TRANSACTION_AMOUNT_DESC_COFFEE;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_TRANSACTION_AMOUNT));

    }

    @Test
    public void parse_emptyTransactionName_failure() {
        // empty transaction name
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = " i/" + targetIndex.getOneBased() + " t/1 n/";
        assertParseFailure(parser, userInput, INVALID_NAME_MESSAGE);

        // whitespace only transaction name
        userInput = " i/" + targetIndex.getOneBased() + " t/1 n/   ";
        assertParseFailure(parser, userInput, INVALID_NAME_MESSAGE);
    }
}
