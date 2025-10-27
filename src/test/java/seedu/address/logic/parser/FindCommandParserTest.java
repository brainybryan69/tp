package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonMatchesPredicates;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefixes_throwsParseException() {
        // no prefixes at all
        assertParseFailure(parser, "Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validTagArgs_returnsFindCommand() {
        // single tag keyword
        FindCommand expectedFindCommand =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("supplier")));
        assertParseSuccess(parser, " t/supplier", expectedFindCommand);

        // multiple tag keywords
        FindCommand expectedMultiTagCommand =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("supplier", "customer")));
        assertParseSuccess(parser, " t/supplier customer", expectedMultiTagCommand);
    }

    @Test
    public void parse_validNameAndTagArgs_returnsFindCommand() {
        // both name and tag present
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        TagContainsKeywordsPredicate tagPredicate = new TagContainsKeywordsPredicate(Arrays.asList("supplier"));
        FindCommand expectedFindCommand =
                new FindCommand(new PersonMatchesPredicates(namePredicate, tagPredicate));
        assertParseSuccess(parser, " n/Alice t/supplier", expectedFindCommand);

        // multiple keywords for both
        NameContainsKeywordsPredicate multiNamePredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        TagContainsKeywordsPredicate multiTagPredicate =
                new TagContainsKeywordsPredicate(Arrays.asList("supplier", "customer"));
        FindCommand expectedMultiCommand =
                new FindCommand(new PersonMatchesPredicates(multiNamePredicate, multiTagPredicate));
        assertParseSuccess(parser, " n/Alice Bob t/supplier customer", expectedMultiCommand);
    }

    @Test
    public void parse_emptyTagValue_throwsParseException() {
        // empty tag value
        assertParseFailure(parser, " t/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // empty tag value with spaces
        assertParseFailure(parser, " t/   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyNameValue_throwsParseException() {
        // empty name value
        assertParseFailure(parser, " n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // empty name value with spaces
        assertParseFailure(parser, " n/   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleTagPrefixes_returnsFindCommand() {
        // multiple t/ prefixes with single keywords each
        FindCommand expectedFindCommand =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("supplier", "customer")));
        assertParseSuccess(parser, " t/supplier t/customer", expectedFindCommand);

        // multiple t/ prefixes with multiple keywords
        FindCommand expectedMultiCommand =
                new FindCommand(new TagContainsKeywordsPredicate(
                        Arrays.asList("supplier", "customer", "landlord", "employee")));
        assertParseSuccess(parser, " t/supplier customer t/landlord employee", expectedMultiCommand);
    }

}
