package model;

import java.util.*;

import jakarta.persistence.*;


@Entity(name = "COURSES")
@Table(
    uniqueConstraints = 
        @UniqueConstraint(columnNames = {"NUMBER"})
)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_ID")
    private int courseId;

    @Column(length = 8)
    private String number;

    @Column(length = 64)
    private String title;
    
    private int units;

    @OneToMany(mappedBy = "course")
    private List<Prerequisite> prerequisites;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;




    public Course() {
    }


    public Course(Department department, String number, String title, int units, List<Prerequisite> prerequisites) {
        this.department = department;
        this.number = number;
        this.title = title;
        this.units = units;
        this.prerequisites = prerequisites;
    }


    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUnits() {
        return this.units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public List<Prerequisite> getPrerequisites() {
        return this.prerequisites;
    }

    public void setPrerequisites(List<Prerequisite> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getCourseSurrogate() {
        return this.courseId;
    }

    public void setCourseSurrogate(int courseSurrogate) {
        this.courseId = courseSurrogate;
    }
 

    
}
