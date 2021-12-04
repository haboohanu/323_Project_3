package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "TRANSCRIPTS")
public class Transcript {

    @Column(length = 2)
    private String gradeEarned;

    @Id
    @JoinColumn(name = "SECTION_NUMBER")
    @ManyToOne
    private Section section;

    @Id
    @JoinColumn(name = "STUDENT_ID")
    @ManyToOne
    private Student student;


    public Transcript() {
    }

    public Transcript(String gradeEarned, Section section, Student student) {
        this.gradeEarned = gradeEarned;
        this.section = section;
        this.student = student;
    }

    public String getGradeEarned() {
        return this.gradeEarned;
    }

    public void setGradeEarned(String gradeEarned) {
        this.gradeEarned = gradeEarned;
    }

    public Section getSection() {
        return this.section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    
    
}
