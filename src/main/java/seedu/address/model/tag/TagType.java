package seedu.address.model.tag;

/**
 * Represents the valid categories (types) that a {@link Tag} can belong to.
 * <p>
 * Each {@code TagType} corresponds to a specific aspect of business operations
 * such as finance, logistics, utilities, and personnel.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     TagType type = TagType.CUSTOMER;
 * </pre>
 */
public enum TagType {
    LANDLORD, //Physical location management
    DELIVERY, //For F&B businesses with delivery
    SUPPLIER, //Supply logistics for operation
    CUSTOMER, //Customers
    REGULATORY, //Respond to regulatory and compliance bodies
    FINANCES, //Deal with finances of business
    UTILITY, //In charge of utility like water, electricity, gas, etc
    EMPLOYEE, //Employees of the business
    OTHERS; //ANY OTHER
}
