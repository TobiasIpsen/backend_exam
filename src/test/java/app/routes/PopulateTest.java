package app.routes;

import app.entities.Trip;
import app.entities.Guide;
import app.enums.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PopulateTest {

    public static HashMap<List<Trip>, List<Guide>> populateTestDB(EntityManagerFactory emf) {
        Trip trip1 = new Trip(LocalTime.of(10, 0), LocalTime.of(10, 0), "Los Angeles", "California Beach Getaway", 1500, Category.BEACH);
        Trip trip2 = new Trip(LocalTime.of(8, 0), LocalTime.of(15, 0), "New York City", "NYC Urban Adventure", 1200, Category.CITY);
        Trip trip3 = new Trip(LocalTime.of(9, 20), LocalTime.of(20, 20), "Miami", "Miami Beach Party", 1800, Category.BEACH);


        Guide guide1 = new Guide("Alice", "Johnson", "alice.johnson@example.com", "123-456-7890", 5);
        Guide guide2 = new Guide("Bob", "Smith", "bob.smith@example.com", "234-567-8901", 8);
        Guide guide3 = new Guide("Carol", "Davis", "carol.davis@example.com", "345-678-9012", 3);

        trip1.addGuide(guide1);
        trip2.addGuide(guide2);
//        trip3.addGuide(guide3);

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.persist(trip1);
            em.persist(trip2);
            em.persist(trip3);

            em.persist(guide1);
            em.persist(guide2);
            em.persist(guide3);

            em.getTransaction().commit();
        }

        List<Trip> tripList = new ArrayList<>();
        tripList.add(trip1);
        tripList.add(trip2);
        tripList.add(trip3);

        List<Guide> arrayList = new ArrayList<>();
        arrayList.add(guide1);
        arrayList.add(guide2);
        arrayList.add(guide3);

        HashMap<List<Trip>, List<Guide>> hashMap = new HashMap<>();
        hashMap.put(tripList, arrayList);
        return hashMap;
    }
}
