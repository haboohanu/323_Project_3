package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "PREREQUISITES")
public class Prerequisite {
    
    private char minimumGrade;


    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PREREQUISITE_ID")
    private int transcriptId;

    //@Id
    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    //@Id
    @ManyToOne
    //need @JoinColumn field here? testing
    private Course course_prereq;


    public Prerequisite() {
    }

    public Prerequisite(char minimumGrade, Course course, Course course_prereq) {
        this.minimumGrade = minimumGrade;
        this.course = course;
        this.course_prereq = course_prereq;
    }

    public char getMinimumGrade() {
        return this.minimumGrade;
    }

    public void setMinimumGrade(char minimumGrade) {
        this.minimumGrade = minimumGrade;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCoursePrereq() {
        return this.course_prereq;
    }

    public void setCoursePrereq(Course course_prereq) {
        this.course_prereq = course_prereq;
    }

    
}
