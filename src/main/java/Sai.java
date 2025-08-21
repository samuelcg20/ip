public class Sai {
    private static final String NAME = "S.AI";
    private final String GREETING_MESSAGE = "Hello! I'm %s \nWhat can I do for you?\n";
    private final String FAREWELL_MESSAGE = "Bye. Hope to see you again soon!";

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
    }

    public static void main(String[] args) {
        Sai mySai = new Sai();
        mySai.greet();
        mySai.farewell();
    }
}
