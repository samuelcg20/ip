package duke.list;

import java.util.ArrayList;

import duke.task.Task;

/**
 * Represents a list of tasks and provides operations
 * to manage tasks such as adding, deleting, and retrieving.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates a new empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList initialised with an existing list of tasks.
     *
     * @param tasks pre-existing tasks to load
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to be added
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes the task at the given index.
     *
     * @param index index of the task to delete (0-based)
     * @return the removed Task
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public Task deleteTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index");
        }
        return tasks.remove(index);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index index of the task (0-based)
     * @return Task at the specified index
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying ArrayList of tasks.
     *
     * @return list of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Searches the current task list for tasks that contain the specified keyword
     * in their description.
     * <p>
     * The search returns all tasks whose descriptions
     * include the keyword as a substring.
     *
     * @param keyword the string to search for in task descriptions
     * @return an {@link ArrayList} of {@link Task} objects whose descriptions
     *         contain the specified keyword; if no matches are found, the
     *         returned list will be empty
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> results = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                results.add(task);
            }
        }
        return results;
    }

    /**
     * Returns a formatted string listing all tasks in the list.
     *
     * @return string representation of all tasks
     */
    public String listTasks() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Checks whether the task list contains a task that is equal to the specified task.
     * <p>
     * Equality is determined by the {@link Task#equals(Object)} method of the task objects.
     *
     * @param task the task to check for in the task list
     * @return {@code true} if a matching task is found in the list, {@code false} otherwise
     */
    public boolean contains(Task task) {
        for (Task t : this.tasks) {
            if (t.equals(task)) {
                return true;
            }
        }
        return false;
    }
}

