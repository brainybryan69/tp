package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
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
    public Optional<ReadOnlyAddressBook> readFromArchive() throws DataLoadingException {
        return readFromArchive(filePath);
    }

    @Override
    public Optional<ReadOnlyAddressBook> readFromArchive(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableAddressBook> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAddressBook.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveArchive(ReadOnlyAddressBook archive) throws IOException {
        saveArchive(archive, filePath);
    }

    @Override
    public void saveArchive(ReadOnlyAddressBook archive, Path filePath) throws IOException {
        requireNonNull(archive);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(archive), filePath);
    }
}
