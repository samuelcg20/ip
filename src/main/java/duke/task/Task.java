package duke.task;

/**
 * Represents a task with a description and completion status.
 * A Task can be marked as done or not done.
 */
public abstract class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates a new Task with the given description.
     * By default, the task is not marked as done.
     *
     * @param description Description of the task
     */
    public Task(String description) {
        assert description != null && !description.isBlank() : "Task description must not be null or empty";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as done.
     */
    public void mark() {
        assert !isDone : "Task should not already be marked before marking";
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmark() {
        assert isDone : "Task should be marked before unmarking";
        this.isDone = false;
    }

    /**
     * Returns whether this task is done.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the status icon representing whether the task is done.
     * "X" indicates done, " " indicates not done.
     *
     * @return status icon string
     */
    private String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns a string representation of the task,
     * showing its completion status and description.
     *
     * @return formatted string of the task
     */
    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.description);
    }

    /**
     * Converts this task into a string suitable for persistent storage.
     * <p>
     * Each concrete subclass should implement this method to provide a
     * formatted string that can be easily parsed when loading tasks
     * from storage. Typically, the format includes:
     * <ul>
     *     <li>Task type indicator (e.g., "T", "D", "E")</li>
     *     <li>Completion status (0 = not done, 1 = done)</li>
     *     <li>Description and any subclass-specific fields (e.g., deadlines, event times)</li>
     * </ul>
     *
     * @return a formatted string representing this task for storage
     */
    public abstract String toStorageString();
}
