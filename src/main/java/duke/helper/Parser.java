package duke.helper;

/*
 * Note:
 * Slight code refactoring and Javadoc elaboration were aided by OpenAI's ChatGPT.
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import duke.exceptions.InvalidTaskFormatException;

/**
 * The {@code Parser} class provides utility methods for parsing user input
 * into structured components such as dates, times, and task details.
 * <p>
 * It supports multiple date-time formats and can extract key phrases
 * for different task types like {@code todo}, {@code deadline}, and {@code event}.
 */
public class Parser {

    /**
     * An array of supported {@link DateTimeFormatter} patterns used
     * when parsing user-provided date-time strings.
     * <ul>
     *     <li>{@code yyyy-MM-dd HHmm} e.g. {@code 2019-12-02 1800}</li>
     *     <li>{@code yyyy-MM-dd} e.g. {@code 2019-12-02}</li>
     *     <li>{@code d/M/yyyy HHmm} e.g. {@code 2/12/2019 1800}</li>
     *     <li>{@code d/M/yyyy} e.g. {@code 2/12/2019}</li>
     * </ul>
     */
    private static final DateTimeFormatter[] DATE_FORMATS = new DateTimeFormatter[]{
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"), // e.g. 2019-12-02 1800
            DateTimeFormatter.ofPattern("yyyy-MM-dd"), // e.g. 2019-12-02
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"), // e.g. 2/12/2019 1800
            DateTimeFormatter.ofPattern("d/M/yyyy") // e.g. 2/12/2019
    };


    /**
     * Attempts to parse a date-time string into a {@link LocalDateTime}.
     * <p>
     * The method iterates over multiple possible date and date-time formats.
     * If only a date is supplied (no time), it defaults to 23:59 on that day.
     *
     * @param input the raw date-time string entered by the user
     * @return a parsed {@link LocalDateTime} representing the input
     * @throws IllegalArgumentException if the input cannot be parsed with any supported format
     */
    public static LocalDateTime parseDateTime(String input) throws InvalidTaskFormatException {
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
        throw new InvalidTaskFormatException("Unrecognised date format: "
                + input
                + "\nI only recognise "
                + "yyyy-MM-dd HHmm OR yyyy-MM-dd OR d/M/yyyy HHmm OR d/M/yyyy");
    }

    /**
     * Extracts task-related phrases from a raw user input string.
     * <p>
     * Recognises three types of commands:
     * <ul>
     *     <li><b>todo</b>: requires only a description</li>
     *     <li><b>deadline</b>: requires a description and a {@code /by} clause</li>
     *     <li><b>event</b>: requires a description, a {@code /from} clause, and a {@code /to} clause</li>
     * </ul>
     *
     * @param input the raw user input string
     * @return an array of extracted components:
     *         <ul>
     *             <li>For {@code todo}: [command, description]</li>
     *             <li>For {@code deadline}: [command, description, by]</li>
     *             <li>For {@code event}: [command, description, from, to]</li>
     *         </ul>
     * @throws InvalidTaskFormatException if the input is missing required parts or has invalid structure
     */
    public static String[] extractPhrases(String input) throws InvalidTaskFormatException {
        String[] words = input.trim().toLowerCase().split(" ", 2);
        if (words.length == 0) {
            throw new InvalidTaskFormatException("Unaccepted Input");
        }

        String firstWord = words[0];

        return switch (firstWord) {
        case "todo" -> parseTodo(words);
        case "deadline" -> parseDeadline(input, words, firstWord);
        case "event" -> parseEvent(input, words, firstWord);
        default -> throw new InvalidTaskFormatException("Inputted task does not fall under todo, deadline or event");
        };
    }

    private static String[] parseTodo(String[] words) throws InvalidTaskFormatException {
        if (words.length < 2 || words[1].isBlank()) {
            throw new InvalidTaskFormatException("Todo Task cannot be empty");
        }
        return new String[]{"todo", words[1].trim()};
    }

    private static String[] parseDeadline(String input, String[] words, String firstWord)
            throws InvalidTaskFormatException {
        if (words.length < 2) {
            throw new InvalidTaskFormatException("Deadline Task cannot be empty");
        }

        int byIndex = input.indexOf("/by ");
        if (byIndex == -1) {
            throw new InvalidTaskFormatException("Deadline Task needs a /by statement");
        }

        String task = input.substring(firstWord.length(), byIndex).trim();
        String by = input.substring(byIndex + 4).trim();

        return new String[]{"deadline", task, by};
    }

    private static String[] parseEvent(String input, String[] words, String firstWord)
            throws InvalidTaskFormatException {
        if (words.length < 2) {
            throw new InvalidTaskFormatException("Event Task cannot be empty");
        }

        int fromIndex = input.indexOf("/from ");
        if (fromIndex == -1) {
            throw new InvalidTaskFormatException("Event Task needs a /from statement");
        }

        int toIndex = input.indexOf("/to ");
        if (toIndex == -1 || fromIndex > toIndex) {
            throw new InvalidTaskFormatException("Event Task needs a /to statement that comes after /from");
        }

        String task = input.substring(firstWord.length(), fromIndex).trim();
        String from = input.substring(fromIndex + 6, toIndex).trim();
        String to = input.substring(toIndex + 4).trim();

        return new String[]{"event", task, from, to};
    }
}

