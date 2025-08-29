package duke.helper;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
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

    public static String[] extractPhrases(String input) throws InvalidTaskFormatException {
        String firstWord = "";
        String task = "";
        String by = "";
        String from = "";
        String to = "";

        // First word
        String[] words = input.toLowerCase().split(" ", 2);
        if (words.length > 0) {
            firstWord = words[0];
        } else {
            throw new InvalidTaskFormatException("Unaccepted Input");
        }

        switch (firstWord) {

            case "todo" -> {
                if (words.length > 1) {
                    task = words[1].trim();
                    return new String[] {firstWord, task};
                } else {
                    throw new InvalidTaskFormatException("Todo duke.task.Task cannot be empty");
                }

            }

            case "deadline" -> {
                // After first word up to /by
                int byIndex = input.indexOf("/by");
                if (words.length > 1) {
                    if (byIndex != -1) {
                        task = input.substring(firstWord.length(), byIndex).trim();
                    } else {
                        throw new InvalidTaskFormatException("Deadline duke.task.Task needs a /by statement");
                    }
                } else {
                    throw new InvalidTaskFormatException("Deadline duke.task.Task cannot be empty");
                }

                // Extract after /to
                by = input.substring(byIndex + 4).trim();

                return new String[] {firstWord, task, by};
            }

            case "event" -> {
                // After first word up to /from
                int fromIndex = input.indexOf("/from");
                if (words.length > 1) {
                    if (fromIndex != -1) {
                        task = input.substring(firstWord.length(), fromIndex).trim();
                    } else {
                        throw new InvalidTaskFormatException("Event duke.task.Task needs a /from statement");
                    }
                } else {
                    throw new InvalidTaskFormatException("Event duke.task.Task cannot be empty");
                }

                // Extract between /from and /to
                int toIndex = input.indexOf("/to");
                if (toIndex != -1 && fromIndex < toIndex) {
                    from = input.substring(fromIndex + 6, toIndex).trim();
                } else {
                    throw new InvalidTaskFormatException("Event duke.task.Task needs a /to statement that comes after /from");
                }

                // Extract after /to
                to = input.substring(toIndex + 4).trim();

                return new String[]{firstWord, task, from, to};
            }

            default -> {
                throw new InvalidTaskFormatException("Inputted task does not fall under todo, deadline or event");
            }
        }
    }
}

