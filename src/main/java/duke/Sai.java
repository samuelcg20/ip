package duke;

import duke.helper.Parser;
import duke.helper.Storage;
import duke.helper.Ui;

import duke.task.Task;
import duke.task.DeadlineTask;
import duke.task.TodoTask;
import duke.task.EventTask;

import duke.list.TaskList;

import duke.exceptions.InvalidTaskFormatException;
import duke.exceptions.InvalidTaskNumberException;
import duke.exceptions.InvalidTaskTypeException;

import java.util.Scanner;

/**
 * The main chatbot class for Sai, a task management assistant.
 * <p>
 * Supports adding, deleting, marking, unmarking, and listing tasks.
 * Tasks are persisted using {@link Storage} and displayed using {@link Ui}.
 */
public class Sai {
    private TaskList taskList = new TaskList();
    private Storage storage = new Storage();
    private Ui ui = new Ui();

    /**
     * Greets the user with a welcome message.
     */
    public void greet() {
        ui.showWelcome();
    }

    /**
     * Displays a farewell message when the program ends.
     */
    public void farewell() {
        ui.showGoodbye();
    }

    /**
     * Adds a new task to the list based on user input.
     *
     * @param input raw input string (e.g., "todo read book", "deadline project /by 2025-09-01")
     * @throws InvalidTaskTypeException   if the task type is not recognised
     * @throws InvalidTaskFormatException if the input format is invalid
     */
    public void addToList(String input) throws InvalidTaskTypeException, InvalidTaskFormatException {
        String[] inputList = Parser.extractPhrases(input);

        switch (inputList[0]) {
            case "todo" -> this.taskList.addTask(new TodoTask(inputList[1]));
            case "deadline" -> this.taskList.addTask(new DeadlineTask(inputList[1], inputList[2]));
            case "event" -> this.taskList.addTask(new EventTask(inputList[1], inputList[2], inputList[3]));
            default -> {
                throw new InvalidTaskTypeException("Invalid duke.task.Task Type");
            }
        }

        ui.showAddedTask(this.taskList);

        storage.save(taskList);
    }

    /**
     * Deletes a task based on the provided index in the input.
     *
     * @param input user command in the form "delete INDEX"
     * @throws InvalidTaskNumberException if the index is invalid or out of range
     */
    public void delete(String input) throws InvalidTaskNumberException {
        String[] splitInput = input.split(" ");

        if (splitInput.length != 2) {
            ui.formatMessageWarning("delete");
        } else {
            try {
                int index = Integer.parseInt(splitInput[1]);
                if (index <= this.taskList.size()) {
                    Task item = this.taskList.deleteTask(index - 1);

                    ui.showDeletedTask(item, this.taskList.size());

                    storage.save(taskList);
                } else {
                    throw new InvalidTaskNumberException("duke.task.Task number " + index + " does not exist");
                }
            } catch (NumberFormatException e){
                ui.formatMessageWarning("delete");
            }
        }
    }

    /**
     * Marks a task as completed.
     *
     * @param input user command in the form "mark INDEX"
     * @throws InvalidTaskNumberException if the index is invalid or out of range
     */
    public void mark(String input) throws InvalidTaskNumberException {
        String[] splitInput = input.split(" ");
        if (splitInput.length != 2) {
            ui.formatMessageWarning("mark");
        } else {
            try {
                int index = Integer.parseInt(splitInput[1]);
                if (index <= this.taskList.size()) {
                    Task item = this.taskList.getTask(index - 1);
                    item.mark();

                    ui.showMarked(item);

                    storage.save(taskList);
                } else {
                    throw new InvalidTaskNumberException("duke.task.Task number " + index + " does not exist");
                }
            } catch (NumberFormatException e){
                ui.formatMessageWarning("mark");
            }
        }
    }

    /**
     * Unmarks a task (marks it as not completed).
     *
     * @param input user command in the form "unmark INDEX"
     * @throws InvalidTaskNumberException if the index is invalid or out of range
     */
    public void unmark(String input) throws InvalidTaskNumberException {
        String[] splitInput = input.split(" ");
        if (splitInput.length != 2) {
            ui.formatMessageWarning("unmark");
        } else {
            try {
                int index = Integer.parseInt(splitInput[1]);
                if (index <= this.taskList.size()) {
                    Task item = this.taskList.getTask(index - 1);
                    item.unmark();

                    ui.showUnmarked(item);

                    storage.save(taskList);
                } else {
                    throw new InvalidTaskNumberException("duke.task.Task number " + index + " does not exist");
                }
            } catch (NumberFormatException e){
                ui.formatMessageWarning("unmark");
            }
        }
    }

    /**
     * Displays the full list of tasks.
     */
    public void displayList() {
        ui.showTaskList(this.taskList);
    }

    /**
     * Starts the chatbot loop.
     * <p>
     * Accepts user commands until "bye" is entered.
     * Supports commands: list, mark, unmark, delete, todo, deadline, event.
     */
    public void chat() {
        Scanner scanner = new Scanner(System.in);
        this.greet();
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("bye")) {
            if (input.equalsIgnoreCase("list")) {
                this.displayList();
                input = scanner.nextLine();
            } else if (input.toLowerCase().startsWith("mark")) {
                try {
                    this.mark(input);
                } catch (InvalidTaskNumberException e) {
                    ui.showError(e.getMessage());
                }
                input = scanner.nextLine();
            } else if (input.toLowerCase().startsWith("unmark")) {
                try {
                    this.unmark(input);
                } catch (InvalidTaskNumberException e) {
                    ui.showError(e.getMessage());
                }
                input = scanner.nextLine();
            } else if (input.toLowerCase().startsWith("delete")) {
                try {
                    this.delete(input);
                } catch (InvalidTaskNumberException e) {
                    ui.showError(e.getMessage());
                }
                input = scanner.nextLine();
            }
            else {
                try {
                    this.addToList(input);
                } catch (InvalidTaskTypeException | InvalidTaskFormatException e) {
                    ui.showError(e.getMessage());
                }
                input = scanner.nextLine();
            }
        }

        this.farewell();
    }

    /**
     * Main entry point for Sai.
     * Loads stored tasks and begins the chatbot loop.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        Sai mySai = new Sai();
        mySai.taskList = mySai.storage.load();
        mySai.chat();
    }
}
