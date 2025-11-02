package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOLLOWUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOLLOWUP_URGENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddFollowUpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.followup.FollowUp;

/**
 * Parses input arguments and creates a new AddFollowUpCommand object.
 * This parser handles the command to add a follow-up item to an existing entity.
 */
public class AddFollowUpCommandParser implements Parser<AddFollowUpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddFollowUpCommand
     * and returns an AddFollowUpCommand object for execution.
     *
     * @param args The arguments to parse, expected to contain index, follow-up name, and urgency.
     * @return The new AddFollowUpCommand object with parsed follow-up details.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public AddFollowUpCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_FOLLOWUP_NAME,
                        PREFIX_FOLLOWUP_URGENCY);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_FOLLOWUP_NAME,
                PREFIX_FOLLOWUP_URGENCY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddFollowUpCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX, PREFIX_FOLLOWUP_NAME,
                PREFIX_FOLLOWUP_URGENCY);

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        String followUpName = argMultimap.getValue(PREFIX_FOLLOWUP_NAME).get();
        String followUpUrgency = argMultimap.getValue(PREFIX_FOLLOWUP_URGENCY).get();

        try {
            FollowUp followUp = new FollowUp(followUpName, followUpUrgency);
            return new AddFollowUpCommand(index, followUp);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The ArgumentMultimap to check.
     * @param prefixes The prefixes to verify presence for.
     * @return true if all specified prefixes are present in the ArgumentMultimap.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
