package duke.task;

import duke.helper.Parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task, which has a specific due date/time.
 * Inherits from {@link Task} and adds a "by" deadline.
 */
public class DeadlineTask extends Task {
    private LocalDateTime by;

    /**
     * Creates a new DeadlineTask with the given description and deadline.
     *
     * @param description Description of the deadline task
     * @param by          Deadline date/time string to be parsed
     */
    public DeadlineTask(String description, String by) {
        super(description);
        this.by = Parser.parseDateTime(by);
    }

    /**
     * Returns the deadline of this task.
     *
     * @return LocalDateTime representing the deadline
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a string representation of the deadline task,
     * including its type indicator [D], status, description, and formatted deadline.
     *
     * @return formatted string of the deadline task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + ")";
    }
}
