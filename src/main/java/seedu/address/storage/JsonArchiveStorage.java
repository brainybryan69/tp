package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class JsonArchiveStorage implements ArchiveStorage{
    private static final Logger logger = LogsCenter.getLogger(JsonArchiveStorage.class);

    private Path filePath;

    public JsonArchiveStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getArchiveFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readFromAddressBook() throws DataLoadingException {
        return Optional.empty();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readFromAddressBook(Path filePath) throws DataLoadingException {
        return Optional.empty();
    }

    @Override
    public void saveArchive(ReadOnlyAddressBook addressBook) throws IOException {
        saveArchive(addressBook, filePath);
    }

    @Override
    public void saveArchive(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
    }
}
