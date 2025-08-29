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
                +"\n-------------------------------------------------";
    }

    /**
     * Displays the welcome message when the program starts.
     */
    public void showWelcome() {
        System.out.println(wrap("Hello! I'm S.AI\nWhat can I do for you?"));
    }

    /**
     * Displays the goodbye message when the program ends.
     */
    public void showGoodbye() {
        System.out.println(wrap("Bye. Hope to see you again soon!"));
    }

    /**
     * Displays a custom error message.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println(wrap("Error: " + message));
    }

    /**
     * Displays a message when a task is added to the task list.
     *
     * @param taskList the task list containing the newly added task
     */
    public void showAddedTask(TaskList taskList) {
        System.out.println(wrap("Got it. I've added this task:\n" + taskList.getTask(taskList.size() - 1) + "\n"
                + "Now you have " + (taskList.size()) + " tasks in the list."));
    }

    /**
     * Displays a message when a task is deleted from the task list.
     *
     * @param task the task that was removed
     * @param size current size of the task list after deletion
     */
    public void showDeletedTask(Task task, int size) {
        System.out.println(wrap("Noted. I have removed this task:\n" + task + "\n"
                + "Now you have " + (size) + " tasks in the list."));
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param taskList the task list to display
     */
    public void showTaskList(TaskList taskList) {
        if (taskList.size() == 0) {
            System.out.println("No tasks in your list.");
        } else {
            taskList.listTasks();
        }
    }

    /**
     * Displays a message when a task is marked as done.
     *
     * @param task the task that was marked
     */
    public void showMarked(Task task) {
        System.out.println(wrap("Nice! I've marked this task as done:\n" + task));
    }

    /**
     * Displays a message when a task is unmarked (not done).
     *
     * @param task the task that was unmarked
     */
    public void showUnmarked(Task task) {
        System.out.println(wrap("OK, I've marked this task as not done yet: \n" + task));
    }

    /**
     * Displays a warning message for incorrect command formatting.
     *
     * @param format the expected format string
     */
    public void formatMessageWarning(String format) {
        System.out.println(wrap("Please format your message as \"" + format + " [task number]\""));
    }
}

