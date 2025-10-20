package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddFollowUpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.followUp.FollowUp;

import java.util.stream.Stream;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOLLOWUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOLLOWUP_URGENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

public class AddFollowUpCommandParser implements Parser<AddFollowUpCommand> {
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

        FollowUp followUp = new FollowUp(followUpName, followUpUrgency);

        return new AddFollowUpCommand(index, followUp);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
