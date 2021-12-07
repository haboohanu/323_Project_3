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
        enrollments = new ArrayList<Section>();
    }

    @Override
    public String toString() {
        return this.name + ", id: " + this.studentID;
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

    public void addTranscript(Transcript transcript) {
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

    public enum RegistrationResult {
        SUCCESS,
        ALREADY_PASSED,
        ENROLLED_IN_SECTION,
        NO_PREREQUISITES,
        ENROLLED_IN_ANOTHER,
        TIME_CONFLICT
    };

    public RegistrationResult registerForSection(Section s) {

        RegistrationResult toReturn = RegistrationResult.SUCCESS;

        // 1) Already Passed with C?
        for (Transcript t : transcripts) {
            boolean hasTakenThisClass = t.getSection().getCourse().getCourseId() == s.getCourse().getCourseId();
            if (hasTakenThisClass) {
                char grade = t.getGradeEarned().charAt(0);
                if (grade == 'C' || grade == 'B' || grade == 'A')
                    return RegistrationResult.ALREADY_PASSED;// student already passed class
            }
        }

        // 2 and 4) Already enrolled in this/another section of the course?
        for (Section enrolled : enrollments) {
            if (enrolled.getCourse().getCourseId() == s.getCourse().getCourseId()) {
                if (enrolled.getSectionId() == s.getSectionId())
                    return RegistrationResult.ENROLLED_IN_SECTION; // you are already enrolled in this Course
                else
                    return RegistrationResult.ENROLLED_IN_ANOTHER;
            }

        }

        /*
         * /
         * if(toReturn == RegistrationResult.SUCCESS)
         * {
         * enrollments.add(s);
         * s.enrollStudent(this);
         * }
         * */
          return toReturn;
        
    }

}
