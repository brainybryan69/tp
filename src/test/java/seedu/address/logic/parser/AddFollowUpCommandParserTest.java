package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddFollowUpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.followup.FollowUp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOLLOWUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOLLOWUP_URGENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import org.junit.jupiter.api.Test;

public class AddFollowUpCommandParserTest {
    private AddFollowUpCommandParser parser = new AddFollowUpCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        // Given valid arguments with all required prefixes
        String userInput = " " + PREFIX_INDEX + "1 "
                + PREFIX_FOLLOWUP_NAME + "Call patient "
                + PREFIX_FOLLOWUP_URGENCY + "HIGH";

        Index expectedIndex = Index.fromOneBased(1);
        FollowUp expectedFollowUp = new FollowUp("Call patient", "HIGH");
        AddFollowUpCommand expectedCommand = new AddFollowUpCommand(expectedIndex, expectedFollowUp);

        // When parsing the input
        AddFollowUpCommand actualCommand = parser.parse(userInput);

        // Then the command should match the expected command
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        // Given input missing the index prefix
        String userInput = " "
                + PREFIX_FOLLOWUP_NAME + "Call patient "
                + PREFIX_FOLLOWUP_URGENCY + "HIGH";

        // When parsing the input, then expect a ParseException
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingName_throwsParseException() {
        // Given input missing the name prefix
        String userInput = " " + PREFIX_INDEX + "1 "
                + PREFIX_FOLLOWUP_URGENCY + "HIGH";

        // When parsing the input, then expect a ParseException
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingUrgency_throwsParseException() {
        // Given input missing the urgency prefix
        String userInput = " " + PREFIX_INDEX + "1 "
                + PREFIX_FOLLOWUP_NAME + "Call patient ";

        // When parsing the input, then expect a ParseException
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() {
        // Given input with invalid text in the preamble
        String userInput = "some random text " + PREFIX_INDEX + "1 "
                + PREFIX_FOLLOWUP_NAME + "Call patient "
                + PREFIX_FOLLOWUP_URGENCY + "HIGH";

        // When parsing the input, then expect a ParseException
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Given input with an invalid index (non-numeric)
        String userInput = " " + PREFIX_INDEX + "abc "
                + PREFIX_FOLLOWUP_NAME + "Call patient "
                + PREFIX_FOLLOWUP_URGENCY + "HIGH";

        // When parsing the input, then expect a ParseException
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicateFields_throwsParseException() {
        // Given input with duplicate fields
        String userInput = " " + PREFIX_INDEX + "1 "
                + PREFIX_FOLLOWUP_NAME + "Call patient "
                + PREFIX_FOLLOWUP_NAME + "Another name "
                + PREFIX_FOLLOWUP_URGENCY + "HIGH";

        // When parsing the input, then expect a ParseException
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
