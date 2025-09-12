package duke;

import java.util.ArrayList;

import duke.exceptions.InvalidTaskFormatException;
import duke.exceptions.InvalidTaskNumberException;
import duke.exceptions.InvalidTaskTypeException;
import duke.helper.Parser;
import duke.helper.Storage;
import duke.helper.Ui;
import duke.list.TaskList;
import duke.task.DeadlineTask;
import duke.task.EventTask;
import duke.task.Task;
import duke.task.TodoTask;

/**
 * The main chatbot class for Sai, a task management assistant.
 * <p>
 * Supports adding, deleting, marking, unmarking, and listing tasks.
 * Tasks are persisted using {@link Storage} and displayed using {@link Ui}.
 */
public class Sai {
    private TaskList taskList;
    private Storage storage = new Storage();
    private Ui ui = new Ui();

    /**
     * Constructs a new {@code Sai} instance and initialises the task list.
     * <p>
     * When a {@code Sai} object is created, it loads the existing tasks from storage
     * into the {@link #taskList}. If the storage file does not exist or is empty,
     * the task list will be initialised as empty.
     */
    public Sai() {
        this.taskList = this.storage.load();
    }

    /**
     * Greets the user with a welcome message.
     */
    public String greet() {
        return ui.showWelcome();
    }

    /**
     * Displays a farewell message when the program ends.
     */
    public String farewell() {
        return ui.showGoodbye();
    }

    /**
     * Displays the items in the current taskList.
     */
    public String displayList() {
        return ui.showTaskList(this.taskList);
    }

    /**
     * Adds a new task to the task list based on the user's input.
     * <p>
     * The input is parsed into task type and task details using the {@link Parser#extractPhrases(String)} method.
     * Supported task types are:
     * <ul>
     *     <li>{@code todo} – creates a {@link TodoTask}</li>
     *     <li>{@code deadline} – creates a {@link DeadlineTask} with a description and a due date</li>
     *     <li>{@code event} – creates an {@link EventTask} with a description, start time, and end time</li>
     * </ul>
     * After adding the task, the updated task list is saved to storage, and a confirmation message
     * is returned via the {@link Ui#showAddedTask(TaskList)} method.
     * @param input the raw user input string representing the task to add
     * @return a formatted message confirming that the task has been added
     * @throws InvalidTaskTypeException if the task type is not recognized (not "todo", "deadline", or "event")
     * @throws InvalidTaskFormatException if the input format for the task details is invalid
     */
    public String addToList(String input) throws InvalidTaskTypeException, InvalidTaskFormatException {
        String[] inputList = Parser.extractPhrases(input);

        switch (inputList[0]) {
        case "todo" -> this.taskList.addTask(new TodoTask(inputList[1]));
        case "deadline" -> this.taskList.addTask(new DeadlineTask(inputList[1], inputList[2]));
        case "event" -> this.taskList.addTask(new EventTask(inputList[1], inputList[2], inputList[3]));
        default -> throw new InvalidTaskTypeException("Invalid Task Type");
        }

        storage.save(taskList);

        return ui.showAddedTask(this.taskList);
    }

    /**
     * Deletes a task from the task list based on the user's input.
     * <p>
     * The input is expected to follow the format:
     * <pre>
     *     delete &lt;task_number&gt;
     * </pre>
     * If the input does not contain exactly one valid integer argument, or if the
     * specified task number does not exist in the current task list, a warning
     * message is returned instead of deleting a task.
     *
     * @param input the raw user input string beginning with the "delete" command
     * @return a confirmation message if the task is successfully deleted,
     *         or a warning message if the input is invalid
     * @throws InvalidTaskNumberException if the task number is out of range
     */
    public String delete(String input) throws InvalidTaskNumberException {
        String[] splitInput = input.trim().split(" ");

        if (splitInput.length != 2) {
            return ui.formatMessageWarning("delete");
        }

        try {
            int index = Integer.parseInt(splitInput[1]);

            if (index <= 0 || index > this.taskList.size()) {
                throw new InvalidTaskNumberException("Task number " + index + " does not exist");
            }

            Task item = this.taskList.deleteTask(index - 1);
            storage.save(taskList);

            return ui.showDeletedTask(item, this.taskList.size());
        } catch (NumberFormatException e) {
            return ui.formatMessageWarning("delete");
        }
    }

    /**
     * Marks a task in the task list as completed, based on the user's input.
     * <p>
     * The input is expected to follow the format:
     * <pre>
     *     mark &lt;task_number&gt;
     * </pre>
     * If the input does not contain exactly one valid integer argument, or if the
     * specified task number does not exist in the current task list, a warning
     * message is returned instead of updating the task.
     *
     * @param input the raw user input string beginning with the "mark" command
     * @return a confirmation message if the task is successfully marked,
     *         or a warning message if the input is invalid
     * @throws InvalidTaskNumberException if the task number is out of range
     */
    public String mark(String input) throws InvalidTaskNumberException {
        String[] splitInput = input.trim().split(" ");

        if (splitInput.length != 2) {
            return ui.formatMessageWarning("mark");
        }

        try {
            int index = Integer.parseInt(splitInput[1]);

            if (index <= 0 || index > this.taskList.size()) {
                throw new InvalidTaskNumberException("Task number " + index + " does not exist");
            }

            Task item = this.taskList.getTask(index - 1);
            item.mark();
            storage.save(taskList);

            return ui.showMarked(item);
        } catch (NumberFormatException e) {
            return ui.formatMessageWarning("mark");
        }
    }

    /**
     * Marks a task in the task list as not completed, based on the user's input.
     * <p>
     * The input is expected to follow the format:
     * <pre>
     *     unmark &lt;task_number&gt;
     * </pre>
     * If the input does not contain exactly one valid integer argument, or if the
     * specified task number does not exist in the current task list, a warning
     * message is returned instead of updating the task.
     *
     * @param input the raw user input string beginning with the "unmark" command
     * @return a confirmation message if the task is successfully unmarked,
     *         or a warning message if the input is invalid
     * @throws InvalidTaskNumberException if the task number is out of range
     */
    public String unmark(String input) throws InvalidTaskNumberException {
        String[] splitInput = input.trim().split(" ");

        if (splitInput.length != 2) {
            return ui.formatMessageWarning("unmark");
        }

        try {
            int index = Integer.parseInt(splitInput[1]);

            if (index <= 0 || index > this.taskList.size()) {
                throw new InvalidTaskNumberException("Task number " + index + " does not exist");
            }

            Task item = this.taskList.getTask(index - 1);
            item.unmark();
            storage.save(taskList);

            return ui.showUnmarked(item);
        } catch (NumberFormatException e) {
            return ui.formatMessageWarning("unmark");
        }
    }

    /**
     * Searches the task list for tasks containing the given keyword and returns the results.
     * <p>
     * The input is expected to follow the format:
     * <pre>
     *     find &lt;keyword&gt;
     * </pre>
     * If the input is missing a keyword (e.g., just {@code "find"} or {@code "find    "}),
     * an {@link InvalidTaskFormatException} will be thrown. If no tasks match the keyword,
     * an error message will be returned.
     *
     * @param input the raw user input string starting with the "find" command
     * @return a formatted string of matching tasks, or an error message if no matches are found
     * @throws InvalidTaskFormatException if the input format is invalid or the keyword is missing
     */
    public String find(String input) throws InvalidTaskFormatException {
        String[] words = input.trim().split(" ", 2);

        if (words[0].equals("find")) {
            throw new InvalidTaskFormatException("please put a space after 'find'");
        } else if (words.length < 2 || words[1].isBlank()) {
            throw new InvalidTaskFormatException("please input keyword to be found");
        }

        String keyword = words[1];
        ArrayList<Task> found = taskList.findTasks(keyword);

        if (found.isEmpty()) {
            return ui.showError("No matching tasks found.");
        } else {
            StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < found.size(); i++) {
                sb.append((i + 1)).append(". ").append(found.get(i)).append("\n");
            }
            return sb.toString().trim();
        }
    }

    /**
     * Processes the user input command and returns the corresponding response message.
     * <p>
     * This method normalises the input to lowercase and then checks the command type.
     * Depending on the command, it will delegate the request to the appropriate handler
     * method (e.g., {@code displayList()}, {@code mark()}, {@code delete()}, etc.).
     * <p>
     * Supported commands:
     * <ul>
     *     <li><b>list</b> – Displays the current task list.</li>
     *     <li><b>mark &lt;index&gt;</b> – Marks a task as completed.</li>
     *     <li><b>unmark &lt;index&gt;</b> – Marks a task as not completed.</li>
     *     <li><b>delete &lt;index&gt;</b> – Deletes a task from the list.</li>
     *     <li><b>find &lt;keyword&gt;</b> – Finds tasks containing the given keyword.</li>
     *     <li><b>bye</b> – Returns a farewell message.</li>
     *     <li><i>Any other input</i> – Attempts to add the input as a new task.</li>
     * </ul>
     *
     * @param input the raw user input string
     * @return the response message to be displayed to the user
     */
    public String getResponse(String input) {
        input = input.toLowerCase();

        try {
            if (input.equals("list")) {
                return this.displayList();
            } else if (input.startsWith("mark")) {
                return this.mark(input);
            } else if (input.startsWith("unmark")) {
                return this.unmark(input);
            } else if (input.startsWith("delete")) {
                return this.delete(input);
            } else if (input.startsWith("find")) {
                return this.find(input);
            } else if (input.equals("bye")) {
                return this.farewell();
            } else {
                return this.addToList(input);
            }
        } catch (InvalidTaskTypeException | InvalidTaskFormatException | InvalidTaskNumberException e) {
            return e.getMessage();
        }
    }
}
