package model;

import java.time.LocalTime;
import java.util.*;

import jakarta.persistence.*;

@Entity(name = "TIMESLOTS")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "DAYSOFWEEK", "STARTTIME", "ENDTIME" }))
public class TimeSlot {

    public static final byte SUNDAY = 1 << 6,
            MONDAY = 1 << 5,
            TUESDAY = 1 << 4,
            WEDNESDAY = 1 << 3,
            THURSDAY = 1 << 2,
            FRIDAY = 1 << 1,
            SATURDAY = 1 << 0;

    private byte daysOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIMESLOT_ID")
    private int timeSlotId;

    public TimeSlot() {
    }

    public TimeSlot(byte daysOfWeek, LocalTime startTime, LocalTime endTime) {
        this.daysOfWeek = daysOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public byte getDaysOfWeek() {
        return this.daysOfWeek;
    }

    public void setDaysOfWeek(byte daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getTimeSlotId() {
        return this.timeSlotId;
    }

    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public String daysOfWeekToString() {
        String toReturn = "";
        if ((daysOfWeek & MONDAY) != 0)
            toReturn += "M";
        if ((daysOfWeek & TUESDAY) != 0)
            toReturn += "Tu";
        if ((daysOfWeek & WEDNESDAY) != 0)
            toReturn += "W";
        if ((daysOfWeek & THURSDAY) != 0)
            toReturn += "Th";
        if ((daysOfWeek & FRIDAY) != 0)
            toReturn += "F";
        if ((daysOfWeek & SATURDAY) != 0)
            toReturn += "Sa";
        if ((daysOfWeek & SUNDAY) != 0)
            toReturn += "Su";

        return toReturn;
    }

    public String toString() {
        return daysOfWeekToString() + " " + startTime + " " + endTime;
    }

}
