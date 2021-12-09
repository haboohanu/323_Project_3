package model;

import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity(name = "SECTIONS")
@Table(
    uniqueConstraints = 
        @UniqueConstraint(columnNames = {"SEMESTER_ID","COURSE_ID","SECTION_NUMBER"})
)
public class Section {

    private short maxCapacity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SECTION_ID")
    private int sectionId;

    @Column(name = "SECTION_NUMBER")
    private byte sectionNumber;
    
    @JoinColumn(name = "SEMESTER_ID")
    @ManyToOne
    @NotNull
    private Semester semester;

    @ManyToMany(mappedBy = "enrollments")
    private List<Student> enrolled_students;

    @JoinColumn(name = "TIMESLOT_ID")
    @ManyToOne
    @NotNull
    private TimeSlot timeSlot;

    @JoinColumn(name = "COURSE_ID")
    @ManyToOne
    @NotNull
    private Course course;


    public Section() {
        enrolled_students = new ArrayList<Student>();
    }

    public Section(Course course, Byte sectionNumber, Semester semester, TimeSlot timeSlot, short maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.sectionNumber = sectionNumber;
        semester.addSection(this);//handles creating bidirectional association
        this.timeSlot = timeSlot;
        this.course = course;
        enrolled_students = new ArrayList<Student>();

    }

    public String toString(){
        String toReturn = "SectionId: " + sectionId+ ", "+course + " section " + sectionNumber+", " + semester.getTitle() + ", ";
        
        toReturn += timeSlot + ", capacity " + maxCapacity;
        return  toReturn;
    }


    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public void setMaxCapacity(short maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getSectionNumber() {
        return this.sectionNumber;
    }

    public void setSectionNumber(Byte sectionNumber) {
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

    public int getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }    
    public void enrollStudent(Student s)
    {
        enrolled_students.add(s);
    }

    public List<Student> getEnrolled_students() {
        return this.enrolled_students;
    }

    public void setEnrolled_students(List<Student> enrolled_students) {
        this.enrolled_students = enrolled_students;
    }









    
}
