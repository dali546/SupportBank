package training.supportbank;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static training.supportbank.Main.LOGGER;

class FileReader {

    public static final String CSV = "DodgyTransactions2015.csv";

    static List<Transaction> load() throws IOException {
        LOGGER.debug("Loading File " + CSV);
        List<String> lines = Files.readAllLines(Paths.get(CSV));
        LOGGER.debug("Beginning To Create Transaction");
        return loadLinesIntoTransactions(lines);
    }

    private static List<Transaction> loadLinesIntoTransactions(List<String> lines) {
        return lines.stream()
                .skip(1)
                .map(line -> createTransaction(line.split(",")))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static Transaction createTransaction(String[] list) {
        LOGGER.debug("Creating Transaction From " + Arrays.toString(list));
        try{
            return new Transaction(
                    LocalDate.parse(list[0], DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    list[1],
                    list[2],
                    list[3],
                    Double.parseDouble(list[4])
            );
        } catch (NumberFormatException e){
            LOGGER.warn("Invalid Amount provided" + e);
            return null;
        } catch (DateTimeParseException e){
            LOGGER.warn("Invalid Date Provided" + e);
            return null;
        }

    }
}
