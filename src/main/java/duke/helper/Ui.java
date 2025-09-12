package duke.helper;

import duke.list.TaskList;
import duke.task.Task;

/**
 * Handles interactions with the user.
 * All messages printed to the console are wrapped for formatting.
 */
public class Ui {

    /**
     * Returns the welcome message displayed to the user when the program starts.
     *
     * @return a greeting string introducing S.AI and prompting the user for input
     */
    public String showWelcome() {
        return "Hello! I'm S.AI\nWhat can I do for you?";
    }

    /**
     * Returns the farewell message displayed to the user when exiting the program.
     *
     * @return a goodbye string expressing a farewell
     */
    public String showGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Formats and returns an error message to be displayed to the user.
     *
     * @param message the specific error message to display
     * @return a formatted string prefixed with "Error: " followed by the provided message
     */
    public String showError(String message) {
        return "Error: " + message;
    }

    /**
     * Returns a confirmation message after a task has been added to the task list.
     * <p>
     * The message includes the details of the task that was just added and
     * the updated total number of tasks in the list.
     *
     * @param taskList the current {@link TaskList} containing all tasks
     * @return a formatted string confirming the addition of the last task and showing the updated task count
     */
    public String showAddedTask(TaskList taskList) {
        return "Got it. I've added this task:\n" + taskList.getTask(taskList.size() - 1) + "\n"
                + "Now you have " + (taskList.size()) + " tasks in the list.";
    }

    /**
     * Returns a confirmation message after a task has been deleted from the task list.
     * <p>
     * The message includes the details of the task that was removed and
     * the updated total number of tasks remaining in the list.
     *
     * @param task the {@link Task} that was deleted
     * @param size the updated number of tasks remaining in the task list
     * @return a formatted string confirming the deletion of the task and showing the updated task count
     */
    public String showDeletedTask(Task task, int size) {
        return "Noted. I have removed this task:\n" + task + "\n"
                + "Now you have " + (size) + " tasks in the list.";
    }

    /**
     * Returns a formatted string representation of all tasks in the task list.
     * <p>
     * If the task list is empty, a message indicating that there are no tasks is returned.
     * Otherwise, the list of tasks is returned as provided by {@link TaskList#listTasks()}.
     *
     * @param taskList the {@link TaskList} containing all tasks
     * @return a string showing all tasks in the list, or a message if the list is empty
     */
    public String showTaskList(TaskList taskList) {
        if (taskList.size() == 0) {
            return "No tasks in your list.";
        } else {
            return taskList.listTasks();
        }
    }

    /**
     * Returns a confirmation message after a task has been marked as completed.
     * <p>
     * The message includes the details of the task that was marked as done.
     *
     * @param task the {@link Task} that was marked as completed
     * @return a formatted string confirming that the task has been marked as done
     */
    public String showMarked(Task task) {
        return "Nice! I've marked this task as done:\n" + task;
    }

    /**
     * Returns a confirmation message after a task has been marked as not completed.
     * <p>
     * The message includes the details of the task that was unmarked.
     *
     * @param task the {@link Task} that was marked as not done
     * @return a formatted string confirming that the task has been unmarked
     */
    public String showUnmarked(Task task) {
        return "OK, I've marked this task as not done yet: \n" + task;
    }

    /**
     * Returns a warning message indicating the correct format for a user command.
     * <p>
     * The message instructs the user to follow the expected format for commands
     * that require a task number.
     *
     * @param format the command name (e.g., "mark", "unmark", "delete")
     * @return a formatted warning string showing the proper command syntax
     */
    public String formatMessageWarning(String format) {
        return "Please format your message as \"" + format + " [task number]\"";
    }
}

