package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;

public interface ArchiveStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getArchiveFilePath();

    Optional<ReadOnlyAddressBook> readFromAddressBook() throws DataLoadingException;

    Optional<ReadOnlyAddressBook> readFromAddressBook(Path filePath) throws DataLoadingException;

    void saveArchive(ReadOnlyAddressBook addressBook) throws IOException;

    void saveArchive(ReadOnlyAddressBook addressBook, Path filePath) throws IOException;

}
