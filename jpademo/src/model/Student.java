package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "STUDENTS")
@Table(
    uniqueConstraints = 
        @UniqueConstraint(columnNames = {"STUDENT_ID"})
)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_SURROGATE")
    private int studentSurrogate;

    @Column(length = 128)
    private String name;

    @Column(name = "STUDENT_ID")
    private int studentID;

    @OneToMany(mappedBy = "student")
    private List<Transcript> transcripts;


    public Student() {
    }

    public Student(int studentSurrogate, String name, int studentID) {
        this.studentSurrogate = studentSurrogate;
        this.name = name;
        this.studentID = studentID;
    }

    public int getStudentSurrogate() {
        return this.studentSurrogate;
    }

    public void setStudentSurrogate(int studentSurrogate) {
        this.studentSurrogate = studentSurrogate;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentID() {
        return this.studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public List<Transcript> getTranscripts() {
        return this.transcripts;
    }

    public void setTranscripts(List<Transcript> transcripts) {
        this.transcripts = transcripts;
    }

    public double getGpa(){
        return 0;
    }

    




    
    
}
