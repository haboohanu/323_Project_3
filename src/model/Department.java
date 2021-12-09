package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "DEPARTMENTS")
public class Department {
    
    @Column(length = 128)
    private String name;

    @Column(length = 8)
    private String abbreviation;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_ID")
    private int departmentId;

    @OneToMany(mappedBy = "department")
    private List<Course> courses;


    public Department() {
    }

    public Department(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public int getDepartmentId() {
        return this.departmentId;
    }

    public List<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }



    
}
