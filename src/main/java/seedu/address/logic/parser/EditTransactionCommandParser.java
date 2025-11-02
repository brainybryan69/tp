package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_NUMBER;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTransactionCommand;
import seedu.address.logic.commands.EditTransactionCommand.EditTransactionDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditTransactionCommand object
 */
public class EditTransactionCommandParser implements Parser<EditTransactionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTransactionCommand
     * and returns an EditTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTransactionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_TRANSACTION_NUMBER,
                        PREFIX_TRANSACTION_NAME, PREFIX_TRANSACTION_AMOUNT);

        Index personIndex;
        Index transactionIndex;

        if (!argMultimap.getValue(PREFIX_INDEX).isPresent() || !argMultimap.getValue(PREFIX_TRANSACTION_NUMBER)
                .isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditTransactionCommand.MESSAGE_USAGE));
        }
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
            transactionIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TRANSACTION_NUMBER).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(pe.getMessage(), EditTransactionCommand.MESSAGE_USAGE), pe);
        }

        EditTransactionDescriptor editTransactionDescriptor = new EditTransactionDescriptor();

        if (argMultimap.getValue(PREFIX_TRANSACTION_NAME).isPresent()) {
            String transactionName = argMultimap.getValue(PREFIX_TRANSACTION_NAME).get();
            if (transactionName.trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        EditTransactionCommand.MESSAGE_USAGE));
            }
            editTransactionDescriptor.setName(transactionName);
        }
        if (argMultimap.getValue(PREFIX_TRANSACTION_AMOUNT).isPresent()) {
            editTransactionDescriptor.setAmount(
                    ParserUtil.parseTransactionAmount(argMultimap.getValue(PREFIX_TRANSACTION_AMOUNT).get()));
        }

        if (argMultimap.getValue(PREFIX_TRANSACTION_NAME).isEmpty() && argMultimap
                .getValue(PREFIX_TRANSACTION_AMOUNT).isEmpty()) {
            throw new ParseException(EditTransactionCommand.MESSAGE_NOT_EDITED);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TRANSACTION_NAME, PREFIX_TRANSACTION_AMOUNT);
        return new EditTransactionCommand(personIndex, transactionIndex, editTransactionDescriptor);
    }

}
