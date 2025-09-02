package app;

import app.DAO.DolphinDAO;
import app.config.HibernateConfig;
import app.entities.Fee;
import app.entities.Node;
import app.entities.Person;
import app.entities.PersonDetail;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== DolphinDAO scenario test ===");

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        DolphinDAO dolphinDAO = new DolphinDAO(emf);

        try {
            // 1) CREATE
            System.out.println("\n1) CREATE");
            Person p1 = Person.builder().name("Hanzi").build();

            PersonDetail pd1 = new PersonDetail("Algade 3", 4300, "Holbæk", 45);
            p1.addPersonDetail(pd1);

            Fee f1 = Fee.builder().amount(125).payDate(LocalDate.of(2023, 8, 25)).build();
            Fee f2 = Fee.builder().amount(150).payDate(LocalDate.of(2023, 7, 19)).build();
            p1.addFee(f1);
            p1.addFee(f2);

            Node n1 = Node.builder().note("Nakkede kantinens kylling").created(LocalDate.now()).createdBy("Marcus").build();
            Node n2 = Node.builder().note("skidt på").created(LocalDate.now()).createdBy("Marcus").build();
            p1.addNote(n1);
            p1.addNote(n2);

            Person created = dolphinDAO.create(p1);
            System.out.println("Created person: " + created);
            Integer id = created.getId();
            System.out.println("Created id = " + id);

            // 2) GET BY ID
            System.out.println("\n2) GET BY ID");
            Person person = dolphinDAO.getById(id);
            System.out.println("Fetched by id " + id + " -> " + person);

            // 3) GET ALL
            System.out.println("\n3) GET ALL");
            List<Person> all = dolphinDAO.getAll();
            System.out.println("Total persons: " + all.size());
            all.forEach(System.out::println);

            // 4) UPDATE
            System.out.println("\n4) UPDATE");
            person.setName("Abdul"); // change name
            Fee extra = Fee.builder().amount(200).payDate(LocalDate.now()).build();
            person.addFee(extra); // add a new fee
            Node extraNode = Node.builder().note("Added during update").created(LocalDate.now()).createdBy("MainTest").build();
            person.addNote(extraNode);

            Person updated = dolphinDAO.update(person);
            System.out.println("Updated person -> " + updated);

            // 5) DELETE
            System.out.println("\n5) DELETE");
            boolean deleted = dolphinDAO.delete(1);
        } finally {
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }
}
