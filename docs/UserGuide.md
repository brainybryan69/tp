# Atlas User Guide

## What is Atlas?

Running an F&B business means juggling relationships with suppliers, customers, and partners — all while keeping track of countless orders, payments, and deliveries.

Atlas helps you stay on top of it all.

Built specifically for **F&B business owners**, Atlas is a **desktop app** designed to streamline your daily operations through:

1. **👥 Contact Management** – Keep detailed records of your suppliers, customers, and other key stakeholders in one organized place.

2. **💳 Transaction Handling** – Record and monitor all transactions with each contact — from ingredient purchases to customer payments — for clear, effortless tracking.

3. **🔔 Follow-Up Management** – Never miss a payment, delivery, or client check-in again with built-in reminders and follow-up tracking for every contact.

With Atlas, you can cut through the clutter of admin work and focus on running and growing your F&B business with confidence.

*Atlas – Your Recipe for Faster, Smarter F&B Management*

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Table of contents
- [Quick Start](#quick-start)
- [Features](#features)
- <details>
  <summary>General Commands</summary>

    - [Viewing help : `help`](#viewing-help--help)
    - [Clearing all entries : `clear`](#clearing-all-entries--clear)
    - [Exiting the program : `exit`](#exiting-the-program--exit)
    - [Checking net cashflow: `summary`](#checking-net-cashflow-summary)
  </details>
- <details>
  <summary>Person Management Commands</summary>

    - [Adding a person: `add`](#adding-a-person-add)
    - [Listing all persons : `list`](#listing-all-persons--list)
    - [Editing a person : `edit`](#editing-a-person--edit)
    - [Locating persons by name: `find`](#locating-persons-by-name-find)
    - [Locating persons by tag: `find t/`](#locating-persons-by-tag-find-t)
    - [Locating persons by name AND tag: `find n/ t/`](#locating-persons-by-name-and-tag-find-n-t)
    - [Deleting a person : `delete`](#deleting-a-person--delete)
  </details>
- <details>
  <summary>Transaction Management Commands</summary>

    - [Adding a transaction : `addtxn`](#adding-a-transaction--addtxn)
    - [Deleting a transaction : `deletetxn`](#deleting-a-transaction--deletetxn)
    - [Editing a transaction : `editTxn`](#editing-a-transaction--edittxn)
  </details>
- <details>
  <summary>Follow-up Management Commands</summary>

    - [Adding a follow-up : `addfu`](#adding-a-follow-up--addfu)
    - [Deleting a follow-up : `deletefu`](#deleting-a-follow-up--deletefu)
  </details>
- <details>
  <summary>Data Management</summary>

    - [Saving the data](#saving-the-data)
    - [Editing the data file](#editing-the-data-file)
    - [Archiving data files : `archive`](#archiving-data-files--archive)
    - [Unarchive data files : `unarchive`](#unarchive-data-files--unarchive)
  </details>
- [FAQ](#faq)
- [Known Issues](#known-issues)
- [Command Summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick Start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
    <details>
   <summary>Checking Java Version</summary>
   <ul>
        <li> Open Command Prompt (Windows)/ Terminal (MacOS)</li>
        <li> Run the Command: <code>java -version</code></li>
        <li> If you have Java <code>17</code> or above installed, the following output will be returned: <br>
            <div>
                <img style="text-align: left" src="images/javaVersion.png" width="600">
                <p style="text-align: center; width: 600px; margin: 4px 0;">
                    <em>Figure: Output at Command Terminal indicating Java 17 or above</em>
                </p>
            </div>
        </li>
   </ul>
    </details>


2. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-W13-3/tp/releases).


3. Create a folder in your computer for your application (Preferably *Desktop*).


4. Copy the file to the folder you have created (This will be the _home folder_ for Atlas).


5. Open the home folder using the command terminal.
   <details>
   <summary>Windows User:</summary>
        Right-click on your home folder and select <code>Open in Terminal</code><br>
        <div>
            <img src="images/openCdWindows.png" width="200" height="300">
                <p style="text-align: center; width: 200px; margin: 4px 0;">
                    <em>Figure: Option to Open in Terminal for Windows</em>
                </p>
        </div>
    </details>
   <details>
   <summary>MacOS User:</summary>
        Right-click on your home folder and select <code>New Terminal at Folder</code><br>
        <div>
            <img src="images/openCdMac.jpg" width="200" height="300">
                <p style="text-align: center; width: 200px; margin: 4px 0;">
                    <em>Figure: Option to Open in Terminal for MacOS</em>
                </p>
        </div>
    </details>
   

6. Run: `java -jar atlas.jar` to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data:<br>
   <div>
    <img src="images/atlasui.png" width="600"><br>
    <p style="text-align: center; width: 600px; margin: 4px 0;">
        <em>Figure: UI of Atlas upon Application Start</em>
    </p>
   </div><br>


7. Type the command in the command box and press `Enter` to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all stakeholder contacts (i.e suppliers, landlords, delivery personnel, etc).

    * `add n/John Doe p/98765432 e/johnd@example.com` : Adds an unspecified stakeholder named `John Doe` to Atlas.

    * `addtxn i/1 n/Coffee beans a/-50` : Adds an expense transaction to the 1st stakeholder.

    * `delete 3` : Deletes the 3rd stakeholder shown in the current list.

    * `exit` : Exits the app.


8. Refer to the [Features](#features) below for details of each command.

[↩️ Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Features
Every feature in Atlas is designed around the real needs of F&B business owners — where efficiency, organisation, and relationships matter most.
Each feature is represented by a command, allowing you to perform actions quickly and consistently.

Atlas’ capabilities can be grouped into five key categories, each addressing a specific aspect of running your business:

1. ⚙️ [General Commands](#general-commands):<br>
Handle your everyday essentials — from viewing help guides to navigating the system — so you can focus on operations, not admin work.


2. 👥 [Contact Management Commands](#contact-management-commands):<br>
Keep track of your suppliers, customers, and stakeholders all in one place. Easily add, edit, or view contacts to maintain strong business relationships and never lose touch with key stakeholders.


3. 💳 [Transaction Management Commands](#transaction-management-commands):<br>
Record, review, and update your orders and payments per contact, ensuring you always have a clear view of your financial exchanges — whether it’s supplier invoices or customer payments.


4. 🔔 [Follow-Up Management Commands](#follow-up-management-commands):<br>
Stay on top of your commitments. Set and manage follow-ups with suppliers, customers or various stakeholders — like confirming deliveries or payment dates — so nothing slips through the cracks.


5. 📦 [Data Management Commands](#data-management):<br>
Manage your data efficiently — from saving backups to clearing outdated records — keeping your system organised and your workflow smooth.


<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the attributes to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is an attribute which can be used as `add n/John Doe`, `n/` is its attribute prefix.


* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/supplier` or as `n/John Doe`.


* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/supplier`, `t/supplier t/utility` etc.


* Attributes can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.


* Extraneous attributes for commands that do not take in attributes (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.


* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>

[↩️ Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## General Commands

### Viewing help: `help`

**Description**:<br>
Displays a pop-up window, allowing users to copy the URL to Atlas' User Guide Page.

**Format**:<br>
`help`
<br>
<div>
<img src="images/helpMessage.png" width="600">
<br>
<p style="text-align: center; width: 600px; margin: 4px 0;">
    <em>Figure: UI of pop-up Window after executing help command</em>
</p>
</div><br>




### Clearing all entries: `clear`

<div markdown="block" class="alert alert-warning">

**❗Warning**:

* This command will **permanently** delete all contacts in the address book.
* This action is **irreversible**.

</div>

**Description**:<br>
Clears all entries from the address book.

**Format**:<br>
`clear`




### Exiting the program: `exit`

**Description**:<br>
Exits the program.

**Format**:<br>
`exit`




### Checking Net Cashflow: `summary`

**Description**:<br>
Collates the sum of all transactions tied to every single person in Atlas and displays it as a lump sum 

**Format**:<br>
`summary`

* the sum displayed in the GUI will be positive or negative according to the total cashflow of the user


[↩️ Back to Table of Contents](#table-of-contents)


--------------------------------------------------------------------------------------------------------------------
## Contact Management Commands

### Adding a person: `add`

**Description**:<br>
Adds a stakeholder to Atlas.

**Format**:<br>
`add n/NAME p/PHONE_NUMBER e/EMAIL [a/ADDRESS] [t/TAG]…​`

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**

* `NAME`, `PHONE_NUMBER`, and `EMAIL` are **required fields**.
* `ADDRESS` is **optional**. You can add stakeholders without an address.
* Phone numbers must contain at least 3 digits.
* Tags must be valid.

</div>

* Tags must be one of the tags listed below:
  * `LANDLORD`
  * `DELIVERY`
  * `SUPPLIER`
  * `CUSTOMER`
  * `REGULATORY`
  * `FINANCES`
  * `UTILITY`
  * `EMPLOYEE`
  * `OTHERS`

<box type="info" seamless>

**Tip:** A person can have any number of tags (including 0). Tags help you categorize your contacts (e.g., supplier, customer).

**Note:** A person cannot be added if another contact already exists with **either the same phone number or the same email address**. <br>

**Note:** The `NAME` of a person is case-insensitive. `John Doe` is equal to `JOHN DOE`

Example:
1. `add n/John Doe p/98765432 e/johnd@example.com`
2. `add n/John Doe p/88888888 e/johnd@example.com` is not allowed as they share the same email
3. `add n/John Doe p/9765432 e/johnnydoe@another.com` is not allowed as they share the same contact number
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe p/1234567 e/betsycrowe@example.com t/utility t/supplier`
* `add n/Alex Supplier p/91234567 e/alex@supplier.com` (without address)




### Listing all persons : `list`

**Description**:<br>
Shows a list of all persons in the address book.

**Format**:<br>
`list`




### Editing a person : `edit`

**Description**:<br>
Edits an existing person in the address book.


**Format**:<br>
`edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person's tags by typing `t/` without
  specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.




### Locating persons by name: `find`

**Description**:<br>
Finds persons whose names contain any of the given keywords.


**Format**:<br>
`find n/KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>




### Locating persons by tag: `find t/`

**Description**:<br>
Finds persons whose tag names correspond to the given tag keywords.

**Format**:<br>
`find t/[TAG_NAME]`

* The search is case-insensitive. e.g `supplier` will match `SUPPLIER`
* Multiple tag names can be used. e.g `find t/supplier t/delivery t/employee`
* Only full tag names will be matched e.g. `supp` will not match `supplier`
* Persons whose tag matches at least one of the tag names will be returned.




### Locating persons by name AND tag: `find n/ t/` 

**Description**:<br>
Finds persons whose names and tag names correspond to the given name and tag keywords.

**Format**:<br>
`find n/[NAME] t/[TAG_NAME]`

* The search is case-insensitive. e.g. `supplier` will match `SUPPLIER`
* Multiple tag names can be used. e.g. `find t/supplier t/delivery t/employee`
* Multiple names can be used. e.g. `find n/john n/mary`
* Only full names and tag names will be matched e.g. `supp` will not match `supplier`
* Persons whose name/tag matches at least one of the keywords will be returned.




### Deleting a person : `delete`

**Description**:<br>
Deletes the specified person from the address book.

**Format**:<br>
`delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

[↩️ Back to Table of Contents](#table-of-contents)


--------------------------------------------------------------------------------------------------------------------

## Transaction Management Commands

### Adding a transaction : `addtxn`

**Description**:<br>
Adds a transaction to a specified contact. Useful for tracking purchases from suppliers or sales to customers.


**Format**:<br>
`addtxn i/PERSON_INDEX n/TRANSACTION_NAME a/AMOUNT`

* Adds a transaction to the contact at the specified `PERSON_INDEX`.
* The `AMOUNT` determines the transaction type:
    * **Positive amount** (e.g., `50`) = INCOME transaction
    * **Negative amount** (e.g., `-50`) = EXPENSE transaction
* Atlas will automatically recognise the amount and classify it as an INCOME or EXPENSE accordingly
* The amount cannot be zero.
* All transactions are saved automatically and will persist after restarting the app.

<box type="info" seamless>

**Note:** Transactions are displayed on the contact card below their email address.
</box>

Examples:
* `addtxn i/1 n/Coffee beans a/-150.50` - Adds an expense of $150.50 for coffee beans to contact #1
* `addtxn i/2 n/Monthly payment a/500` - Adds an income of $500 for monthly payment to contact #2
* `addtxn i/3 n/Equipment purchase a/-2000` - Adds an expense of $2000 for equipment to contact #3




### Deleting a transaction : `deletetxn`

**Description**:<br>
Deletes a specified transaction from a contact.


**Format**:<br>
`deletetxn i/PERSON_INDEX t/TRANSACTION_INDEX`

* Deletes the transaction at `TRANSACTION_INDEX` from the person at `PERSON_INDEX`.
* The person index refers to the index shown in the displayed person list.
* The transaction index refers to the transaction number shown on the person's card (starting from 1).
* Both indices **must be positive integers** 1, 2, 3, …​

Examples:
* `deletetxn i/1 t/2` - Deletes the 2nd transaction from the 1st contact
* `deletetxn i/3 t/1` - Deletes the 1st transaction from the 3rd contact




### Editing a transaction : `editTxn`

**Description**:<br>
Edits an existing transaction for a specific contact.


**Format**:<br>
`editTxn i/PERSON_INDEX t/TRANSACTION_INDEX [n/TRANSACTION_NAME] [a/AMOUNT]`

*   Edits the transaction at the specified `TRANSACTION_INDEX` for the person at the specified `PERSON_INDEX`.
*   Both indices must be positive integers (1, 2, 3, ...).
*   At least one of the optional fields (`n/` or `a/`) must be provided.
*   Existing values will be overwritten by the new input values.
*   The `AMOUNT` can be a decimal number.

<box type="info" seamless>

**Note:** Upon successful execution, a confirmation message is displayed, showing the contact's details and the updated transaction. For example: `Edited transaction for Person: John Doe; ... Transaction: Coffee: $1.00 (EXPENSE)`
</box>

Examples:
*   `editTxn i/1 t/1 n/Coffee Powder` - Edits the name of the 1st transaction of the 1st person to "Coffee Powder".
*   `editTxn i/2 t/3 a/25` - Edits the amount of the 3rd transaction of the 2nd person to 25.
*   `editTxn i/3 t/2 n/Monthly Rent a/-1200` - Edits both the name and amount of the 2nd transaction of the 3rd person.

[↩️ Back to Table of Contents](#table-of-contents)


--------------------------------------------------------------------------------------------------------------------

## Follow-up Management Commands

### Adding a follow-up : `addfu`

**Description**:<br>
Adds a followup task to a contact


**Format**:<br>
`addfu i/PERSON_INDEX f/FOLLOWUP_NAME u/PRIORITY`

* adds a follow-up task to the contact at the specified `PERSON_INDEX`.
* the PRIORITY field has to be one of the three priority levels
  1. HIGH
  2. MEDIUM
  3. LOW
* the follow-ups are color coded according to their priority levels for easier recognition by the user
* PERSON_INDEX must be a positive integer

**Note:** Followups are displayed on the contact card below their Transactions.




### Deleting a follow-up : `deletefu`

**Description**:<br>
Deletes a followup task from a contact

**Format**:<br>
`deletefu i/PERSON_INDEX f/FOLLOWUP_INDEX`

* deletes a follow-up task specified by `FOLLOWUP_INDEX` from the contact at the specified `PERSON_INDEX`.
* there is no need to specify priority
* Both indices **must be positive integers** 1, 2, 3, …​

[↩️ Back to Table of Contents](#table-of-contents)


--------------------------------------------------------------------------------------------------------------------

## Data Management

### Archiving data files : `archive`

**Description**:<br>
Archives all existing data into an archive file to clean up the contact list.


**Format**:<br>
`archive`

* Prevents accidental deletion while keeping your active list clean
* clears all contacts from the Atlas display and saves it into a -- file
* the path to the archive file is `[JAR file location]/data/archive.json`
* experienced users are likewise free to update the archive file directly




### Unarchive data files : `unarchive`

**Description**:<br>
Restores all data stored in the archive file into Atlas


**Format**:<br>
`unarchive`

* Allows easy reactivation of inactive stakeholder relationships
* restores contacts from the Atlas archive file located in `[JAR file location]/data/archive.json`
* the contacts displayed after the `unarchive` command will be in the same state as Atlas when the `archive` command was run




### Saving the data

Atlas data (including all contacts and transactions) are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.




### Editing the data file

Atlas data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, Atlas will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause Atlas to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

[↩️ Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Atlas home folder.

**Q**: What happens if I try to add a person with the same name?<br>
**A**: Atlas will reject the addition and show an error message. Each person must have a unique name.

**Q**: Can I add a contact without an address?<br>
**A**: Yes! Only name, phone number, and email are required. Address is optional.

**Q**: How do I view my transactions?<br>
**A**: Transactions are automatically displayed on each person's contact card in the main list view.

[↩️ Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

[↩️ Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Command summary


### General Commands

Action     | Format, Examples
-----------|------------------
**Help**   | `help`
**Clear**  | `clear`
**Summary**| `summary`
**Exit**   | `exit`


### Person Management Commands

Action       | Format, Examples
-------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**      | `add n/NAME p/PHONE_NUMBER e/EMAIL [a/ADDRESS] [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com t/supplier`
**List**     | `list`
**Edit**     | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**     | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Delete**   | `delete INDEX`<br> e.g., `delete 3`


### Transaction Management Commands

Action                 | Format, Examples
-----------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add Transaction**    | `addtxn i/PERSON_INDEX n/TRANSACTION_NAME a/AMOUNT` <br> e.g., `addtxn i/1 n/Coffee beans a/-150.50`
**Edit Transaction**   | `editTxn i/PERSON_INDEX t/TRANSACTION_INDEX [n/TRANSACTION_NAME] [a/AMOUNT]` <br> e.g., `editTxn i/1 t/1 n/Coffee Powder a/10`
**Delete Transaction** | `deletetxn i/PERSON_INDEX t/TRANSACTION_INDEX` <br> e.g., `deletetxn i/1 t/2`
**Edit Transaction**   | `editTxn i/PERSON_INDEX t/TRANSACTION_INDEX [n/NAME] [a/AMOUNT]`


### Follow-up Management Commands
Action       | Format, Examples
-------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add Follow-up**      | `addfu i/PERSON_INDEX f/FOLLOWUP_NAME u/PRIORITY`
**Delete Follow-up**     | `deletefu i/PERSON_INDEX f/FOLLOWUP_INDEX`


### Data Management Commands
Action       | Format, Examples
-------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Archive**      | `archive`
**Unarchive**     | `unarchive`

[↩️ Back to Table of Contents](#table-of-contents)
