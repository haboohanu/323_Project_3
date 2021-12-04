package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "SECTIONS")
@Table(
    uniqueConstraints = 
        @UniqueConstraint(columnNames = {"SECTION_NUMBER"})
)
public class Section {

    private int maxCapacity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SECTION_SURROGATE")
    private int sectionSurrogate;

    @Column(name = "SECTION_NUMBER")
    private int sectionNumber;
    
    @JoinColumn(name = "SEMESTER_SURROGATE")
    @ManyToOne
    private Semester semester;

    //@JoinColumn(name = "STUDENT_SURROGATE")
    @ManyToMany
    private List<Student> students;

    @JoinColumn(name = "TIMESLOT_SURROGATE")
    @ManyToOne
    private TimeSlot timeSlot;

    @JoinColumn(name = "COURSE_SURROGATE")
    @ManyToOne
    private Course course;


    public Section() {
    }

    public Section(int maxCapacity, int sectionSurrogate, int sectionNumber) {
        this.maxCapacity = maxCapacity;
        this.sectionSurrogate = sectionSurrogate;
        this.sectionNumber = sectionNumber;
    }


    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getSectionSurrogate() {
        return this.sectionSurrogate;
    }

    public void setSectionSurrogate(int sectionSurrogate) {
        this.sectionSurrogate = sectionSurrogate;
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
