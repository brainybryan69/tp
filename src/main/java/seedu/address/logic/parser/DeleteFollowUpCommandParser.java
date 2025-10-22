package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteFollowUpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOLLOWUP_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

public class DeleteFollowUpCommandParser implements Parser<DeleteFollowUpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteFollowUpCommand
     * and returns a DeleteTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteFollowUpCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_FOLLOWUP_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_FOLLOWUP_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteFollowUpCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX, PREFIX_FOLLOWUP_INDEX);

        Index personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        Index followUpIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_FOLLOWUP_INDEX).get());

        return new DeleteFollowUpCommand(personIndex, followUpIndex);
    }

    /**
     * Returns true if all given prefixes contain non-empty values in the {@code ArgumentMultimap}.
     *
     * @param argumentMultimap the map of argument prefixes to their values
     * @param prefixes prefixes to check for presence
     * @return true if all prefixes are present and have associated values
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
