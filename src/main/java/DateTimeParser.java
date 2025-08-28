import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeParser {
    private static final DateTimeFormatter[] DATE_FORMATS = new DateTimeFormatter[]{
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"), // e.g. 2019-12-02 1800
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),      // e.g. 2019-12-02
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),   // e.g. 2/12/2019 1800
            DateTimeFormatter.ofPattern("d/M/yyyy")         // e.g. 2/12/2019
    };


    public static LocalDateTime parseDateTime(String input) {
        for (DateTimeFormatter fmt : DATE_FORMATS) {
            // Try parsing into LocalDateTime
            try {
                return LocalDateTime.parse(input, fmt);
            } catch (DateTimeParseException e) {
                // continue to next
            }

            // Try parsing into LocalDate (if no time is provided)
            try {
                LocalDate d = LocalDate.parse(input, fmt);
                return d.atTime(23, 59);
            } catch (DateTimeParseException e) {
                // continue to next iteration
            }
        }
        throw new IllegalArgumentException("Unrecognised date format: " + input);
    }
}

