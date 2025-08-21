import java.util.Scanner;
import java.util.ArrayList;

public class Sai {
    private static final String NAME = "S.AI";
    private final String GREETING_MESSAGE = "Hello! I'm %s \nWhat can I do for you?";
    private final String FAREWELL_MESSAGE = "Bye. Hope to see you again soon!";
    private ArrayList<Task> list = new ArrayList<>();
    private int last_pos = 0;

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
                this.mark(input);
                input = scanner.nextLine();
            } else if (input.toLowerCase().startsWith("unmark")) {
                this.unmark(input);
                input = scanner.nextLine();
            }
            else {
                this.addToList(input);
                input = scanner.nextLine();
            }
        }

        this.farewell();
    }

    public void addToList(String input) {
        String[] inputList = extractPhrases(input);

        switch (inputList[0]) {
            case "todo" -> this.list.add(new TodoTask(inputList[1]));
            case "deadline" -> this.list.add(new DeadlineTask(inputList[1], inputList[2]));
            case "event" -> this.list.add(new EventTask(inputList[1], inputList[2], inputList[3]));
            default -> {
                this.say("Wrong task type");
                return;
            }
        }

        this.say("Got it. I've added this task:\n" + this.list.get(last_pos) + "\n" + "Now you have " + (last_pos + 1) + " tasks in the list.");
        last_pos += 1;
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

    public void mark(String input) {
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
                } else {
                    this.say("Task number does not exist");
                }
            } catch (NumberFormatException e){
                this.say("Please format your message as \"mark [task number]\"");
            }
        }
    }

    public void unmark(String input) {
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
                } else {
                    this.say("Task number does not exist");
                }
            } catch (NumberFormatException e){
                this.say("Please format your message as \"unmark [task number]\"");
            }
        }
    }

    public String[] extractPhrases(String input) {
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
            return new String[] {firstWord};
        }

        switch (firstWord) {

            case "todo" -> {
                task = words[1].trim();
                return new String[] {firstWord, task};
            }

            case "deadline" -> {
                // After first word up to /by
                int byIndex = input.indexOf("/by");
                if (words.length > 1) {
                    if (byIndex != -1) {
                        task = input.substring(firstWord.length(), byIndex).trim();
                    } else {
                        task = words[1].trim();
                    }
                }

                // Extract after /to
                if (byIndex != -1) {
                    by = input.substring(byIndex + 4).trim();
                }

                return new String[] {firstWord, task, by};
            }

            case "event" -> {
                // After first word up to /from
                int fromIndex = input.indexOf("/from");
                if (words.length > 1) {
                    if (fromIndex != -1) {
                        task = input.substring(firstWord.length(), fromIndex).trim();
                    } else {
                        task = words[1].trim();
                    }
                }

                // Extract between /from and /to
                int toIndex = input.indexOf("/to");
                if (fromIndex != -1 && toIndex != -1 && fromIndex < toIndex) {
                    from = input.substring(fromIndex + 6, toIndex).trim();  // skip "/from "
                }

                // Extract after /to
                if (toIndex != -1) {
                    to = input.substring(toIndex + 4).trim();  // skip "/to "
                }

                return new String[]{firstWord, task, from, to};
            }

            default -> {
                return new String[]{firstWord};
            }
        }
    }

    public static void main(String[] args) {
        Sai mySai = new Sai();
        mySai.chat();
    }
}
