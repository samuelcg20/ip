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
     * Returns a string representation of the todo task,
     * including its type indicator [T] and completion status.
     *
     * @return formatted string of the todo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
