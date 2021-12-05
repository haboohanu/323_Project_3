import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

import jakarta.persistence.*;

import model.*;
public class App {
    // These demos show finding, creating, and updating individual objects.
    private static void basicDemos() {
        // In true Java fashion, we can't just create an EntityManager; we have to first
        // create a Factory that can then create the Manager. Don't ask me why.

        // The parameter "demoDb" matches the "name" of our data source, set in
        // src/META-INF/persistence.xml.
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();
        
        // The EntityManager object lets us find, create, update, and delete individual
        // instances of our entity classes.
        
        System.out.println("Example 1: find an entity based on its primary key.");
        Museum firstMuseum = em.find(Museum.class, 4); // parameter 2: the primary key value.
        if (firstMuseum != null) {
            System.out.println("Museum with ID 4: " + firstMuseum);
        }
        else {
            System.out.println("There is no museum with ID 4");
        }

        // The next "if" block will protect me if I run this code multiple times.
        // Otherwise we'll keep trying to create an object with a non-unique primary key,
        // and crash the program.
        if (firstMuseum == null) {
            System.out.println();
            System.out.println("Example 2: creating a new entity.");
            
            // We must begin and later end a transaction when modifying the database.
            em.getTransaction().begin();
            
            Museum newMuseum = new Museum(4, "Metropolitan Museum of Art of New York City", 
                "New York, NY");
            // The previous line just creates an object. It's not in the database yet.
            // The next line tells JPA to "stage" the object
            em.persist(newMuseum);

            // Committing the transaction will push/"flush" the changes to the database.
            em.getTransaction().commit();
            System.out.println("Museum " + newMuseum + " added to database. Go check DataGrip if you don't believe me!");

            // Example 3: updating an entity.
            Museum fromDatabase = em.find(Museum.class, 4);
            em.getTransaction().begin();
            fromDatabase.setLocation("Manhattan, New York, NY");
            // This object is already staged, since it was retrieved from the EntityManager.
            // We just need to flush the change.
            em.getTransaction().commit();
        }

        System.out.println();
        System.out.println("Example #3: retrieving an object without its primary key");
        // EntityManager.find can only look at primary keys. To do other queries,
        // we have to write "JPQL" -- a language very similar to SQL.

        // Suppose we want to find "Museum of Latin American Art". We'd write this query:
        // SELECT * FROM MUSEUMS WHERE NAME = 'Museum of Latin American Art'

        // JPA doesn't use SQL; it uses JPQL, which doesn't select from arbitrary tables,
        // but instead selects from the entity types known to JPA, like MUSEUMS.
        String jpaQuery = "SELECT m FROM MUSEUMS m WHERE m.name = " +
            "'Museum of Latin American Art'";


        // The important bit is "MUSEUMS m"; this tells the query to iterate over the
        // MUSEUMS table one row at a time, temporarily calling each row "m". We can then
        // refer to "m" like it's an object, in order to select a row or filter based 
        // on its columns.

        // createQuery returns a Query object, which can be executed with getSingleResult
        // if it always returns <= 1 object.
        Museum molaa = em.createQuery(jpaQuery, Museum.class).getSingleResult();
        System.out.println("MOLAA retrieved: " + molaa);

        // If we want to SAFELY involve user input, we use a TypedQuery.
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter a museum name: ");
        String name = input.nextLine();

        // A TypedQuery is strongly typed; a normal Query would not be.
        var namedMuseum = em.createQuery("SELECT m FROM MUSEUMS m WHERE "
            + "m.name = ?1", Museum.class);
        namedMuseum.setParameter(1, name);
        try {
            Museum requested = namedMuseum.getSingleResult();
            System.out.println("Your requested museum: " + requested);
            
        }
        catch (NoResultException ex) {
            System.out.println("Museum with name '" + name + "' not found.");
        }

        System.out.println();
        System.out.println("Example #4: Using JPQL to select all museums");
        // This is simple. Just omit the WHERE, and use .getResultList().
        var museums = em.createQuery("select m from MUSEUMS m", Museum.class).getResultList();

        for (Museum m : museums) {
            System.out.println(m);
        }
    }

    // These demos show how to navigate associations.
    private static void associationDemos() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();
        


        System.out.println();
        System.out.println("Example #5: Navigating a one-to-one association");
        // With the annotations set up, navigating from one object to its associated
        // object is a breeze!
        
        Museum molaa = em.find(Museum.class, 1);
        // Museum has a superintendent field that we can access to follow the association:
        System.out.println("MOLAA's Superintendent: " + molaa.getSuperintendent());

        Superintendent sup = em.find(Superintendent.class, 1);
        System.out.println("Superindendent #1: " + sup);

        // We can also query a Superintendent for a specific Museum:
        sup = em.createQuery("SELECT s FROM SUPERINTENDENTS s " +
            "WHERE s.museum.museumId = 1", Superintendent.class).getSingleResult();
        
        // REMINDER: JPA is interested in Java objects, not SQL tables.
        // JPQL lets us navigate between objects using "." notation like accessing fields.
        // Instead of using column names like MUSEUM_ID, we use field names from
        // the appropriate class(es).
        System.out.println("The superintendent for Museum #1: " + sup);


        System.out.println();
        System.out.println("Example #6: Navigating a one-to-many association");
        
        System.out.println("MOLAA's Buildings:");
        for (Building b : molaa.getBuildings()) {
            System.out.println(b);
        }

        System.out.println();
        // In a bidirectional association, we can also walk from the Many to the One...
        Building bu = em.find(Building.class, 1);
        System.out.println(bu + " is in Museum " + bu.getMuseum());

        // ... or even find the Many objects based on the One they are associated with.
        var buildings = em.createQuery("SELECT b FROM BUILDINGS b " +
            "WHERE b.museum.museumId = 1", Building.class).getResultList();

        System.out.println("MOLAA's Buildings, using a query:");
        for (Building b : buildings) {
            System.out.println(b);
        }

        System.out.println();
        System.out.println("Example #7: Navigating a many-to-many association");
        System.out.println("The Members of " + molaa + ":");
        for (Visitor v : molaa.getMembers()) {
            System.out.println(v);
            
            for (Museum m : v.getMemberships()) {
                System.out.println("\tmember of " + m + " ");
            }
        }

        for (MuseumVisit visit : molaa.getVisits()) {
            System.out.println(visit.getVisitor() + " went to " + visit.getMuseum() 
                + " on " + visit.getVisitDate());
        }

        Visitor neal = em.find(Visitor.class, 1);
        for (MuseumVisit visit : neal.getVisits()) {
            System.out.println(neal + " went to " + visit.getMuseum() 
                + " on " + visit.getVisitDate());
        }

    }
    
    
    public static void instantiate(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();

        Semester s1 = new Semester("Spring 2021", LocalDate.of(2021,1,19));
        Semester s2 = new Semester("Fall 2021", LocalDate.of(2021,8,17));
        Semester s3 = new Semester("Spring 2022", LocalDate.of(2022,1,20));

        Department d1 = new Department("Computer Engineering and Computer Science", "CECS");
        Department d2 = new Department("Italian Studies", "ITAL");

        TimeSlot t1 = new TimeSlot((byte)(40), LocalTime.of(12,30), LocalTime.of(13,45));
        TimeSlot t2 = new TimeSlot((byte)(20), LocalTime.of(14, 0), LocalTime.of(15,15));
        TimeSlot t3 = new TimeSlot((byte)(42), LocalTime.of(12, 0), LocalTime.of(12,50));
        TimeSlot t4 = new TimeSlot((byte)(2), LocalTime.of(8, 0), LocalTime.of(10,45));
        
        Course c1 = new Course(d1, "174", "Introduction to Programming and Problem Solving", 3);
        Course c2 = new Course(d1, "274", "Data Structures", 3);
        Course c3 = new Course(d1, "277", "Object Oriented Application Programming", 3);
        Course c4 = new Course(d1, "282", "Advanced C++", 3);
        Course c5 = new Course(d2, "101A", "Fundamentals of Italian", 4);
        Course c6 = new Course(d2, "101B", "Fundamentals of Italian", 4);

        Prerequisite p1 = new Prerequisite('C', c2, c1);
        Prerequisite p2 = new Prerequisite('C', c3, c2);
        Prerequisite p3 = new Prerequisite('C', c4, c2);
        Prerequisite p4 = new Prerequisite('C', c4, c3);
        Prerequisite p5 = new Prerequisite('D', c6, c5);

        Section a = new Section(c1, 1, s1, t1, 105);
        Section b = new Section(c2, 1, s2, t2, 140);
        Section c = new Section(c3, 3, s2, t4, 35);
        Section d = new Section(c4, 5, s3, t2, 35);
        Section e = new Section(c3, 1, s3, t1, 35);
        Section f = new Section(c4, 7, s3, t1, 35);
        Section g = new Section(c5, 1, s3, t3, 25);

        Student st1 = new Student("Naomi Nagata", 123456789);
        Student st2 = new Student("James Holden", 987654321);
        Student st3 = new Student("Amos Burton", 555555555);

        //Naomi
        Transcript tr1 = new Transcript("A", a, st1);
        Transcript tr2 = new Transcript("A", b, st1);
        Transcript tr3 = new Transcript("A", c, st1);
        //James
        Transcript tr4 = new Transcript("C", a, st2);
        Transcript tr5 = new Transcript("C", b, st2);
        Transcript tr6 = new Transcript("C", c, st2);
        //Amos
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

    public static void lookup(){
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
        
        }
        catch (NoResultException ex) {
            System.out.println("Student with name '" + name + "' not found.");
        }

    }

    public static void register(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();
        instantiate();
        var semesters = em.createQuery("SELECT s FROM SEMESTERS s",Semester.class).getResultList();
        for(Semester s : semesters){
            System.out.println(s);
        }


    }

    public static void menu(){
        Scanner input = new Scanner(System.in);
        while(true){
            System.out.println("Input a number option");
            System.out.println("Menu:");
            System.out.println("(1) Instantiate model");
            System.out.println("(2) Student lookup");
            System.out.println("(3) Register for a course");
            System.out.println("(0) Exit");
            int i = input.nextInt();
            if(i==1){
                System.out.println("Initializing...");
                instantiate();
            }
            else if(i==2){
                System.out.println("Looking up student...");
                lookup();
            }
            else if(i==3){
                System.out.println("Registering course...");
                register();
            }
            else if(i==0){
                System.out.println("Bye.");
                break;
            }
            else{
                System.out.println("Invalid input.\nTry again.");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //basicDemos();
        //associationDemos();
        //EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        menu();
    }
}
