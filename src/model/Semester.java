package model;

import java.time.LocalDate;
import java.util.*;

import jakarta.persistence.*;

@Entity(name = "SEMESTERS")
public class Semester {
    
    
    @Column(length = 16)
    private String title;
    
    private LocalDate startDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEMESTER_ID")
    private int semesterId;

    @OneToMany(mappedBy = "semester")
    private List<Section> sections;


    public Semester() {
    }

    public Semester(String title, LocalDate startDate) {
        this.title = title;
        this.startDate = startDate;
    }

    @Override
    public String toString(){
        return "Semester: " + title + ", StartDate: " + startDate;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getSemesterId() {
        return this.semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }


    public List<Section> getSections() {
        return this.sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section s){
        this.sections.add(s);
        s.setSemester(this);
    }

    
}
