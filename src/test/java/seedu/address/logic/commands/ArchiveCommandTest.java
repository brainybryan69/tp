package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ArchiveCommandTest {

    private Model emptyModelStub = new ModelManager(new AddressBook(), new UserPrefs(), new AddressBook());
    private Model modelStub = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new AddressBook());

    @Test
    public void execute_emptyAddressBook_throwsCommandException() {
        ArchiveCommand archiveCommand = new ArchiveCommand();
        assertCommandFailure(archiveCommand, emptyModelStub, ArchiveCommand.MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_nonEmptyAddressBook_archivesAllContact() {
        ArchiveCommand archiveCommand = new ArchiveCommand();
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), getTypicalAddressBook());
        assertCommandSuccess(archiveCommand, modelStub, String.format(ArchiveCommand.MESSAGE_SUCCESS, 7),
                expectedModel);
    }

}
