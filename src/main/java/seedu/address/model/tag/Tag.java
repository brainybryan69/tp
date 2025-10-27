package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a {@code Tag} in the address book.
 * <p>
 * Each Tag is immutable and must have a valid name and type.
 * <ul>
 *     <li>Tag names must be alphanumeric.</li>
 *     <li>Each Tag must belong to a predefined {@link TagType} category.</li>
 * </ul>
 * Example valid tags: {@code LANDLORD}, {@code CUSTOMER}, {@code EMPLOYEE}.
 */
public class Tag {
    /** Error message for invalid tag names. */
    public static final String MESSAGE_CONSTRAINTS = "Tag names should only contain alphabets (a-z) "
            + "and must not be NULL";
    /** Error message for invalid tag types. */
    public static final String TYPE_CONSTRAINTS = "Each tag should only belong to one of the following: \n"
            + "-LANDLORD \n"
            + "-DELIVERY \n"
            + "-SUPPLIER \n"
            + "-CUSTOMER \n"
            + "-REGULATORY \n"
            + "-FINANCES \n"
            + "-UTILITY \n"
            + "-EMPLOYEE \n"
            + "-OTHERS \n";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        tagName = tagName.trim();
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        checkArgument(isValidTagType(tagName), TYPE_CONSTRAINTS);
        this.tagName = tagName.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid tag name.
     * <p>
     * A valid tag name consists only of alphanumeric characters and cannot be empty.
     *
     * @param test The string to validate.
     * @return {@code true} if {@code test} matches {@link #VALIDATION_REGEX}, otherwise {@code false}.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }
    /**
     * Returns true if a given string matches one of the predefined {@link TagType} values.
     *
     * @param test The tag name to validate.
     * @return {@code true} if {@code test} corresponds to a valid {@link TagType}, otherwise {@code false}.
     */
    public static boolean isValidTagType(String test) {
        try {
            TagType.valueOf(test.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    /**
     * Checks if this {@code Tag} is equal to another object.
     * Two tags are considered equal if they have the same {@code tagName}.
     *
     * @param other The object to compare with.
     * @return {@code true} if both objects represent the same tag, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagName.equals(otherTag.tagName);
    }
    /**
     * Returns the hash code of this tag.
     *
     * @return The hash code derived from {@code tagName}.
     */
    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
