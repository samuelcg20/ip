import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Sai {
    private static final String NAME = "S.AI";
    private final String GREETING_MESSAGE = "Hello! I'm %s \nWhat can I do for you?";
    private final String FAREWELL_MESSAGE = "Bye. Hope to see you again soon!";
    private List<Task> list = new ArrayList<>();
    private int last_pos = 0;
    private Storage storage = new Storage();

    private String wrap(String message) {
        return "-------------------------------------------------\n"
                + message
                +"\n-------------------------------------------------";
    }

    public void greet() {
        System.out.println(wrap(String.format(GREETING_MESSAGE, Sai.NAME)));
    }

    public void farewell() {
        System.out.println(wrap(FAREWELL_MESSAGE));
        return;
    }

    private void say(String message) {
        System.out.println(wrap(message));
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
                    this.say(e.getMessage());
                }
                input = scanner.nextLine();
            } else if (input.toLowerCase().startsWith("unmark")) {
                try {
                    this.unmark(input);
                } catch (InvalidTaskNumberException e) {
                    this.say(e.getMessage());
                }
                input = scanner.nextLine();
            } else if (input.toLowerCase().startsWith("delete")) {
                try {
                    this.delete(input);
                } catch (InvalidTaskNumberException e) {
                    this.say(e.getMessage());
                }
                input = scanner.nextLine();
            }
            else {
                try {
                    this.addToList(input);
                } catch (InvalidTaskTypeException | InvalidTaskFormatException e) {
                    this.say(e.getMessage());
                }
                input = scanner.nextLine();
            }
        }

        this.farewell();
    }

    public void addToList(String input) throws InvalidTaskTypeException, InvalidTaskFormatException {
        String[] inputList = extractPhrases(input);

        switch (inputList[0]) {
            case "todo" -> this.list.add(new TodoTask(inputList[1]));
            case "deadline" -> this.list.add(new DeadlineTask(inputList[1], inputList[2]));
            case "event" -> this.list.add(new EventTask(inputList[1], inputList[2], inputList[3]));
            default -> {
                throw new InvalidTaskTypeException("Invalid Task Type");
            }
        }

        this.say("Got it. I've added this task:\n" + this.list.get(last_pos) + "\n"
                + "Now you have " + (last_pos + 1) + " tasks in the list.");
        last_pos += 1;
        storage.save(list);
    }

    public void displayList() {
        StringBuilder output = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < last_pos; i++) {
            if (i == last_pos - 1) {
                output.append(String.format("%d. %s", i + 1, list.get(i)));
            } else {
                output.append(String.format("%d. %s\n", i + 1, list.get(i)));
            }
        }
        this.say(output.toString());
    }

    public void mark(String input) throws InvalidTaskNumberException{
        String[] splitInput = input.split(" ");
        if (splitInput.length != 2) {
            this.say("Please format your message as \"mark [task number]\"");
        } else {
            try {
                int index = Integer.parseInt(splitInput[1]);
                if (index <= last_pos) {
                    Task item = this.list.get(index - 1);
                    item.mark();
                    this.say("Nice! I've marked this task as done:\n" + item);
                    storage.save(list);
                } else {
                    throw new InvalidTaskNumberException("Task number " + index + " does not exist");
                }
            } catch (NumberFormatException e){
                this.say("Please format your message as \"mark [task number]\"");
            }
        }
    }

    public void unmark(String input) throws InvalidTaskNumberException{
        String[] splitInput = input.split(" ");
        if (splitInput.length != 2) {
            this.say("Please format your message as \"unmark [task number]\"");
        } else {
            try {
                int index = Integer.parseInt(splitInput[1]);
                if (index <= last_pos) {
                    Task item = this.list.get(index - 1);
                    item.unmark();
                    this.say("OK, I've marked this task as not done yet: \n" + item);
                    storage.save(list);
                } else {
                    throw new InvalidTaskNumberException("Task number " + index + " does not exist");
                }
            } catch (NumberFormatException e){
                this.say("Please format your message as \"unmark [task number]\"");
            }
        }
    }

    public String[] extractPhrases(String input) throws InvalidTaskFormatException {
        String firstWord = "";
        String task = "";
        String by = "";
        String from = "";
        String to = "";

        // First word
        String[] words = input.toLowerCase().split(" ", 2);
        if (words.length > 0) {
            firstWord = words[0];
        } else {
            throw new InvalidTaskFormatException("Unaccepted Input");
        }

        switch (firstWord) {

            case "todo" -> {
                if (words.length > 1) {
                    task = words[1].trim();
                    return new String[] {firstWord, task};
                } else {
                    throw new InvalidTaskFormatException("Todo Task cannot be empty");
                }

            }

            case "deadline" -> {
                // After first word up to /by
                int byIndex = input.indexOf("/by");
                if (words.length > 1) {
                    if (byIndex != -1) {
                        task = input.substring(firstWord.length(), byIndex).trim();
                    } else {
                        throw new InvalidTaskFormatException("Deadline Task needs a /by statement");
                    }
                } else {
                    throw new InvalidTaskFormatException("Deadline Task cannot be empty");
                }

                // Extract after /to
                by = input.substring(byIndex + 4).trim();

                return new String[] {firstWord, task, by};
            }

            case "event" -> {
                // After first word up to /from
                int fromIndex = input.indexOf("/from");
                if (words.length > 1) {
                    if (fromIndex != -1) {
                        task = input.substring(firstWord.length(), fromIndex).trim();
                    } else {
                        throw new InvalidTaskFormatException("Event Task needs a /from statement");
                    }
                } else {
                    throw new InvalidTaskFormatException("Event Task cannot be empty");
                }

                // Extract between /from and /to
                int toIndex = input.indexOf("/to");
                if (toIndex != -1 && fromIndex < toIndex) {
                    from = input.substring(fromIndex + 6, toIndex).trim();
                } else {
                    throw new InvalidTaskFormatException("Event Task needs a /to statement that comes after /from");
                }

                // Extract after /to
                to = input.substring(toIndex + 4).trim();

                return new String[]{firstWord, task, from, to};
            }

            default -> {
                throw new InvalidTaskFormatException("Inputted task does not fall under todo, deadline or event");
            }
        }
    }

    public void delete(String input) throws InvalidTaskNumberException {
        String[] splitInput = input.split(" ");

        if (splitInput.length != 2) {
            this.say("Please format your message as \"delete [task number]\"");
        } else {
            try {
                int index = Integer.parseInt(splitInput[1]);
                if (index <= last_pos) {
                    Task item = this.list.remove(index - 1);
                    last_pos -= 1;
                    this.say("Noted. I have removed this task:\n" + item + "\n"
                            + "Now you have " + (last_pos) + " tasks in the list.");
                    storage.save(list);
                } else {
                    throw new InvalidTaskNumberException("Task number " + index + " does not exist");
                }
            } catch (NumberFormatException e){
                this.say("Please format your message as \"delete [task number]\"");
            }
        }
    }

    public static void main(String[] args) {
        Sai mySai = new Sai();
        mySai.list = mySai.storage.load();        // load saved tasks
        mySai.last_pos = mySai.list.size();       // set last_pos correctly
        mySai.chat();
    }
}
