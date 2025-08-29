package duke.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TaskTest {

    @Test
    void constructor_initializesCorrectly() {
        Task task = new Task("Read a book");

        assertEquals("Read a book", task.getDescription());
        assertFalse(task.isDone(), "New tasks should not be marked as done by default");
        assertEquals("[ ] Read a book", task.toString());
    }

    @Test
    void mark_setsTaskAsDone() {
        Task task = new Task("Finish homework");

        task.mark();

        assertTrue(task.isDone());
        assertEquals("[X] Finish homework", task.toString());
    }

    @Test
    void unmark_setsTaskAsNotDone() {
        Task task = new Task("Go jogging");

        task.mark();
        task.unmark();

        assertFalse(task.isDone());
        assertEquals("[ ] Go jogging", task.toString());
    }

    @Test
    void toString_reflectsStatusCorrectly() {
        Task task = new Task("Cook dinner");

        // Initially not done
        assertEquals("[ ] Cook dinner", task.toString());

        // After marking
        task.mark();
        assertEquals("[X] Cook dinner", task.toString());

        // After unmarking again
        task.unmark();
        assertEquals("[ ] Cook dinner", task.toString());
    }
}

