package pl.javastart.library.io.file;

import pl.javastart.library.exception.NoSuchFileTypeException;
import pl.javastart.library.io.ConsolePrinter;
import pl.javastart.library.io.DataReader;
import pl.javastart.library.model.Publication;

public class FileManagerBuilder {
    private ConsolePrinter printer;
    private DataReader reader;

    public FileManagerBuilder(ConsolePrinter printer, DataReader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    public FileManager build() {
        printer.printLine("Wybierz format danych:");
        FileType fileType = getFileType();
        switch (fileType) {
            case CSV:
                return new CsvFileManager();
            case SERIAL:
                return new SerializableFileManager() {
                    @java.lang.Override
                    public java.lang.String toString() {
                        return null;
                    }

                    @java.lang.Override
                    public int hashCode() {
                        return 0;
                    }

                    @java.lang.Override
                    public int compareTo(Publication p) {
                        return 0;
                    }

                    @java.lang.Override
                    public boolean equals(java.lang.Object o) {
                        return false;
                    }
                };
            default:
                throw new NoSuchFileTypeException("Nieobsługiwany typ danych");
        }
    }

    private FileType getFileType() {
        boolean typeOk = false;
        FileType result = null;
        do {
            printTypes();
            String type = reader.getString().toUpperCase();
            try {
                result = FileType.valueOf(type);
                typeOk = true;
            } catch (IllegalArgumentException e) {
                printer.printLine("Nieobsługiwany typ danych, wybierz ponownie.");
            }
        } while (!typeOk);

        return result;
    }

    private void printTypes() {
        for (FileType value : FileType.values()) {
            printer.printLine(value.name());
        }
    }
}