import java.util.Scanner;

public class Sai {
    private TaskList taskList = new TaskList();
    private Storage storage = new Storage();
    private Ui ui = new Ui();

    public void greet() {
        ui.showWelcome();
    }

    public void farewell() {
        ui.showGoodbye();
    }

    public void addToList(String input) throws InvalidTaskTypeException, InvalidTaskFormatException {
        String[] inputList = Parser.extractPhrases(input);

        switch (inputList[0]) {
            case "todo" -> this.taskList.addTask(new TodoTask(inputList[1]));
            case "deadline" -> this.taskList.addTask(new DeadlineTask(inputList[1], inputList[2]));
            case "event" -> this.taskList.addTask(new EventTask(inputList[1], inputList[2], inputList[3]));
            default -> {
                throw new InvalidTaskTypeException("Invalid Task Type");
            }
        }

        ui.showAddedTask(this.taskList);

        storage.save(taskList);
    }

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
                    throw new InvalidTaskNumberException("Task number " + index + " does not exist");
                }
            } catch (NumberFormatException e){
                ui.formatMessageWarning("delete");
            }
        }
    }

    public void mark(String input) throws InvalidTaskNumberException{
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
                    throw new InvalidTaskNumberException("Task number " + index + " does not exist");
                }
            } catch (NumberFormatException e){
                ui.formatMessageWarning("mark");
            }
        }
    }

    public void unmark(String input) throws InvalidTaskNumberException{
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
                    throw new InvalidTaskNumberException("Task number " + index + " does not exist");
                }
            } catch (NumberFormatException e){
                ui.formatMessageWarning("unmark");
            }
        }
    }

    public void displayList() {
        ui.showTaskList(this.taskList);
    }

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

    public static void main(String[] args) {
        Sai mySai = new Sai();
        mySai.taskList = mySai.storage.load();
        mySai.chat();
    }
}
