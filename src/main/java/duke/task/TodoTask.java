package duke.task;

/**
 * Represents a todo task, which is a simple task without a date or time.
 * Inherits from {@link Task}.
 */
public class TodoTask extends Task {

    /**
     * Creates a new TodoTask with the given description.
     *
     * @param description Description of the todo task
     */
    public TodoTask(String description) {
        super(description);
    }

    /**
     * Converts this {@link TodoTask} into a string suitable for storage.
     * <p>
     * The format is designed to be easily parsed when loading tasks from storage:
     * <pre>
     *     T | 0 or 1 | description
     * </pre>
     * where "T" indicates a TodoTask, "0" or "1" represents whether the task is done,
     * followed by the task description.
     *
     * @return a formatted string representing this TodoTask for persistent storage
     */
    @Override
    public String toStorageString() {
        return "T | "
                + (this.isDone() ? "1" : "0")
                + " | " + this.getDescription();
    }

    /**
     * Returns a string representation of the todo task,
     * including its type indicator [T] and completion status.
     *
     * @return formatted string of the todo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TodoTask) {
            return ((TodoTask) o).getDescription().equals(this.getDescription());
        }
        return false;
    }
}
