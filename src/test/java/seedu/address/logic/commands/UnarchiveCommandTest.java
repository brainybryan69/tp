package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UnarchiveCommandTest {

    private Model emptyArchiveModelStub = new ModelManager(new AddressBook(), new UserPrefs(), new AddressBook());
    private Model notEmptyArchivemodelStub = new ModelManager(new AddressBook(), new UserPrefs(),
            getTypicalAddressBook());

    @Test
    public void execute_emptyArchive_throwsCommandException() {
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand();
        assertCommandFailure(unarchiveCommand, emptyArchiveModelStub, UnarchiveCommand.MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_nonEmptyArchive_unarchivesAllContact() {
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand();
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new AddressBook());
        assertCommandSuccess(unarchiveCommand, notEmptyArchivemodelStub,
                String.format(UnarchiveCommand.MESSAGE_SUCCESS, 7), expectedModel);
    }

}
