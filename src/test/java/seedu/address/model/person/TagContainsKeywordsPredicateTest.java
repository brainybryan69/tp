package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("supplier");
        List<String> secondPredicateKeywordList = Arrays.asList("supplier", "customer");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("supplier"));
        assertTrue(predicate.test(new PersonBuilder().withTags("supplier").build()));

        // Multiple keywords - person has one of them
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("supplier", "customer"));
        assertTrue(predicate.test(new PersonBuilder().withTags("supplier").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("customer").build()));

        // Person has multiple tags, one matches
        predicate = new TagContainsKeywordsPredicate(Collections.singletonList("supplier"));
        assertTrue(predicate.test(new PersonBuilder().withTags("supplier", "customer").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("SUPPLIER"));
        assertTrue(predicate.test(new PersonBuilder().withTags("supplier").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("supplier").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("customer"));
        assertFalse(predicate.test(new PersonBuilder().withTags("supplier").build()));

        // Person has no tags
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("supplier"));
        assertFalse(predicate.test(new PersonBuilder().build()));

        // Keywords match name, but not tags
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTags("supplier").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("supplier", "customer");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);

        String expected = TagContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
