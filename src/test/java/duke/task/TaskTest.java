package duke.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class TodoTaskTest {

    @Test
    void constructor_initializesCorrectly() {
        TodoTask task = new TodoTask("Read a book");

        assertEquals("Read a book", task.getDescription());
        assertFalse(task.isDone());
        assertEquals("[T][ ] Read a book", task.toString());
    }

    @Test
    void markAndUnmark_updatesStatusCorrectly() {
        TodoTask task = new TodoTask("Write notes");

        task.mark();
        assertTrue(task.isDone());
        assertEquals("[T][X] Write notes", task.toString());

        task.unmark();
        assertFalse(task.isDone());
        assertEquals("[T][ ] Write notes", task.toString());
    }

    @Test
    void toStorageString_formatsCorrectly() {
        TodoTask task = new TodoTask("Do laundry");
        assertEquals("T | 0 | Do laundry", task.toStorageString());

        task.mark();
        assertEquals("T | 1 | Do laundry", task.toStorageString());
    }
}

class DeadlineTaskTest {

    @Test
    void constructor_initializesCorrectly() {
        DeadlineTask task = null;
        try {
            task = new DeadlineTask("Submit assignment", "2025-09-15 2359");
        } catch (Exception e) {
            fail();
        }

        assertEquals("Submit assignment", task.getDescription());
        assertFalse(task.isDone());
        assertEquals(LocalDateTime.of(2025, 9, 15, 23, 59), task.getBy());
    }

    @Test
    void toString_reflectsDeadlineCorrectly() {
        DeadlineTask task = null;
        try {
            task = new DeadlineTask("Submit assignment", "2025-09-15 2359");
        } catch (Exception e) {
            fail();
        }

        String expected = "[D][ ] Submit assignment (by: Sep 15 2025, 11:59pm)";
        assertEquals(expected, task.toString());
    }

    @Test
    void toStorageString_formatsCorrectly() {
        DeadlineTask task = null;
        try {
            task = new DeadlineTask("Submit assignment", "2025-09-15 2359");
        } catch (Exception e) {
            fail();
        }
        assertEquals("D | 0 | Submit assignment | 2025-09-15T23:59", task.toStorageString());

        task.mark();
        assertEquals("D | 1 | Submit assignment | 2025-09-15T23:59", task.toStorageString());
    }
}

class EventTaskTest {

    @Test
    void constructor_initializesCorrectly() {
        EventTask task = null;
        try {
            task = new EventTask("Team meeting", "2025-09-20 1000", "2025-09-20 1200");
        } catch (Exception e) {
            fail();
        }

        assertEquals("Team meeting", task.getDescription());
        assertFalse(task.isDone());
        assertEquals(LocalDateTime.of(2025, 9, 20, 10, 0), task.getStart());
        assertEquals(LocalDateTime.of(2025, 9, 20, 12, 0), task.getEnd());
    }

    @Test
    void toString_reflectsEventCorrectly() {
        EventTask task = null;
        try {
            task = new EventTask("Team meeting", "2025-09-20 1000", "2025-09-20 1200");
        } catch (Exception e) {
            fail();
        }

        String expected = "[E][ ] Team meeting (from: Sep 20 2025, 10:00am to: Sep 20 2025, 12:00pm)";
        assertEquals(expected, task.toString());
    }

    @Test
    void toStorageString_formatsCorrectly() {
        EventTask task = null;
        try {
            task = new EventTask("Team meeting", "2025-09-20 1000", "2025-09-20 1200");
        } catch (Exception e) {
            fail();
        }

        assertEquals(
                "E | 0 | Team meeting | 2025-09-20T10:00 | 2025-09-20T12:00",
                task.toStorageString()
        );

        task.mark();
        assertEquals(
                "E | 1 | Team meeting | 2025-09-20T10:00 | 2025-09-20T12:00",
                task.toStorageString()
        );
    }
}


