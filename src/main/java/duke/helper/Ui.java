package duke.helper;

import duke.list.TaskList;

import duke.task.Task;

/**
 * Handles interactions with the user.
 * All messages printed to the console are wrapped for formatting.
 */
public class Ui {

    /**
     * Wraps a message with separators for better readability.
     *
     * @param message the message to wrap
     * @return formatted message string
     */
    private String wrap(String message) {
        return "-------------------------------------------------\n"
                + message
                + "\n-------------------------------------------------";
    }

    /**
     * Displays the welcome message when the program starts.
     */
    public String showWelcome() {
        return "Hello! I'm S.AI\nWhat can I do for you?";
    }

    /**
     * Displays the goodbye message when the program ends.
     */
    public String showGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Displays a custom error message.
     *
     * @param message the error message to display
     */
    public String showError(String message) {
        return "Error: " + message;
    }

    /**
     * Displays a message when a task is added to the task list.
     *
     * @param taskList the task list containing the newly added task
     */
    public String showAddedTask(TaskList taskList) {
        return "Got it. I've added this task:\n" + taskList.getTask(taskList.size() - 1) + "\n"
                + "Now you have " + (taskList.size()) + " tasks in the list.";
    }

    /**
     * Displays a message when a task is deleted from the task list.
     *
     * @param task the task that was removed
     * @param size current size of the task list after deletion
     */
    public String showDeletedTask(Task task, int size) {
        return "Noted. I have removed this task:\n" + task + "\n"
                + "Now you have " + (size) + " tasks in the list.";
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param taskList the task list to display
     */
    public String showTaskList(TaskList taskList) {
        if (taskList.size() == 0) {
            return "No tasks in your list.";
        } else {
            return taskList.listTasks();
        }
    }

    /**
     * Displays a message when a task is marked as done.
     *
     * @param task the task that was marked
     */
    public String showMarked(Task task) {
        return "Nice! I've marked this task as done:\n" + task;
    }

    /**
     * Displays a message when a task is unmarked (not done).
     *
     * @param task the task that was unmarked
     */
    public String showUnmarked(Task task) {
        return "OK, I've marked this task as not done yet: \n" + task;
    }

    /**
     * Displays a warning message for incorrect command formatting.
     *
     * @param format the expected format string
     */
    public String formatMessageWarning(String format) {
        return "Please format your message as \"" + format + " [task number]\"";
    }

    public String showMessage(String message) {
        return message;
    }
}

