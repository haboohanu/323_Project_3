package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "SECTIONS")
public class Section {

    private int maxCapacity;

    @Id
    @Column(name = "SECTION_NUMBER")
    private int sectionNumber;
    
    @JoinColumn(name = "SEMESTER_ID")
    @ManyToOne
    private Semester semester;

    //@JoinColumn(name = "STUDENT_SURROGATE")
    @ManyToMany
    private List<Student> students;

    @JoinColumn(name = "TIMESLOT_ID")
    @ManyToOne
    private TimeSlot timeSlot;

    @JoinColumn(name = "COURSE_ID")
    @ManyToOne
    private Course course;


    public Section() {
    }

    public Section(int maxCapacity, int sectionNumber, Semester semester, List<Student> students, TimeSlot timeSlot, Course course) {
        this.maxCapacity = maxCapacity;
        this.sectionNumber = sectionNumber;
        this.semester = semester;
        this.students = students;
        this.timeSlot = timeSlot;
        this.course = course;
    }


    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getSectionNumber() {
        return this.sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public Semester getSemester() {
        return this.semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public TimeSlot getTimeSlot() {
        return this.timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }







    
}
