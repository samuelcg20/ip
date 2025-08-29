package duke.task;

/**
 * Represents a task with a description and completion status.
 * A Task can be marked as done or not done.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates a new Task with the given description.
     * By default, the task is not marked as done.
     *
     * @param description Description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as done.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmark() {
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
     * Returns a string representation of the task,
     * showing its completion status and description.
     *
     * @return formatted string of the task
     */
    @Override
    public String toString() { return String.format("[%s] %s", this.getStatusIcon(), this.description); }

    /**
     * Returns the status icon representing whether the task is done.
     * "X" indicates done, " " indicates not done.
     *
     * @return status icon string
     */
    private String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

}
