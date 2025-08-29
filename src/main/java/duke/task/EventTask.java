package duke.task;

import duke.helper.Parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a start and end date/time.
 * Inherits from {@link Task} and extends it by adding scheduling details.
 */
public class EventTask extends Task {
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * Creates a new EventTask with the given description, start, and end date/time.
     *
     * @param description Description of the event
     * @param start       Start date/time string to be parsed
     * @param end         End date/time string to be parsed
     */
    public EventTask(String description, String start, String end) {
        super(description);
        this.start = Parser.parseDateTime(start);
        this.end = Parser.parseDateTime(end);
    }

    /**
     * Returns the start date/time of the event.
     *
     * @return start date/time
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Returns the end date/time of the event.
     *
     * @return end date/time
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Returns a string representation of the event,
     * including description, status, and formatted start/end times.
     *
     * @return formatted string of the event task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + start.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma"))
                + " to: " + end.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + ")";
    }
}
