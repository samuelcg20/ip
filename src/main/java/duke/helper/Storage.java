package duke.helper;

import duke.list.TaskList;

import duke.task.Task;
import duke.task.DeadlineTask;
import duke.task.EventTask;
import duke.task.TodoTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the saving and loading of tasks from the local storage file.
 * <p>
 * Each line in the file represents one task in a specific format:
 * <ul>
 *     <li>Todo: {@code T | isDone | description}</li>
 *     <li>Deadline: {@code D | isDone | description | by}</li>
 *     <li>Event: {@code E | isDone | description | start | end}</li>
 * </ul>
 */
public class Storage {
    private static final String FILE_PATH = "./data/sai.txt";

    /**
     * Loads tasks from the storage file.
     * <p>
     * If the file or its parent directories do not exist, they are created.
     * If the file is empty, an empty {@link TaskList} is returned.
     * Lines that cannot be parsed are skipped with a warning message.
     *
     * @return A {@link TaskList} containing all tasks read from storage.
     */
    public TaskList load() {
        ArrayList<Task> taskList = new ArrayList<>();
        File file = new File(FILE_PATH);

        try {
            // Create directory and file if they do not exist yet
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
                return new TaskList(); // empty list if first run
            }

            List<String> lines = Files.readAllLines(Path.of(FILE_PATH));
            for (String line : lines) {
                try {
                    taskList.add(readLine(line));
                } catch (Exception e) {
                    System.out.println("Warning: This line cannot be read: " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return new TaskList(taskList);
    }

    /**
     * Saves the given {@link TaskList} to the storage file.
     * <p>
     * Each task is formatted according to its type and written on a new line.
     *
     * @param taskList The {@link TaskList} to save.
     */
    public void save(TaskList taskList) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH);
            ArrayList<Task> innerList = taskList.getTasks();

            for (Task task : innerList) {
                fw.write(formatTask(task) + System.lineSeparator());
            }

            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Parses a single line from the storage file into a {@link Task}.
     *
     * @param line A formatted string representing a task.
     * @return A {@link Task} object corresponding to the line.
     * @throws IllegalArgumentException If the task type is invalid.
     */
    private Task readLine(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = switch (type) {
            case "T" -> new TodoTask(description);
            case "D" -> {
                String by = parts[3];
                yield new DeadlineTask(description, by);
            }
            case "E" -> {
                String start = parts[3];
                String end = parts[4];
                yield new EventTask(description, start, end);
            }
            default -> throw new IllegalArgumentException("Invalid task type: " + type);
        };

        if (isDone) {
            task.mark();
        }
        return task;
    }

    /**
     * Converts a {@link Task} object into a string representation
     * suitable for saving into the storage file.
     *
     * @param task The {@link Task} to format.
     * @return A formatted string representation of the task.
     * @throws IllegalArgumentException If the task type is unknown.
     */
    private String formatTask(Task task) {
        String status = task.isDone() ? "1" : "0";
        if (task instanceof TodoTask) {
            return "T | " + status + " | " + task.getDescription();
        } else if (task instanceof DeadlineTask) {
            return "D | " + status + " | " + task.getDescription()
                    + " | " + ((DeadlineTask) task).getBy();
        } else if (task instanceof EventTask) {
            return "E | " + status + " | " + task.getDescription()
                    + " | " + ((EventTask) task).getStart()
                    + " | " + ((EventTask) task).getEnd();
        }
        throw new IllegalArgumentException("Unknown task type");
    }
}

