package duke.helper;

import duke.exceptions.InvalidTaskFormatException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.Month;

public class ParserTest {

    @Test
    public void testParseDateTime_variousFormats() {
        // yyyy-MM-dd HHmm
        LocalDateTime dt1 = Parser.parseDateTime("2019-12-02 1800");
        assertEquals(2019, dt1.getYear());
        assertEquals(12, dt1.getMonthValue());
        assertEquals(2, dt1.getDayOfMonth());
        assertEquals(18, dt1.getHour());
        assertEquals(0, dt1.getMinute());

        // yyyy-MM-dd (time defaults to 23:59)
        LocalDateTime dt2 = Parser.parseDateTime("2019-12-02");
        assertEquals(2019, dt2.getYear());
        assertEquals(12, dt2.getMonthValue());
        assertEquals(2, dt2.getDayOfMonth());
        assertEquals(23, dt2.getHour());
        assertEquals(59, dt2.getMinute());

        // d/M/yyyy HHmm
        LocalDateTime dt3 = Parser.parseDateTime("2/12/2019 1800");
        assertEquals(2019, dt3.getYear());
        assertEquals(Month.DECEMBER, dt3.getMonth());
        assertEquals(2, dt3.getDayOfMonth());
        assertEquals(18, dt3.getHour());
        assertEquals(0, dt3.getMinute());

        // d/M/yyyy (time defaults to 23:59)
        LocalDateTime dt4 = Parser.parseDateTime("2/12/2019");
        assertEquals(2019, dt4.getYear());
        assertEquals(Month.DECEMBER, dt4.getMonth());
        assertEquals(2, dt4.getDayOfMonth());
        assertEquals(23, dt4.getHour());
        assertEquals(59, dt4.getMinute());
    }

    @Test
    public void testParseDateTime_invalidFormat() {
        try {
            Parser.parseDateTime("invalid date string");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Unrecognised date format: invalid date string", e.getMessage());
        }
    }

    @Test
    public void testExtractPhrases_todo() throws InvalidTaskFormatException {
        String[] result = Parser.extractPhrases("todo read book");
        assertEquals("todo", result[0]);
        assertEquals("read book", result[1]);
    }

    @Test
    public void testExtractPhrases_deadline() throws InvalidTaskFormatException {
        String[] result = Parser.extractPhrases("deadline return book /by 2025-12-02");
        assertEquals("deadline", result[0]);
        assertEquals("return book", result[1]);
        assertEquals("2025-12-02", result[2]);
    }

    @Test
    public void testExtractPhrases_event() throws InvalidTaskFormatException {
        String[] result = Parser.extractPhrases("event project meeting /from 10:00 /to 12:00");
        assertEquals("event", result[0]);
        assertEquals("project meeting", result[1]);
        assertEquals("10:00", result[2]);
        assertEquals("12:00", result[3]);
    }

    @Test
    public void testExtractPhrases_invalidTask() throws InvalidTaskFormatException {
        try {
            Parser.extractPhrases("unknown something");
            fail();
        } catch (InvalidTaskFormatException e) {
            assertEquals("Inputted task does not fall under todo, deadline or event", e.getMessage());
        }
    }

    @Test
    public void testExtractPhrases_emptyInput() throws InvalidTaskFormatException {
        try {
            Parser.extractPhrases("event");
            fail();
        } catch (InvalidTaskFormatException e) {
            assertEquals("Event Task cannot be empty", e.getMessage());
        }
    }
}

