package baymax.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    // Dates for the duration
    private LocalDate date;
    private LocalTime from;
    private LocalTime to;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a");

    public Event(String description, String date, String from, String to) {
        super(description, TaskType.EVENT);
        this.date = LocalDate.parse(date);
        this.from = LocalTime.parse(from);
        this.to = LocalTime.parse(to);
    }

    public Event(String description, String date, String from, String to, boolean isDone) {
        super(description, TaskType.EVENT);
        this.date = LocalDate.parse(date);
        this.from = LocalTime.parse(from);
        this.to = LocalTime.parse(to);
        this.isDone = isDone;
    }

    public static Event fromFileFormat(String description, String day, String from, String to, boolean isDone) {
        return new Event(description, day, from, to, isDone);
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + date + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (on: " + date.format(DATE_FORMAT)
                + " from: " + from.format(TIME_FORMAT) + " to: " + to.format(TIME_FORMAT) + ")";
    }
}
