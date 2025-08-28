import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    // Constructor for a new empty task list
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    // Constructor for loading from existing tasks
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    // Add a task
    public void addTask(Task task) {
        tasks.add(task);
    }

    // Remove a task by index
    public Task deleteTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index");
        }
        return tasks.remove(index);
    }

    // Get a task by index
    public Task getTask(int index) {
        return tasks.get(index);
    }

    // Get number of tasks
    public int size() {
        return tasks.size();
    }

    // Return the whole list
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    // Print all tasks in the list
    public String listTasks() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }
}

