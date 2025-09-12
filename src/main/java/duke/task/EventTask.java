package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import duke.helper.Parser;

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
     * Converts this {@link EventTask} into a string suitable for storage.
     * <p>
     * The format is designed to be easily parsed when loading tasks from storage:
     * <pre>
     *     E | 0 or 1 | description | start | end
     * </pre>
     * where "E" indicates an EventTask, "0" or "1" represents whether the task is done,
     * followed by the task description, start time, and end time.
     *
     * @return a formatted string representing this EventTask for persistent storage
     */
    @Override
    public String toStorageString() {
        return "E | "
                + (this.isDone() ? "1" : "0")
                + " | " + this.getDescription()
                + " | " + this.getStart()
                + " | " + this.getEnd();
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
