import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.*;

import javax.sound.midi.SysexMessage;

import jakarta.persistence.*;

import model.*;
import model.Student.RegistrationResult;

public class App {


    public static void instantiate() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();

        Semester s1 = new Semester("Spring 2021", LocalDate.of(2021, 1, 19));
        Semester s2 = new Semester("Fall 2021", LocalDate.of(2021, 8, 17));
        Semester s3 = new Semester("Spring 2022", LocalDate.of(2022, 1, 20));

        Department d1 = new Department("Computer Engineering and Computer Science", "CECS");
        Department d2 = new Department("Italian Studies", "ITAL");

        TimeSlot t1 = new TimeSlot((byte) (40), LocalTime.of(12, 30), LocalTime.of(13, 45));
        TimeSlot t2 = new TimeSlot((byte) (20), LocalTime.of(14, 0), LocalTime.of(15, 15));
        TimeSlot t3 = new TimeSlot((byte) (42), LocalTime.of(12, 0), LocalTime.of(12, 50));
        TimeSlot t4 = new TimeSlot((byte) (2), LocalTime.of(8, 0), LocalTime.of(10, 45));

        Course c1 = new Course(d1, "174", "Introduction to Programming and Problem Solving", (byte)3);
        Course c2 = new Course(d1, "274", "Data Structures", (byte)3);
        Course c3 = new Course(d1, "277", "Object Oriented Application Programming", (byte)3);
        Course c4 = new Course(d1, "282", "Advanced C++", (byte)3);
        Course c5 = new Course(d2, "101A", "Fundamentals of Italian",(byte) 4);
        Course c6 = new Course(d2, "101B", "Fundamentals of Italian", (byte)4);

        Prerequisite p1 = new Prerequisite('C', c2, c1);
        Prerequisite p2 = new Prerequisite('C', c3, c2);
        Prerequisite p3 = new Prerequisite('C', c4, c2);
        Prerequisite p4 = new Prerequisite('C', c4, c3);
        Prerequisite p5 = new Prerequisite('D', c6, c5);

        Section a = new Section(c1, (byte)1, s1, t1, (short)105);// 174
        Section b = new Section(c2, (byte)1, s2, t2, (short)140);// 274
        Section c = new Section(c3, (byte)3, s2, t4, (short)35);// 277
        Section d = new Section(c4, (byte)5, s3, t2, (short)35);// 282
        Section e = new Section(c3, (byte)1, s3, t1, (short)35);// 277
        Section f = new Section(c4, (byte)7, s3, t1, (short)35);// 282
        Section g = new Section(c5, (byte)1, s3, t3, (short)25);// 101A
        

        Student st1 = new Student("Naomi Nagata", 123456789);
        st1.getEnrollments().add(d);
        Student st2 = new Student("James Holden", 987654321);
        Student st3 = new Student("Amos Burton", 555555555);

        // Naomi
        Transcript tr1 = new Transcript("A", a, st1);
        Transcript tr2 = new Transcript("A", b, st1);
        Transcript tr3 = new Transcript("A", c, st1);
        // James
        Transcript tr4 = new Transcript("C", a, st2);
        Transcript tr5 = new Transcript("C", b, st2);
        Transcript tr6 = new Transcript("C", c, st2);
        // Amos
        Transcript tr7 = new Transcript("C", a, st3);
        Transcript tr8 = new Transcript("B", b, st3);
        Transcript tr9 = new Transcript("D", c, st3);
        

        em.getTransaction().begin();
        em.persist(s1);
        em.persist(s2);
        em.persist(s3);
        em.persist(d1);
        em.persist(d2);
        em.persist(t1);
        em.persist(t2);
        em.persist(t3);
        em.persist(t4);
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.persist(c4);
        em.persist(c5);
        em.persist(c6);
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(p4);
        em.persist(p5);
        em.persist(a);
        em.persist(b);
        em.persist(c);
        em.persist(d);
        em.persist(e);
        em.persist(f);
        em.persist(g);
        em.persist(st1);
        em.persist(st2);
        em.persist(st3);
        em.persist(tr1);
        em.persist(tr2);
        em.persist(tr3);
        em.persist(tr4);
        em.persist(tr5);
        em.persist(tr6);
        em.persist(tr7);
        em.persist(tr8);
        em.persist(tr9);

        em.getTransaction().commit();

    }

    public static void lookup() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();
        System.out.println("Enter the name of student");
        Scanner input = new Scanner(System.in);
        String name = input.nextLine();
        var namedStudent = em.createQuery("SELECT s FROM STUDENTS s WHERE "
                + "s.name = ?1", Student.class);
        namedStudent.setParameter(1, name);
        try {
            Student requested = namedStudent.getSingleResult();
            System.out.println("Your requested student: " + requested);
            var studentTranscripts = requested.getTranscripts();
            Collections.sort(studentTranscripts);
            for (Transcript t : studentTranscripts) {
                System.out.println(t);
            }
            System.out.println("Your student gpa: " + requested.getGpa());

        } catch (NoResultException ex) {
            System.out.println("Student with name '" + name + "' not found.");
        }

    }

    public static void register() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();
        var semesters = em.createQuery("SELECT s FROM SEMESTERS s", Semester.class).getResultList();
        for (Semester s : semesters) {
            System.out.println(s);
        }
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("Enter semester (ex: Spring 2022)");
            String semester = input.nextLine();
            var namedSemester = em.createQuery("SELECT s FROM SEMESTERS s WHERE "
                    + "s.title = ?1", Semester.class);
            namedSemester.setParameter(1, semester);
            var registerSemesterId = namedSemester.getSingleResult().getSemesterId();
            System.out.println("Enter student name");
            String name = input.nextLine();
            var namedStudent = em.createQuery("SELECT s FROM STUDENTS s WHERE "
                    + "s.name = ?1", Student.class);
            namedStudent.setParameter(1, name);
            Student student = namedStudent.getSingleResult();
            System.out.println("Enter course section");
            String courseSection = input.nextLine();
            String[] tokens = courseSection.split("-| ");
            String department = tokens[0];
            String course = tokens[1];
            String section = tokens[2].replaceFirst("^0+(?!$)", "");

            var registerDepartment = em.createQuery("SELECT d FROM DEPARTMENTS d WHERE "
                    + "d.abbreviation = ?1 ", Department.class);
            registerDepartment.setParameter(1, department);
            Department registerDepartmentResult = registerDepartment.getSingleResult();
            var registerDepartmentId = registerDepartmentResult.getDepartmentId();
            var registerCourse = em.createQuery("SELECT c FROM COURSES c WHERE "
                    + "c.department.departmentId = " + registerDepartmentId + " AND c.number = ?1", Course.class);
            registerCourse.setParameter(1, course);
            var registerCourseResult = registerCourse.getSingleResult();
            var registerCourseId = registerCourseResult.getCourseId();
            var registerSection = em.createQuery("SELECT s FROM SECTIONS s WHERE "
                    + "s.course.courseId = " + registerCourseId + " AND s.sectionNumber = ?1 AND s.semester.semesterId = " + registerSemesterId, Section.class);
            registerSection.setParameter(1, Integer.parseInt(section));
            var registerSectionResult = registerSection.getSingleResult();

            var result = student.registerForSection(registerSectionResult);
            System.out.println(result);
            if (result == RegistrationResult.SUCCESS){
                em.getTransaction().begin();
                em.persist(student);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            System.out.println("Invalid Input.");
        }


    }

    public static void menu() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Input a number option");
            System.out.println("Menu:");
            System.out.println("(1) Instantiate model");
            System.out.println("(2) Student lookup");
            System.out.println("(3) Register for a course");
            System.out.println("(0) Exit");
            int i = input.nextInt();
            if (i == 1) {
                System.out.println("Initializing...");
                instantiate();
            } else if (i == 2) {
                System.out.println("Looking up student...");
                lookup();
            } else if (i == 3) {
                System.out.println("Registering course...");
                register();
            } else if (i == 0) {
                System.out.println("Bye.");
                break;
            } else {
                System.out.println("Invalid input.\nTry again.");
            }
        }
    }

    public static void main(String[] args) throws Exception {

        menu();
    }
}
