package duke.helper;

import duke.list.TaskList;
import duke.task.Task;

public class Ui {
    private String wrap(String message) {
        return "-------------------------------------------------\n"
                + message
                +"\n-------------------------------------------------";
    }

    public void showWelcome() {
        System.out.println(wrap("Hello! I'm S.AI\nWhat can I do for you?"));
    }

    public void showGoodbye() {
        System.out.println(wrap("Bye. Hope to see you again soon!"));
    }

    public void showLoadingError() {
        System.out.println(wrap("Error loading tasks from file."));
    }

    public void showError(String message) {
        System.out.println(wrap("Error: " + message));
    }

    public void showAddedTask(TaskList taskList) {
        System.out.println(wrap("Got it. I've added this task:\n" + taskList.getTask(taskList.size() - 1) + "\n"
                + "Now you have " + (taskList.size()) + " tasks in the list."));
    }

    public void showDeletedTask(Task task, int size) {
        System.out.println(wrap("Noted. I have removed this task:\n" + task + "\n"
                + "Now you have " + (size) + " tasks in the list."));
    }

    public void showTaskList(TaskList taskList) {
        if (taskList.size() == 0) {
            System.out.println("No tasks in your list.");
        } else {
            StringBuilder output = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < taskList.size(); i++) {
                if (i == taskList.size() - 1) {
                    output.append(String.format("%d. %s", i + 1, taskList.getTask(i)));
                } else {
                    output.append(String.format("%d. %s\n", i + 1, taskList.getTask(i)));
                }
            }
            System.out.println(wrap(output.toString()));
        }
    }

    public void showMarked(Task task) {
        System.out.println(wrap("Nice! I've marked this task as done:\n" + task));
    }

    public void showUnmarked(Task task) {
        System.out.println(wrap("OK, I've marked this task as not done yet: \n" + task));
    }

    public void formatMessageWarning(String format) {
        System.out.println(wrap("Please format your message as \"" + format + " [task number]\""));
    }
}

