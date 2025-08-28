import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Sai {
    private List<Task> list = new ArrayList<>();
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
            case "todo" -> this.list.add(new TodoTask(inputList[1]));
            case "deadline" -> this.list.add(new DeadlineTask(inputList[1], inputList[2]));
            case "event" -> this.list.add(new EventTask(inputList[1], inputList[2], inputList[3]));
            default -> {
                throw new InvalidTaskTypeException("Invalid Task Type");
            }
        }

        ui.showAddedTask(this.list);

        storage.save(list);
    }

    public void delete(String input) throws InvalidTaskNumberException {
        String[] splitInput = input.split(" ");

        if (splitInput.length != 2) {
            ui.formatMessageWarning("delete");
        } else {
            try {
                int index = Integer.parseInt(splitInput[1]);
                if (index <= this.list.size()) {
                    Task item = this.list.remove(index - 1);

                    ui.showDeletedTask(item, this.list.size());

                    storage.save(list);
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
                if (index <= this.list.size()) {
                    Task item = this.list.get(index - 1);
                    item.mark();

                    ui.showMarked(item);

                    storage.save(list);
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
                if (index <= this.list.size()) {
                    Task item = this.list.get(index - 1);
                    item.unmark();

                    ui.showUnmarked(item);

                    storage.save(list);
                } else {
                    throw new InvalidTaskNumberException("Task number " + index + " does not exist");
                }
            } catch (NumberFormatException e){
                ui.formatMessageWarning("unmark");
            }
        }
    }

    public void displayList() {
        ui.showTaskList(this.list);
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
        mySai.list = mySai.storage.load();      // set last_pos correctly
        mySai.chat();
    }
}
