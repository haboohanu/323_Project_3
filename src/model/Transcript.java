package model;

import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity(name = "TRANSCRIPTS")
public class Transcript implements Comparable<Transcript>{

    @Column(length = 2)
    @NotNull
    private String gradeEarned;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSCRIPT_ID")
    private int transcriptId;

    @JoinColumn(name = "SECTION_ID")
    @ManyToOne
    private Section section;

    @JoinColumn(name = "STUDENT_ID")
    @ManyToOne
    private Student student;


    public Transcript() {
    }

    public Transcript(String gradeEarned, Section section, Student student) {
        this.gradeEarned = gradeEarned;
        this.section = section;
        this.student = student;

        student.addTranscript(this);
    }

    @Override
    public String toString(){
        return this.section.getCourse().getDepartment().getAbbreviation() + " " + this.section.getCourse().getNumber() 
        + ", " + this.section.getSemester().getTitle() + ". Grade earned: " + this.gradeEarned;
    }

    public int getTranscriptId() {
        return this.transcriptId;
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


    public int compareTo(Transcript t) {
        if(this.section.getSemester().getStartDate().isAfter(t.section.getSemester().getStartDate())){
            return 1;
        }
        else if(this.section.getSemester().getStartDate().isBefore(t.section.getSemester().getStartDate())){
            return -1;
        }
        else{
            return 0;
        }
    }
    
    
}
