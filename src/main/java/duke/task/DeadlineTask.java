package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import duke.helper.Parser;

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
     * Converts this {@link DeadlineTask} into a string suitable for storage.
     * <p>
     * The format is designed to be easily parsed when loading tasks from storage:
     * <pre>
     *     D | 0 or 1 | description | by
     * </pre>
     * where "D" indicates a DeadlineTask, "0" or "1" represents whether the task is done,
     * followed by the task description and the deadline date/time.
     *
     * @return a formatted string representing this DeadlineTask for persistent storage
     */
    @Override
    public String toStorageString() {
        return "D | "
                + (this.isDone() ? "1" : "0")
                + " | " + this.getDescription()
                + " | " + this.getBy();
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof DeadlineTask) {
            return ((DeadlineTask) o).getDescription().equals(this.getDescription())
                    && ((DeadlineTask) o).getBy().equals(this.getBy());
        }
        return false;
    }
}
