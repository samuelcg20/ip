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

    public void addToList(String listItem) {
        this.list.add(new Task(listItem));
        last_pos += 1;
        this.say("added: " + listItem);
    }

    public void displayList() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < last_pos; i++) {
            if (i == last_pos - 1) {
                output.append(String.format("%d. %s", i + 1, list.get(i)));
            } else {
                output.append(String.format("%d. %s\n", i + 1, list.get(i)));
            }
        }
        System.out.println(wrap(output.toString()));
    }

    public void mark(String input) {
        String[] splitInput = input.split(" ");
        if (splitInput.length != 2) {
            System.out.println(wrap("Please format your message as \"mark [task number]\""));
        } else {
            try {
                int index = Integer.parseInt(splitInput[1]);
                if (index <= last_pos) {
                    Task item = this.list.get(index - 1);
                    item.mark();
                    System.out.println(wrap("Nice! I've marked this task as done:\n" + item));
                } else {
                    System.out.println(wrap("Task number does not exist"));
                }
            } catch (NumberFormatException e){
                System.out.println(wrap("Please format your message as \"mark [task number]\""));
            }
        }
    }

    public void unmark(String input) {
        String[] splitInput = input.split(" ");
        if (splitInput.length != 2) {
            System.out.println(wrap("Please format your message as \"unmark [task number]\""));
        } else {
            try {
                int index = Integer.parseInt(splitInput[1]);
                if (index <= last_pos) {
                    Task item = this.list.get(index - 1);
                    item.unmark();
                    System.out.println(wrap("OK, I've marked this task as not done yet: \n" + item));
                } else {
                    System.out.println(wrap("Task number does not exist"));
                }
            } catch (NumberFormatException e){
                System.out.println(wrap("Please format your message as \"unmark [task number]\""));
            }
        }
    }

    public static void main(String[] args) {
        Sai mySai = new Sai();
        mySai.chat();
    }
}
