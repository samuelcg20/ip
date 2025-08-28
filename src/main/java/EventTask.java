import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventTask extends Task{
    private LocalDateTime start;
    private LocalDateTime end;

    public EventTask(String description, String start, String end) {
        super(description);
        this.start = Parser.parseDateTime(start);
        this.end = Parser.parseDateTime(end);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + start.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma"))
                + " to: " + end.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + ")";
    }
}
