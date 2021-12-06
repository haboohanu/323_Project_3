package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "STUDENTS")
public class Student {

    @Column(length = 128)
    private String name;

    @Id
    @Column(name = "STUDENT_ID")
    private int studentID;

    @OneToMany(mappedBy = "student")
    private List<Transcript> transcripts;

    @JoinTable(name = "ENROLLMENTS", joinColumns = @JoinColumn(name = "STUDENT_ID"), inverseJoinColumns = @JoinColumn(name = "SECTION_ID"))
    @ManyToMany
    private List<Section> enrollments;

    public Student() {
    }

    public Student(String name, int studentID) {
        this.name = name;
        this.studentID = studentID;
        transcripts = new ArrayList<Transcript>();
    }

    @Override
    public String toString(){
        return this.name + " " + this.studentID;
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

    //testing
    public void addTranscript(Transcript transcript)
    {
        transcripts.add(transcript);
    }

    public List<Section> getEnrollments() {
        return this.enrollments;
    }

    public void setEnrollments(List<Section> enrollments) {
        this.enrollments = enrollments;
    }

    public double getGpa() {

        int totalUnits = 0;
        int gradePoints = 0;

        for (Transcript t : transcripts) {
            int units = t.getSection().getCourse().getUnits();
            totalUnits += units;

            char grade = t.getGradeEarned().charAt(0);

            if (grade == 'A')
                gradePoints += units * 4;
            else if (grade == 'B')
                gradePoints += units * 3;
            else if (grade == 'C')
                gradePoints += units * 2;
            else if (grade == 'D')
                gradePoints += units * 1;

        }
        double gpa = (double) gradePoints / totalUnits;
        return gpa;
    }

}
