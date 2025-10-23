package seedu.address.logic.parser;

import seedu.address.logic.commands.SummaryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SummaryCommand object
 */
public class SummaryCommandParser implements Parser<SummaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SummaryCommand
     * and returns a SummaryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SummaryCommand parse(String args) throws ParseException {
        return new SummaryCommand();
    }
}
