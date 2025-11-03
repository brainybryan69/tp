---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# Atlas Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Acknowledgements

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## Setting up, getting started

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## Design

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="550" />

The **Architecture Diagram** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="550" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="550" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component" width="550"/>

The UI consists of a `MainWindow` that is made up of parts e.g., `CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" width="550" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g., to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="550"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g., during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="550" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g., the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="550" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## Implementation

This section describes some noteworthy details on how certain features are implemented.

### Transaction Management

The transaction management feature allows users to add and delete financial transactions associated with a person in the address book. This is useful for tracking income and expenses related to a contact.

#### Add Transaction Command (`addtxn`)

The `addtxn` command adds a new transaction to a person. The implementation involves parsing the user input, creating a `Transaction` object, and adding it to the person's transaction list.

The following sequence diagram illustrates the process:

<puml src="diagrams/AddTransactionSequenceDiagram.puml" alt="Add Transaction Sequence Diagram" width="550" />

**Implementation Details:**

1.  **Parsing:** The `AddressBookParser` identifies the `addtxn` command word and passes the arguments to the `AddTransactionCommandParser`.
2.  **Index and Transaction Creation:** The `AddTransactionCommandParser` parses the person's index, transaction name, and amount to create an `AddTransactionCommand` object.
3.  **Execution:** The `AddTransactionCommand` retrieves the `Person` from the `Model` using the provided index.
4.  **Person Update:** A new `Person` object is created with the updated transaction list.
5.  **Model Update:** The `Model` is updated with the new `Person` object.



The `deletetxn` command removes a transaction from a person. The implementation is similar to `addtxn`, but it removes the transaction from the person's transaction list instead of adding one.

The following sequence diagram illustrates the process:

<puml src="diagrams/DeleteTransactionSequenceDiagram.puml" alt="Delete Transaction Sequence Diagram" width="550" />

**Implementation Details:**

1.  **Parsing:** The `AddressBookParser` identifies the `deletetxn` command word and passes the arguments to the `DeleteTransactionCommandParser`.
2.  **Index and Transaction Creation:** The `DeleteTransactionCommandParser` parses the person's index and the transaction's index to create a `DeleteTransactionCommand` object.
3.  **Execution:** The `DeleteTransactionCommand` retrieves the `Person` from the `Model` using the provided index.
4.  **Person Update:** A new `Person` object is created with the transaction removed from the transaction list.
5.  **Model Update:** The `Model` is updated with the new `Person` object.

#### Edit Transaction Command (`editTxn`)

The `editTxn` command edits an existing transaction of a person. The implementation involves parsing the user input, creating an `EditTransactionDescriptor` object, and updating the transaction in the person's transaction list.

The following sequence diagram illustrates the process:

<puml src="diagrams/EditTransactionSequenceDiagram.puml" alt="Edit Transaction Sequence Diagram" width="550" />

**Implementation Details:**

1.  **Parsing:** The `AddressBookParser` identifies the `editTxn` command word and passes the arguments to the `EditTransactionCommandParser`.
2.  **Index and Descriptor Creation:** The `EditTransactionCommandParser` parses the person's index, transaction index, and the fields to be edited to create an `EditTransactionCommand` object.
3.  **Execution:** The `EditTransactionCommand` retrieves the `Person` from the `Model` using the provided index.
4.  **Transaction Update:** A new `Transaction` object is created with the updated details.
5.  **Person Update:** A new `Person` object is created with the updated transaction list.
6.  **Model Update:** The `Model` is updated with the new `Person` object.


### FollowUp Management

The follow up management feature allows users to add and delete follow up tasks associated with a person in the address book. This is useful for tracking follow up tasks related to a contact.

#### Add FollowUp Command (`addfu`)

The `addfu` command adds a new follow-up to a person. The implementation involves parsing the user input, creating a `FollowUp` object, and adding it to the person's follow up list.

The following sequence diagram illustrates the process:

<puml src="diagrams/AddFollowUpSequenceDiagram.puml" alt="Add Follow Up Sequence Diagram" width="550" />

**Implementation Details:**

1.  **Parsing:** The `AddressBookParser` identifies the `addfu` command word and passes the arguments to the `AddFollowUpCommandParser`.
2.  **Index and FollowUp Creation:** The `AddFollowUpCommandParser` parses the person's index, follow-up name, and urgency to create an  `AddFollowUpCommand` object.
3.  **Execution:** The `AddFollowUpCommand` retrieves the `Person` from the `Model` using the provided index.
4.  **Person Update:** A new `Person` object is created with the updated follow-up list.
5.  **Model Update:** The `Model` is updated with the new `Person` object.



The `deletefu` command removes a follow-up from a person. The implementation is similar to `addfu`, but it removes the follow-up from the person's follow-up list instead of adding one.

The following sequence diagram illustrates the process:

<puml src="diagrams/DeleteFollowUpSequenceDiagram.puml" alt="Delete FollowUp Sequence Diagram" width="550" />

**Implementation Details:**

1.  **Parsing:** The `AddressBookParser` identifies the `deletefu` command word and passes the arguments to the `DeleteFollowUpCommandParser`.
2.  **Index and FollowUp Creation:** The `DeleteFollowUpCommandParser` parses the person's index and the follow-up's index to create a `DeleteFollowUpCommand` object.
3.  **Execution:** The `DeleteFollowUpCommand` retrieves the `Person` from the `Model` using the provided index.
4.  **Person Update:** A new `Person` object is created with the follow-up removed from the follow-up list.
5.  **Model Update:** The `Model` is updated with the new `Person` object.

### Data archiving

The archive management feature allows users to archive and unarchive persons in the address book. This is useful for maintaining a clean view of active contacts while preserving information about inactive or past contacts.

#### Archive Command (`archive`)

The `archive` command moves all the people from the active address book to the archived list.

The following sequence diagram illustrates the process:

<puml src="diagrams/ArchiveSequenceDiagram.puml" alt="Archive Sequence Diagram" width="550" />

**Implementation Details:**

1.  **Parsing:** The `AddressBookParser` identifies the `archive` command word and creates an `ArchiveCommand` object.`.
3.  **Execution:** The `ArchiveCommand` retrieves the `Person` from the `Model` using the provided index.
4.  **Person Update:** A new `Person` object is created with the archive status set to archived.
5.  **Model Update:** The `Model` is updated with the new `Person` object, moving them to the archived list.

#### Unarchive Command (`unarchive`)

The `unarchive` command restores a person from the archived list back to the active address book. The implementation is similar to `archive`, but it changes the person's archive status from archived to active.

The following sequence diagram illustrates the process:

<puml src="diagrams/UnarchiveSequenceDiagram.puml" alt="Unarchive Sequence Diagram" width="550" />

**Implementation Details:**

1.  **Parsing:** The `AddressBookParser` identifies the `unarchive` command word and passes the arguments to the `UnarchiveCommandParser`.
2.  **Index Parsing:** The `UnarchiveCommandParser` parses the person's index to create an `UnarchiveCommand` object.
3.  **Execution:** The `UnarchiveCommand` retrieves the `Person` from the archived list in the `Model` using the provided index.
4.  **Person Update:** A new `Person` object is created with the archive status set to active.
5.  **Model Update:** The `Model` is updated with the new `Person` object, moving them back to the active list.




--------------------------------------------------------------------------------------------------------------------

## Documentation, logging, testing, configuration, dev-ops

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## Appendix: Requirements

### Product scope

**Target user profile**:

* food and beverage entrepreneurs/business owners
* manages a high volume of contacts
* manages a high volume of transactions
* prefers digital solutions over paper-based methods
* time-conscious and efficiency-oriented
* may lack dedicated administrative or IT support

**Value proposition**:
Manage suppliers, contractors, employees and even transactions easily
* Saves data locally without the need for remote servers


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                   | I want to …​                                                                                                          | So that I can…​                                                                          |
|----------|---------------------------|-----------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| `* * *`  | As an F&B business owner  | add contact details of stakeholders (such as suppliers, customers, and delivery partners)                             | keep track of everyone involved in my business operations                                |
| `* * *`  | As an F&B business owner  | view all the contact details of my stakeholders                                                                       | easily see who I work with and identify any missing or new contacts to add               |
| `* * *`  | As an F&B business owner  | delete outdated or irrelevant stakeholder contacts                                                                    | maintain an up-to-date and organized contact list                                        |
| `* * *`  | As an F&B business owner  | record sales and expense transactions linked to my stakeholders (e.g., daily sales, supplier payments, utility bills) | monitor my business finances accurately                                                  |
| `* * *`  | As an F&B business owner  | delete incorrect or duplicate sales and expense transactions                                                          | keep my financial records clean and accurate                                             |
| `* * *`  | As an F&B business owner  | archive all my current contacts with a single command                                                                 | clear my active contact list without permanently deleting valuable business relationships |
| `* * *` | As an F&B business owner | unarchive my contacts | quickly resume business relationships without re-entering contact information |
| `* * *`  | As an F&B business owner  | add follow-up tasks with urgency levels to my contacts                                                                | track important actions I need to take with each business relationship                   |
| `* * *`  | As an F&B business owner  | delete specific follow-up tasks from contacts                                                                         | keep my task list current and remove completed items                    |

### Use cases

(For all use cases below, the **System** is the `Atlas` and the **Actor** is the `user`, unless specified otherwise)

#### General Commands

**Use case: UC01 - View help**

**MSS**

1. User requests to view help.
2. System displays a help message with a link to the user guide.

   Use case ends.

---

**Use case: UC02 - Clear all entries**

**MSS**

1. User requests to clear all entries.
2. System clears all contacts from the address book.
3. System confirms that all entries have been cleared.

   Use case ends.

---

**Use case: UC03 - View net cash-flow summary**

**MSS**

1. User requests to view net cash-flow summary.
2. System collates the sum of all transactions from every contact.
3. System displays the total cash-flow (positive or negative).

   Use case ends.

---

**Use case: UC04 - Exit the program**

**MSS**

1. User requests to exit the program.
2. System closes the application.

   Use case ends.

---

#### Contact Management Commands

**Use case: UC05 - Add a contact**

**MSS**

1. User chooses to add a contact with details(name, phone number, email, and optionally address and tags).
2. System validates that there are no duplicate contacts.
3. System adds the contact and tells user operation is successful.

   Use case ends.

**Extensions**

* 1a. User does not enter all required details (name, phone number, or email).
    * 1a1. System shows error message indicating missing fields.

  Use case resumes at step 1.

* 2a. Duplicate contact already exists in the address book.
    * 2a1. System shows error message indicating duplicate phone number or email.

  Use case resumes at step 1.

* 1b. Phone number has fewer than 3 digits.
    * 1b1. System shows error message indicating invalid phone number format.

  Use case resumes at step 1.

* 1c. Tag entered is not one of the valid tags.
    * 1c1. System shows error message indicating invalid tag.

  Use case resumes at step 1.

---

**Use case: UC06 - List all contacts**

**MSS**

1. User requests to list all contacts.
2. System displays all contacts in the address book.

   Use case ends.

---

**Use case: UC07 - Edit a contact**

**MSS**

1. User searches for contact to edit.
2. User requests to edit a contact at a specified index with at least one field to update.
3. System validates the index.
4. System updates the contact with the new field values.
5. System displays the updated contact information.

   Use case ends.

**Extensions**

* 3a. Index is invalid (not a positive integer or out of range).
    * 3a1. System shows error message indicating invalid index.

  Use case resumes at step 2.

* 2a. User does not provide any field to update.
    * 2a1. System shows error message indicating at least one field must be provided.

  Use case resumes at step 2.

* 4a. Updated phone number or email already exists for another contact.
    * 4a1. System shows error message indicating duplicate phone number or email.

  Use case resumes at step 2.

---

**Use case: UC08 - Find contacts by name**

**MSS**

1. User requests to find contacts by providing name keywords.
2. System searches for contacts whose names contain any of the given keywords.
3. System displays the list of matching contacts.

   Use case ends.

**Extensions**

* 2a. No contacts match the search keywords.
    * 2a1. System displays an empty list.

  Use case ends.

---

**Use case: UC09 - Find contacts by tag**

**MSS**

1. User requests to find contacts by providing tag keywords.
2. System searches for contacts whose tags match any of the given tag keywords.
3. System displays the list of matching contacts.

   Use case ends.

**Extensions**

* 2a. No contacts match the search tags.
    * 2a1. System displays an empty list.

  Use case ends.

---

**Use case: UC10 - Find contacts by name and tag**

**MSS**

1. User requests to find contacts by providing both name and tag keywords.
2. System searches for contacts whose names or tags match any of the given keywords.
3. System displays the list of matching contacts.

   Use case ends.

**Extensions**

* 2a. No contacts match the search criteria.
    * 2a1. System displays an empty list.

  Use case ends.

---

**Use case: UC11 - Delete a contact**

**MSS**

1. User searches for contact to delete.
2. System checks if user exists.
3. System deletes the contact.
4. System confirms the deletion and updates the display.

   Use case ends.

**Extensions**

* 3a. Index is invalid (not a positive integer or out of range).
    * 3a1. System shows error message indicating invalid index.

  Use case resumes at step 2.

---

#### Transaction Management Commands

**Use case: UC12 - Add a transaction**

**MSS**

1. User requests to add a transaction to a contact at a specified index with transaction name and amount.
2. System checks if user exists.
3. System retrieves the contact at the specified index.
4. System determines the transaction type based on the amount (positive = income, negative = expense).
5. System creates a new transaction and adds it to the contact's transaction list.
6. System updates the contact with the new transaction list.
7. System displays the updated contact with the new transaction.

   Use case ends.

**Extensions**

* 3a. Index is invalid (not a positive integer or out of range).
    * 3a1. System shows error message indicating invalid index.

  Use case resumes at step 2.

* 2a. Amount is zero.
    * 2a1. System shows error message indicating amount cannot be zero.

  Use case resumes at step 2.

* 2b. Transaction name is not provided.
    * 2b1. System shows error message indicating transaction name is required.

  Use case resumes at step 2.

---

**Use case: UC13 - Delete a transaction**

**MSS**

1. User requests to delete a transaction at a specified transaction index from a contact at a specified person index.
2. System checks if person and transaction exist.
3. System retrieves the contact at the specified person index.
4. System retrieves the transaction at the specified transaction index from the contact's transaction list.
5. System removes the transaction from the contact's transaction list.
6. System updates the contact with the modified transaction list.
7. System confirms the deletion and updates the display.

   Use case ends.

**Extensions**

* 3a. Person index is invalid (not a positive integer or out of range).
    * 3a1. System shows error message indicating invalid person index.

  Use case resumes at step 2.

* 3b. Transaction index is invalid (not a positive integer or out of range).
    * 3b1. System shows error message indicating invalid transaction index.

  Use case resumes at step 2.

---

**Use case: UC14 - Edit a transaction**

**MSS**

1. User requests to edit a transaction at a specified transaction index for a contact at a specified person index with at least one field to update (name or amount).
2. System validates both indices.
3. System retrieves the transaction from the contact's transaction list.
4. System updates the transaction with the new field values.
5. System updates the contact with the modified transaction list.
6. System displays the updated contact with the edited transaction.

   Use case ends.

**Extensions**

* 2a. Person index is invalid (not a positive integer or out of range).
    * 2a1. System shows error message indicating invalid person index.

  Use case resumes at step 1.

* 2b. Transaction index is invalid (not a positive integer or out of range).
    * 2b1. System shows error message indicating invalid transaction index.

  Use case resumes at step 1.

* 1a. User does not provide any field to update.
    * 1a1. System shows error message indicating at least one field must be provided.

  Use case resumes at step 1.

* 1b. Updated amount is zero.
    * 1b1. System shows error message indicating amount cannot be zero.

  Use case resumes at step 1.

---

#### Follow-up Management Commands

**Use case: UC15 - Add a follow-up**

**MSS**

1. User searches for contact to add follow-up.
2. User requests to add a follow-up to a contact at a specified index with follow-up name and urgency level.
3. System validates the person index and urgency level (HIGH, MEDIUM, or LOW).
4. System updates the contact with the new follow-up.
5. System displays the updated contact with the new follow-up (color-coded by priority).

   Use case ends.

**Extensions**

* 3a. Person index is invalid (not a positive integer or out of range).
    * 3a1. System shows error message indicating invalid person index.

  Use case resumes at step 2.

* 3b. Priority level is not HIGH, MEDIUM, or LOW.
    * 3b1. System shows error message indicating invalid priority level.

  Use case resumes at step 2.

* 2a. Follow-up name is not provided.
    * 2a1. System shows error message indicating follow-up name is required.

  Use case resumes at step 2.

---

**Use case: UC16 - Delete a follow-up**

**MSS**

1. User searches for contact with follow-up to delete.
2. User requests to delete a follow-up at a specified follow-up index from a contact at a specified person index.
3. System validates both indices.
4. System removes the follow-up from the contact.
5. System confirms the deletion and updates the display.

   Use case ends.

**Extensions**

* 3a. Person index is invalid (not a positive integer or out of range).
    * 3a1. System shows error message indicating invalid person index.

  Use case resumes at step 2.

* 3b. Follow-up index is invalid (not a positive integer or out of range).
    * 3b1. System shows error message indicating invalid follow-up index.

  Use case resumes at step 2.

---

#### Data Management Commands

**Use case: UC17 - Archive all data**

**MSS**

1. User requests to archive all existing data.
2. System saves all current contacts to the archive file at `[JAR file location]/data/archive.json`.
3. System clears all contacts from the active address book display.
4. System confirms the archiving operation.

   Use case ends.

---

**Use case: UC18 - Unarchive data**

**MSS**

1. User requests to restore archived data.
2. System reads the archive file from `[JAR file location]/data/archive.json`.
3. System restores all contacts from the archive file to the active address book.
4. System displays all restored contacts in addition to the currently displayed contacts.
5. System confirms the unarchive operation.

   Use case ends.

**Extensions**

* 2a. Archive file does not exist or is empty.
    * 2a1. System shows error message indicating no archived data found.

  Use case ends.

* 2b. Archive file format is invalid.
    * 2b1. System shows error message indicating corrupted archive file.

  Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  Should be able to run without internet connection
4.  Should be able to support colour-coded output for readability (e.g success = green, errors = red)
5.  The app should adhere to data protection principles

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Transaction**: A financial record associated with a person, which can be either an income or an expense.
* **Income**: A transaction that increases the amount of money.
* **Expense**: A transaction that decreases the amount of money.
* **Follow-up**: A task associated with a specific contact and with a specific level or urgency.
* **Archive**: Temporarily stores all listed contacts in a archive and returns a new address book.
* **Unarchive**: Restores all archived contacts to the current address book.

--------------------------------------------------------------------------------------------------------------------

## Appendix: Instructions for manual testing

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Editing a person

1. Editing a person while all persons are being shown
   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.
   2. Test case: `edit 1 n/new name`<br>
      Expected: First contact's name is changed to "new name". Details of the edited contact shown in the status message. Timestamp in the status bar is updated.
   3. Test case: `edit 0 p/12345678`<br>
      Expected: No person is edited. Error details shown in the status message. Status bar remains the same.
   4. Other incorrect edit commands to try: `edit`, `edit x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Finding a person

1. Finding a person by name
   1. Prerequisites: Add a person named "John Doe" to the list.
   2. Test case: `find n/John`<br>
      Expected: The contact "John Doe" is shown in the list.
   3. Test case: `find n/Peter`<br>
      Expected: An empty list is shown.

2. Finding a person by tag
   1. Prerequisites: Add a person named "Alice" with tag "friends" and "Bob" with tag "colleagues".
   2. Test case: `find t/friends`<br>
      Expected: The contact "Alice" is shown in the list.
   3. Test case: `find t/family`<br>
      Expected: An empty list is shown.

3. Finding a person by name and tag
   1. Prerequisites: Add a person named "Charlie" with tag "clients" and "David" with tag "clients".
   2. Test case: `find n/Charlie t/clients`<br>
      Expected: The contact "Charlie" is shown in the list.
   3. Test case: `find n/Eve t/clients`<br>
      Expected: An empty list is shown.
   4. Test case: `find n/Charlie t/friends`<br>
      Expected: An empty list is shown.

### Managing transactions

1. Adding a transaction
   1. Prerequisites: List all persons using the `list` command.
   2. Test case: `addtxn i/1 n/Coffee v/5.00`<br>
      Expected: A new transaction "Coffee" with value "5.00" is added to the first person.
   3. Test case: `addtxn i/1 n/Lunch v/-10.00`<br>
      Expected: A new transaction "Lunch" with value "-10.00" is added to the first person.

2. Deleting a transaction
   1. Prerequisites: Add a transaction to the first person.
   2. Test case: `deletetxn i/1 ti/1`<br>
      Expected: The first transaction of the first person is deleted.

3. Editing a transaction
   1. Prerequisites: Add a transaction to the first person.
   2. Test case: `edittxn i/1 ti/1 n/new name`<br>
      Expected: The first transaction of the first person is renamed to "new name".

### Managing follow-ups

1. Adding a follow-up
   1. Prerequisites: List all persons using the `list` command.
   2. Test case: `addfu i/1 f/Follow up on invoice u/HIGH`<br>
      Expected: A new follow-up "Follow up on invoice" with HIGH priority is added to the first person.
   3. Test case: `addfu i/1 f/Another follow up u/LOW`<br>
      Expected: A new follow-up "Another follow up" with LOW priority is added to the first person.

2. Deleting a follow-up
   1. Prerequisites: Add a follow-up to the first person.
   2. Test case: `deletefu i/1 f/1`<br>
      Expected: The first follow-up of the first person is deleted.

### Saving data

1. Dealing with missing/corrupted data files

   1. Simulate a missing data file by renaming `addressbook.json` to `addressbook.json.bak`.
      Expected: The app should launch with an empty contact list.
   2. Simulate a corrupted data file by adding invalid text to `addressbook.json`.
      Expected: The app should launch with an empty contact list. A backup of the corrupted file should be created.

1. _{ more test cases …​ }_

--------------------------------------------------------------------------------------------------------------------

## Appendix: Project Effort

### Overview
The development of Atlas represented a significant step-up in complexity from the AddressBook-Level3 (AB3) project. While AB3 manages a single entity (`Person`), Atlas is designed to handle multiple, interconnected entities: `Person`, `Transaction`, and `FollowUp`. This multi-entity architecture introduced substantial challenges and required a greater development effort.

### Key Challenges
*   **Complex Data Modeling**: The core challenge was designing and implementing the one-to-many relationships between a `Person` and their associated `Transaction` and `FollowUp` entities. This required careful data structure design and robust dependency management for all CRUD (Create, Read, Update, Delete) operations.
*   **Feature Complexity**:
    *   **Financial Tracking**: We built a complete transaction management system from the ground up, including logic for income/expense tracking, balance calculations, and a `summary` command for net cash flow analysis.
    *   **Task Management**: The priority-based `FollowUp` system, with its color-coded UI and persistent storage, added another layer of complexity not present in AB3.
    *   **Data Archiving**: The `archive` and `unarchive` features required implementing a dual-storage strategy, careful state management, and robust file I/O to prevent data loss.

### Effort Breakdown
The estimated effort distribution for major components is as follows:
*   **Core Entity Management (30%)**: Extending the single-entity architecture to support multiple related entities.
*   **Financial Transaction System (25%)**: Implementing transaction logic, CRUD operations, and summary calculations.
*   **Follow-up Management (20%)**: Building the priority-based task system and UI integration.
*   **Archive System (15%)**: Developing the archive/unarchive functionality and file management.
*   **UI/UX Enhancements (10%)**: Adapting the GUI to display nested entity lists within the main contact cards.

### Strategic Reuse
We leveraged existing libraries and patterns to focus our efforts on core features, saving an estimated **15% of development time**.
*   **Jackson Library**: Utilized for JSON serialization and deserialization for both primary (`addressbook.json`) and archive (`archive.json`) data. Our custom adaptations are encapsulated in the `JsonUtil` class and associated entity-specific serializers.
*   **JavaFX & Command Pattern**: We reused the foundational UI components and command architecture from AB3. However, these were significantly extended. For example, the `PersonCard` was heavily modified to display nested data, and numerous complex commands (`AddTransactionCommand`, `ArchiveCommand`, etc.) were built on top of the base pattern.

### Summary of Achievements
Despite the increased complexity, we successfully delivered a robust application with:
*   A **fully functional multi-entity management system** for contacts, transactions, and follow-ups.
*   **Integrated financial tracking** with summary reporting.
*   **Intuitive, priority-driven task management**.
*   **Safe data preservation** through an archive/unarchive system.
*   **Comprehensive test coverage** that meets AB3's high standards while covering all new features.

In summary, while AB3 provided a solid architectural foundation, Atlas required approximately **2.5 times the implementation effort** due to its multi-entity data model, complex business logic, and advanced data management features.

--------------------------------------------------------------------------------------------------------------------

## Appendix: Planned Enhancements

**Team size:** 5

1. **Add confirmation dialog for archive command:** Currently, the `archive` command immediately clears all contacts from the display without asking for confirmation. We plan to add a confirmation prompt that displays the number of contacts to be archived and requires explicit user confirmation before proceeding. For example:
   ```
   > archive
   Warning: You are about to archive 25 contacts. This will clear your current contact list.
   Type 'archive confirm' to proceed, or any other command to cancel.
   ```

2. **Make unarchive handle merge conflicts:** Currently, if the user has active contacts and runs `unarchive`, the behavior when contacts with duplicate phone numbers or emails exist is undefined. We plan to implement a merge strategy that shows a warning message listing conflicting contacts and asks the user whether to skip duplicates or replace existing contacts. For example:
   ```
   > unarchive
   Warning: 3 contacts in the archive conflict with existing contacts:
   - John Doe (same email: john@example.com)
   - Jane Smith (same phone: 91234567)
   Would you like to: [skip/replace/merge]
   ```

3. **Allow editing of follow-up priority:** Currently, once a follow-up is added, users cannot change its priority level without deleting and re-adding it. We plan to add an `editfu` command that allows users to modify the follow-up name and/or priority. Format: `editfu i/PERSON_INDEX f/FOLLOWUP_INDEX [n/NEW_NAME] [u/NEW_PRIORITY]`. For example:
   ```
   > editfu i/1 f/2 u/HIGH
   Follow-up priority updated: "Check delivery status" changed from MEDIUM to HIGH priority
   ```

4. **Add follow-up sorting by priority:** Currently, follow-ups are displayed in the order they were added, which may not reflect their urgency. We plan to automatically sort follow-ups on each contact card by priority (HIGH first, then MEDIUM, then LOW) to make urgent tasks more visible. The display will show:
   ```
   Follow-ups:
   [RED] Call supplier urgently (HIGH)
   [RED] Confirm order details (HIGH)
   [YELLOW] Review contract (MEDIUM)
   [GREEN] Send thank you note (LOW)
   ```

5. **Make archive operations preserve contact order:** Currently, when using `unarchive`, contacts may not be restored in their original order. We plan to preserve the exact ordering of contacts as they appeared before archiving, including any custom sorting that was applied. This will maintain visual consistency for users who rely on contact positioning.

6. **Add validation for duplicate follow-up names per contact:** Currently, users can add multiple follow-ups with identical names to the same contact, which can cause confusion. We plan to add validation that prevents adding a follow-up if another follow-up with the exact same name (case-insensitive) already exists for that contact. For example:
   ```
   > addfu i/1 f/Call supplier u/HIGH
   Error: A follow-up named "Call supplier" already exists for this contact.
   Please use a different name or delete the existing follow-up first.
   ```

7. **Enhance transaction summary to show income vs expense breakdown:** Currently, the `summary` command only shows the net cashflow as a single number. We plan to enhance it to display a breakdown showing total income, total expenses, and net cashflow separately. For example:
   ```
   > summary
   Financial Summary:
   Total Income: $5,250.00
   Total Expenses: $3,120.50
   Net Cashflow: +$2,129.50
   ```

8. **Add date/timestamp to follow-ups:** Currently, follow-ups have no time tracking, making it difficult to know when they were created or when they should be completed. We plan to add an optional due date field when creating follow-ups and display creation timestamps. Format: `addfu i/PERSON_INDEX f/FOLLOWUP_NAME u/PRIORITY [d/DUE_DATE]`. Display example:
   ```
   Follow-ups:
   [RED] Call supplier urgently (HIGH) - Due: 2025-11-05, Created: 2025-10-28
   ```

9. **Make follow-up deletion request confirmation for HIGH priority tasks:** Currently, deleting any follow-up happens immediately without confirmation, which could lead to accidental deletion of critical tasks. We plan to add a confirmation step only for HIGH priority follow-ups. For example:
   ```
   > deletefu i/1 f/1
   Warning: You are about to delete a HIGH priority follow-up: "Call supplier urgently"
   Type 'deletefu i/1 f/1 confirm' to proceed, or any other command to cancel.
   ```

10. **Add archive information display command:** Currently, users cannot view what's in the archive without running `unarchive`, which would overwrite their current contacts. We plan to add an `archiveinfo` command that displays summary information about archived contacts without loading them. For example:
   ```
   > archiveinfo
   Archive Summary:
   Total Contacts: 45
   Last Archived: 2025-09-15 14:30
   Total Transactions: 128
   Total Follow-ups: 23 (8 HIGH, 10 MEDIUM, 5 LOW)
   Archive Location: /data/archive.json
   ```
