package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

public class JsonArchiveStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readFromArchive_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readFromArchive(null));
    }

    private java.util.Optional<ReadOnlyAddressBook> readFromArchive(String filePath) throws Exception {
        return new JsonAddressBookStorage(Paths.get(filePath)).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readFromArchive("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readFromArchive("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readFromArchive_invalidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readFromArchive("invalidPersonAddressBook.json"));
    }

    @Test
    public void readFromArchive_invalidAndValidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readFromArchive("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveArchiveBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempArchive.json");
        AddressBook original = getTypicalAddressBook();
        JsonArchiveStorage jsonAddressBookStorage = new JsonArchiveStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveArchive(original, filePath);
        ReadOnlyAddressBook readBack = jsonAddressBookStorage.readFromArchive(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonAddressBookStorage.saveArchive(original, filePath);
        readBack = jsonAddressBookStorage.readFromArchive(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonAddressBookStorage.saveArchive(original); // file path not specified
        readBack = jsonAddressBookStorage.readFromArchive().get(); // file path not specified
        assertEquals(original, new AddressBook(readBack));

    }

    @Test
    public void saveArchivesBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveFromArchive(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveFromArchive(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            new JsonAddressBookStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveFromArchive_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveFromArchive(new AddressBook(), null));
    }
}
