package app;

import app.config.HibernateConfig;
import app.entities.Fee;
import app.entities.Person;
import app.entities.PersonDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Dolphin!");

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        try (EntityManager em = emf.createEntityManager()) {
            Person p1 = Person.builder()
                    .name("Hanzi")
                    .build();

            PersonDetail pd1 = new PersonDetail("Algade 3", 4300, "Holbæk", 45);
            p1.addPersonDetail(pd1);
            Fee f1 = Fee.builder()
                    .amount(125)
                    .payDate(LocalDate.of(2023, 8, 25))
                    .build();

            Fee f2 = Fee.builder()
                    .amount(150)
                    .payDate(LocalDate.of(2023, 7, 19))
                    .build();

            p1.addFee(f1);
            p1.addFee(f2);

            em.getTransaction().begin();
            em.persist(p1);
            em.getTransaction().commit();
            System.out.println(p1);
        }
        emf.close();
    }
}