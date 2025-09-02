package app.DAO;

import app.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DolphinDAO implements IDAO<Person, Integer> {
    private final EntityManagerFactory emf;

    public DolphinDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Person create(Person person) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(person);
            entityManager.getTransaction().commit();
            return person;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Person getById(Integer id) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager.find(Person.class, id);
        }
    }

    @Override
    public List<Person> getAll() {
        try (EntityManager entityManager = emf.createEntityManager()) {
            TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p", Person.class);
            return query.getResultList();
        }
    }

    @Override
    public Person update(Person person) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();
            person.getFees().forEach(fee -> entityManager.merge(fee));
            person.getNodes().forEach(node -> entityManager.merge(node));
            person = entityManager.merge(person);
            entityManager.getTransaction().commit();
        }
        return person;
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()){
            Person personToDelete = em.find(Person.class, id);
            if (personToDelete != null){
                em.getTransaction().begin();
                em.remove(personToDelete);
                em.getTransaction().commit();
                return true;
            } else {
                return false;
            }
        } catch (PersistenceException e){
            return false;
        }
    }
}

