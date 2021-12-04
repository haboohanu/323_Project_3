package model;

import java.util.*;

import jakarta.persistence.*;

@Entity(name = "DEPARTMENTS")
// @Table(
//     uniqueConstraints = 
//         @UniqueConstraint(columnNames = {"DEPARTMENT_ID"})
// )
public class Department {
    private String name;
    private String abbreviation;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_SURROGATE")
    private int departmentSurrogate;

    // @Column(name = "DEPARTMENT_ID")
    // private int departmentId;

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

    // public int getDepartmentId() {
    //     return this.departmentId;
    // }

    // public void setDepartmentId(int departmentId) {
    //     this.departmentId = departmentId;
    // }

    public List<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getDepartmentSurrogate() {
        return this.departmentSurrogate;
    }

    public void setDepartmentSurrogate(int departmentSurrogate) {
        this.departmentSurrogate = departmentSurrogate;
    }


    
}