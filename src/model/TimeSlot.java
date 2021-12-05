package model;

import java.time.LocalTime;
import java.util.*;

import jakarta.persistence.*;

@Entity(name = "TIMESLOTS")
@Table(
    uniqueConstraints = 
        @UniqueConstraint(columnNames = {"DAYSOFWEEK", "STARTTIME", "ENDTIME"})
)
public class TimeSlot {

    
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
    



    
}
