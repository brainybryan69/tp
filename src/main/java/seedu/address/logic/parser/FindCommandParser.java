package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonMatchesPredicates;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        // Check if at least one prefix is present
        boolean hasNamePrefix = argMultimap.getValue(PREFIX_NAME).isPresent();
        boolean hasTagPrefix = argMultimap.getValue(PREFIX_TAG).isPresent();

        if (!hasNamePrefix && !hasTagPrefix) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Create predicates based on what's present
        Predicate<Person> namePredicate = null;
        Predicate<Person> tagPredicate = null;

        if (hasNamePrefix) {
            String nameKeywordsString = argMultimap.getValue(PREFIX_NAME).get();
            String[] nameKeywords = nameKeywordsString.trim().split("\\s+");
            namePredicate = new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        }

        if (hasTagPrefix) {
            String tagKeywordsString = argMultimap.getValue(PREFIX_TAG).get();
            String[] tagKeywords = tagKeywordsString.trim().split("\\s+");
            tagPredicate = new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords));
        }

        // Return appropriate predicate
        if (namePredicate != null && tagPredicate != null) {
            // Both name and tag filters - use combined predicate
            return new FindCommand(new PersonMatchesPredicates(namePredicate, tagPredicate));
        } else if (namePredicate != null) {
            // Only name filter
            return new FindCommand(namePredicate);
        } else {
            // Only tag filter
            return new FindCommand(tagPredicate);
        }
    }

}
