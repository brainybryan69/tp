package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteFollowUpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOLLOWUP_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

public class DeleteFollowUpCommandParserTest {

    private final DeleteFollowUpCommandParser parser = new DeleteFollowUpCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        // valid input: person index 1, follow-up index 2
        String userInput = " " + PREFIX_INDEX + "1 " + PREFIX_FOLLOWUP_INDEX + "2";

        DeleteFollowUpCommand expectedCommand =
                new DeleteFollowUpCommand(Index.fromOneBased(1), Index.fromOneBased(2));

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_fieldsOutOfOrder_success() throws Exception {
        // Even if follow-up index comes first, it should still parse correctly
        String userInput = " " + PREFIX_FOLLOWUP_INDEX + "3 " + PREFIX_INDEX + "5";

        DeleteFollowUpCommand expectedCommand =
                new DeleteFollowUpCommand(Index.fromOneBased(5), Index.fromOneBased(3));

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_missingPersonIndex_failure() {
        String userInput = " " + PREFIX_FOLLOWUP_INDEX + "2";
        ParseException e = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertTrue(e.getMessage().contains(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteFollowUpCommand.MESSAGE_USAGE)));
    }

    @Test
    public void parse_missingFollowUpIndex_failure() {
        String userInput = " " + PREFIX_INDEX + "1";
        ParseException e = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertTrue(e.getMessage().contains(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteFollowUpCommand.MESSAGE_USAGE)));
    }

    @Test
    public void parse_extraPreamble_failure() {
        String userInput = "extraText " + PREFIX_INDEX + "1 " + PREFIX_FOLLOWUP_INDEX + "2";
        ParseException e = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertTrue(e.getMessage().contains(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteFollowUpCommand.MESSAGE_USAGE)));
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String userInput = " " + PREFIX_INDEX + "1 " + PREFIX_INDEX + "2 " + PREFIX_FOLLOWUP_INDEX + "3";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidPersonIndex_failure() {
        String userInput = " " + PREFIX_INDEX + "a " + PREFIX_FOLLOWUP_INDEX + "2";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidFollowUpIndex_failure() {
        String userInput = " " + PREFIX_INDEX + "1 " + PREFIX_FOLLOWUP_INDEX + "x";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}

