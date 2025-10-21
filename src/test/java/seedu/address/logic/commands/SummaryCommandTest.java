package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.transaction.Transaction;

/**
 * Contains integration tests (interaction with the Model) for {@code SummaryCommand}.
 */
public class SummaryCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        CommandResult result = new SummaryCommand().execute(model);
        String expectedMessage = "Total cashflow from all contacts: +$0.00";
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_personWithNoTransactions_success() {
        AddressBook ab = new AddressBook();
        Model model = new ModelManager(ab, new UserPrefs());
        Person person = new Person(new Name("Alice"), new Phone("12345"), new Email("a@a.com"),
                new Address("123 sesame street"), new HashSet<>());
        model.addPerson(person);
        CommandResult result = new SummaryCommand().execute(model);
        String expectedMessage = "Total cashflow from all contacts: +$0.00";
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_singlePersonWithPositiveCashflow_success() {
        AddressBook ab = new AddressBook();
        Model model = new ModelManager(ab, new UserPrefs());
        Person person = new Person(new Name("Bob"), new Phone("23456"), new Email("b@b.com"),
                new Address("123 sesame street"), new HashSet<>(),
                Collections.singletonList(new Transaction("Project A", 150.50)));
        model.addPerson(person);
        CommandResult result = new SummaryCommand().execute(model);
        String expectedMessage = "Total cashflow from all contacts: +$150.50";
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_singlePersonWithNegativeCashflow_success() {
        AddressBook ab = new AddressBook();
        Model model = new ModelManager(ab, new UserPrefs());
        Person person = new Person(new Name("Charlie"), new Phone("34567"), new Email("c@c.com"),
                new Address("123 sesame street"), new HashSet<>(),
                Collections.singletonList(new Transaction("Lunch", -25.00)));
        model.addPerson(person);
        CommandResult result = new SummaryCommand().execute(model);
        String expectedMessage = "Total cashflow from all contacts: -$25.00";
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_multiplePersonsWithMixedCashflow_success() {
        AddressBook ab = new AddressBook();
        Model model = new ModelManager(ab, new UserPrefs());
        Person p1 = new Person(new Name("David"), new Phone("45678"), new Email("d@d.com"),
                new Address("123 sesame street"), new HashSet<>(),
                Collections.singletonList(new Transaction("Freelance", 1000.00)));
        Person p2 = new Person(new Name("Eve"), new Phone("56789"), new Email("e@e.com"),
                new Address("123 sesame street"), new HashSet<>(),
                Arrays.asList(new Transaction("Rent", -450.55), new Transaction("Groceries", -49.00)));
        model.addPerson(p1);
        model.addPerson(p2);
        CommandResult result = new SummaryCommand().execute(model);
        String expectedMessage = "Total cashflow from all contacts: +$500.45"; // 1000.00 - 450.55 - 49.00
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }
}