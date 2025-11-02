package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.followup.FollowUp;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Transaction;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private final List<FollowUp> followUps = new ArrayList<>();

    /**
     * Every field except address must be present and not null.
     * Address can be null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Every field except address must be present and not null.
     * Address can be null. Includes transactions.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  List<Transaction> transactions, List<FollowUp> followUps) {
        requireAllNonNull(name, phone, email, tags, transactions);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.transactions.addAll(transactions);
        this.followUps.addAll(followUps);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable transaction list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    /**
     * Returns an immutable transaction list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<FollowUp> getFollowUps() {
        return Collections.unmodifiableList(followUps);
    }

    /**
     * Returns true if both persons have the same name and same phone or same email.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null && isSameContactDetails(otherPerson);
    }

    /**
     * Returns true if both persons have the same email or phone number.
     */
    public boolean isSameContactDetails(Person otherPerson) {
        boolean sameEmail = otherPerson.getEmail().equals(this.getEmail());
        boolean samePhone = otherPerson.getPhone().equals(this.getPhone());
        return sameEmail || samePhone;
    }

    /**
     * Returns true if both persons have the same identity fields.
     * Two persons are considered equal if they have the same name (case-insensitive) and
     * either the same phone number or the same email (case-insensitive).
     * Note: Address and tags are not considered in equality comparison.
     *
     * @param other The object to compare with.
     * @return True if the other object is a Person with matching identity fields, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && Objects.equals(address, otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("transactions", transactions)
                .toString();
    }
}
