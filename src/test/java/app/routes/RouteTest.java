package app.routes;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.config.Populate;
import app.dtos.GuideDTO;
import app.dtos.TripDTO;
import app.dtos.UserDTO;
import app.entities.Trip;
import app.entities.Guide;
import app.enums.Category;
import app.security.controllers.SecurityController;
import app.security.daos.SecurityDAO;
import app.security.exceptions.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RouteTest {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(true);
    private static List<Trip> tripList = new ArrayList<>();
    private static List<Guide> guideList = new ArrayList<>();
    private static Javalin app;

    // -------------- Security --------------
    private static final SecurityController securityController = SecurityController.getInstance();
    private static final SecurityDAO securityDAO = new SecurityDAO(emf);
    private static UserDTO userDTO, adminDTO;
    private static String userToken, adminToken;

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "http://localhost:7070/api/trips";
        app = ApplicationConfig.startServer(7070);
    }

    @BeforeEach
    void beforeEach() {
        PopulateTest.populateTestDB(emf);

        // -------------- Security --------------
        UserDTO[] users = Populate.populateUsers(emf);
        userDTO = users[0];
        adminDTO = users[1];

        try {
            UserDTO verifiedUser = securityDAO.getVerifiedUser(userDTO);
            UserDTO verifiedAdmin = securityDAO.getVerifiedUser(adminDTO);
            userToken = "Bearer " + securityController.createToken(verifiedUser);
            adminToken = "Bearer " + securityController.createToken(verifiedAdmin);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void afterEach() {
        tripList.clear();
        guideList.clear();
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE Trip").executeUpdate();
            em.createQuery("DELETE Guide").executeUpdate();
            em.createQuery("DELETE Role").executeUpdate();
            em.createQuery("DELETE User").executeUpdate();

            em.getTransaction().commit();
        }
    }

    @AfterAll
    static void afterAll() {
        ApplicationConfig.stopServer(app);
    }

    @Test
    void getAll() {
        given()
                .when()
                // .header("Authorization", userToken)
                .get("/")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    void getById() {

        try (EntityManager em = emf.createEntityManager()) {
            Trip entity = em.createQuery("SELECT t FROM Trip t WHERE t.name = :name", Trip.class)
                    .setParameter("name", "California Beach Getaway")
                    .getSingleResult();

            given()
                    .pathParam("id", entity.getId())
                    .when()
                    .get("/{id}")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("id", equalTo(entity.getId()));
        }
    }

    @Test
    void getByIdNotFound() {
        given()
                .pathParam("id", 1000)
                .when()
                .get("/{id}")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    void getByIdString() {
        given()
                .pathParam("id", "string")
                .when()
                .get("/{id}")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    void create() throws JsonProcessingException {

        TripDTO dto = new TripDTO(LocalTime.of(11, 0), LocalTime.of(21, 0), "USA", "Beach Getaway", 150, Category.BEACH);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(dto);

        given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("/")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    void createBadRequest() {

        given()
                .contentType("application/json")
                .body("{}")
                .when()
                .post("/")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    void updateTrip() throws JsonProcessingException {

        TripDTO dto = new TripDTO(LocalTime.of(9, 0), LocalTime.of(21, 0), "USA", "Beach Getaway", 150, Category.BEACH);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(dto);

        try (EntityManager em = emf.createEntityManager()) {
            Trip entity = em.createQuery("SELECT t FROM Trip t WHERE t.name = :name", Trip.class)
                    .setParameter("name", "California Beach Getaway")
                    .getSingleResult();

            given().
                    contentType("application/json")
                    .body(json)
                    .pathParam("id", entity.getId())
                    .when()
                    .put("/{id}")
                    .then()
                    .log().all()
                    .statusCode(200);
        }
    }

    @Test
    void delete() {
        try (EntityManager em = emf.createEntityManager()) {
            Trip entity = em.createQuery("SELECT t FROM Trip t WHERE t.name = :name", Trip.class)
                    .setParameter("name", "California Beach Getaway")
                    .getSingleResult();

            given()
                    .pathParam("id", entity.getId())
                    .when()
                    .delete("/{id}")
                    .then()
                    .log().all()
                    .statusCode(204);
        }
    }

    @Test
    void addGuide() {

        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.createQuery("SELECT t FROM Trip t WHERE t.name = :name", Trip.class)
                    .setParameter("name", "California Beach Getaway")
                    .getSingleResult();
            Guide guide = em.createQuery("SELECT g FROM Guide g WHERE g.firstName = :name", Guide.class)
                    .setParameter("name", "Alice")
                    .getSingleResult();

            given()
                    .pathParam("tripId", trip.getId())
                    .pathParam("guideId", guide.getId())
                    .when()
                    .put("/{tripId}/guides/{guideId}")
                    .then()
                    .log().all()
                    .statusCode(200);
        }
    }

    @Test
    void tripsByGuide() {

        try (EntityManager em = emf.createEntityManager()) {
            Guide guide = em.createQuery("SELECT g FROM Guide g WHERE g.firstName = :name", Guide.class)
                    .setParameter("name", "Alice")
                    .getSingleResult();

            given()
                    .pathParam("guideId", guide.getId())
                    .when()
                    .get("/{guideId}")
                    .then()
                    .log().all()
                    .statusCode(200);
        }
    }

    @Test
    void getByCategory() {
        given()
                .pathParam("category", "CITY")
                .when()
                .get("/category/{category}")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    void getBySpecialityNotFound() {
        given()
                .pathParam("category", "notfound")
                .when()
                .get("/category/{category}")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    void guidesTotalPrice() {
        when()
                .get("/guides/totalPrice")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    void tripsPackingList() {
        given()
                .pathParam("category", "beach")
                .when()
                .get("/category/packinglist/{category}")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    void tripsPackingWeight() {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.createQuery("SELECT t FROM Trip t WHERE t.name = :name", Trip.class)
                    .setParameter("name", "California Beach Getaway")
                    .getSingleResult();

            given()
                    .pathParam("id", trip.getId())
                    .when()
                    .get("/{id}/packingweight")
                    .then()
                    .log().all()
                    .statusCode(200);
        }
    }

}
