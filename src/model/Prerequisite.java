package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "PREREQUISITES")
public class Prerequisite {
    
    private char minimumGrade;

    @Id
    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    @Id
    @ManyToOne
    private Course course_prereq;


    public Prerequisite() {
    }

    public Prerequisite(char minimumGrade, Course course, Course coursePrereq) {
        this.minimumGrade = minimumGrade;
        this.course = course;
        //this.coursePrereq = coursePrereq;
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

    // public Course getCoursePrereq() {
    //     return this.coursePrereq;
    // }

    // public void setCoursePrereq(Course coursePrereq) {
    //     this.coursePrereq = coursePrereq;
    // }

    
}
