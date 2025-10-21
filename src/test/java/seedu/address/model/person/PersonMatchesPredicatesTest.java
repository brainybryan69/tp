package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonMatchesPredicatesTest {

    @Test
    public void equals() {
        NameContainsKeywordsPredicate namePredicate1 =
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        NameContainsKeywordsPredicate namePredicate2 =
                new NameContainsKeywordsPredicate(Collections.singletonList("Bob"));
        TagContainsKeywordsPredicate tagPredicate1 =
                new TagContainsKeywordsPredicate(Collections.singletonList("supplier"));
        TagContainsKeywordsPredicate tagPredicate2 =
                new TagContainsKeywordsPredicate(Collections.singletonList("customer"));

        PersonMatchesPredicates firstPredicate = new PersonMatchesPredicates(namePredicate1, tagPredicate1);
        PersonMatchesPredicates secondPredicate = new PersonMatchesPredicates(namePredicate2, tagPredicate2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonMatchesPredicates firstPredicateCopy = new PersonMatchesPredicates(namePredicate1, tagPredicate1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicates -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personMatchesBothPredicates_returnsTrue() {
        // Both name and tag match
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        TagContainsKeywordsPredicate tagPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("supplier"));
        PersonMatchesPredicates predicate = new PersonMatchesPredicates(namePredicate, tagPredicate);

        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("supplier").build()));
    }

    @Test
    public void test_personMatchesOnlyName_returnsFalse() {
        // Name matches but tag doesn't
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        TagContainsKeywordsPredicate tagPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("supplier"));
        PersonMatchesPredicates predicate = new PersonMatchesPredicates(namePredicate, tagPredicate);

        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTags("customer").build()));
    }

    @Test
    public void test_personMatchesOnlyTag_returnsFalse() {
        // Tag matches but name doesn't
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        TagContainsKeywordsPredicate tagPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("supplier"));
        PersonMatchesPredicates predicate = new PersonMatchesPredicates(namePredicate, tagPredicate);

        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withTags("supplier").build()));
    }

    @Test
    public void test_personMatchesNeither_returnsFalse() {
        // Neither name nor tag match
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        TagContainsKeywordsPredicate tagPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("supplier"));
        PersonMatchesPredicates predicate = new PersonMatchesPredicates(namePredicate, tagPredicate);

        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withTags("customer").build()));
    }

    @Test
    public void test_nullNamePredicate_onlyChecksTag() {
        // Only tag predicate provided (name is null)
        TagContainsKeywordsPredicate tagPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("supplier"));
        PersonMatchesPredicates predicate = new PersonMatchesPredicates(null, tagPredicate);

        // Should match if tag matches, regardless of name
        assertTrue(predicate.test(new PersonBuilder().withName("Anyone").withTags("supplier").build()));
        assertFalse(predicate.test(new PersonBuilder().withName("Anyone").withTags("customer").build()));
    }

    @Test
    public void test_nullTagPredicate_onlyChecksName() {
        // Only name predicate provided (tag is null)
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        PersonMatchesPredicates predicate = new PersonMatchesPredicates(namePredicate, null);

        // Should match if name matches, regardless of tag
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("supplier").build()));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withTags("supplier").build()));
    }

    @Test
    public void test_multipleKeywords_allMatch() {
        // Multiple keywords for both predicates
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        TagContainsKeywordsPredicate tagPredicate =
                new TagContainsKeywordsPredicate(Arrays.asList("supplier", "customer"));
        PersonMatchesPredicates predicate = new PersonMatchesPredicates(namePredicate, tagPredicate);

        // Alice with supplier tag - should match
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("supplier").build()));

        // Bob with customer tag - should match
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").withTags("customer").build()));

        // Charlie with supplier tag - name doesn't match
        assertFalse(predicate.test(new PersonBuilder().withName("Charlie").withTags("supplier").build()));

        // Alice with landlord tag - tag doesn't match
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTags("landlord").build()));
    }
}
