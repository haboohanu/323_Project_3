package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "SECTIONS")
@Table(
    uniqueConstraints = 
        @UniqueConstraint(columnNames = {"SEMESTER_ID","COURSE_ID","SECTION_NUMBER"})
)
public class Section {

    private int maxCapacity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SECTION_ID")
    private int sectionId;

    @Column(name = "SECTION_NUMBER")
    private int sectionNumber;
    
    @JoinColumn(name = "SEMESTER_ID")
    @ManyToOne
    private Semester semester;

    @ManyToMany(mappedBy = "enrollments")
    private List<Student> enrolled_students;

    @JoinColumn(name = "TIMESLOT_ID")
    @ManyToOne
    private TimeSlot timeSlot;

    @JoinColumn(name = "COURSE_ID")
    @ManyToOne
    private Course course;


    public Section() {
        enrolled_students = new ArrayList<Student>();
    }

    public Section(Course course, int sectionNumber, Semester semester, TimeSlot timeSlot, int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.sectionNumber = sectionNumber;
        //this.semester = semester;
        semester.addSection(this);//handles creating bidirectional association
        this.timeSlot = timeSlot;
        this.course = course;

        enrolled_students = new ArrayList<Student>();

    }

    public String toString(){
        String toReturn = course + " section " + sectionNumber+", " + semester.getTitle() + ", ";
        
        toReturn += timeSlot + ", capacity " + maxCapacity;
        return  toReturn;
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
