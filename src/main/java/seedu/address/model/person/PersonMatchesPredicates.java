package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person} matches all given predicates (name and/or tag).
 * This allows filtering by multiple criteria simultaneously.
 */
public class PersonMatchesPredicates implements Predicate<Person> {
    private final Predicate<Person> namePredicate;
    private final Predicate<Person> tagPredicate;

    /**
     * Constructs a {@code PersonMatchesPredicates} with the given predicates.
     * Either predicate can be null if that filter is not needed.
     *
     * @param namePredicate Predicate to test person's name (can be null).
     * @param tagPredicate Predicate to test person's tags (can be null).
     */
    public PersonMatchesPredicates(Predicate<Person> namePredicate, Predicate<Person> tagPredicate) {
        this.namePredicate = namePredicate;
        this.tagPredicate = tagPredicate;
    }

    @Override
    public boolean test(Person person) {
        boolean nameMatches = namePredicate == null || namePredicate.test(person);
        boolean tagMatches = tagPredicate == null || tagPredicate.test(person);
        return nameMatches && tagMatches;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonMatchesPredicates)) {
            return false;
        }

        PersonMatchesPredicates otherPredicate = (PersonMatchesPredicates) other;
        return ((namePredicate == null && otherPredicate.namePredicate == null)
                || (namePredicate != null && namePredicate.equals(otherPredicate.namePredicate)))
                && ((tagPredicate == null && otherPredicate.tagPredicate == null)
                || (tagPredicate != null && tagPredicate.equals(otherPredicate.tagPredicate)));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate)
                .add("tagPredicate", tagPredicate)
                .toString();
    }
}
