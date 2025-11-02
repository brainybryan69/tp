package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_NAME;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transaction.Transaction;

/**
 * Parses input arguments and creates a new AddTransactionCommand object
 */
public class AddTransactionCommandParser implements Parser<AddTransactionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTransactionCommand
     * and returns an AddTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTransactionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_TRANSACTION_NAME,
                        PREFIX_TRANSACTION_AMOUNT);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_TRANSACTION_NAME,
                PREFIX_TRANSACTION_AMOUNT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTransactionCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX, PREFIX_TRANSACTION_NAME,
                PREFIX_TRANSACTION_AMOUNT);

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        String transactionName = argMultimap.getValue(PREFIX_TRANSACTION_NAME).get();
        if (transactionName.trim().isEmpty()) {
            throw new ParseException(
                    AddTransactionCommand.MESSAGE_INVALID_TRANSACTION_NAME);
        }
        double transactionAmount = ParserUtil.parseTransactionAmount(argMultimap.getValue(PREFIX_TRANSACTION_AMOUNT)
                .get());
        if (roundOffAmount(transactionAmount) == 0f || roundOffAmount(transactionAmount) > 100000f) {
            throw new ParseException(ParserUtil.MESSAGE_INVALID_TRANSACTION_AMOUNT);
        }
        Transaction transaction = new Transaction(transactionName, roundOffAmount(transactionAmount));

        return new AddTransactionCommand(index, transaction);
    }

    /**
     * Rounds off a given double to 2 decimal places
     *
     * @param transactionAmount a double transaction amount.
     */
    public double roundOffAmount(double transactionAmount) {
        return BigDecimal.valueOf(transactionAmount).setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
