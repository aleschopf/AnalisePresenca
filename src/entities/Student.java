package entities;

public class Student {
    private final String day;
    private final String name;
    private final long minutes;

    public Student(String day, String name, long minutes) {
        this.day = day;
        this.name = name;
        this.minutes = minutes;
    }

    public String getDay() {
        return day;
    }

    public String getName() {
        return name;
    }

    public long getMinutes() {
        return minutes;
    }
}
