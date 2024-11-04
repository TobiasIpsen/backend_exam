package app.daos;

import app.config.Populate;
import app.dtos.TripDTO;
import app.entities.Guide;
import app.entities.Trip;
import app.enums.Category;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TripDAO implements ITripGuideDAO, IDAO<TripDTO, Integer> {
    private static TripDAO instance;
    private static EntityManagerFactory emf;
    Logger logger = LoggerFactory.getLogger(TripDAO.class);

    @Synchronized //Allow only one thread to access it at once
    public static TripDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TripDAO();
        }
        return instance;
    }

    @Override
    public List<TripDTO> getAll() throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {

            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t", Trip.class);
            List<Trip> resultList = query.getResultList();
            return resultList.stream()
                    .map(TripDTO::new)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Failed to fetch all Trips", e);
            throw new ApiException(400, "Something went wrong in getAll method: " + e.getMessage());
        }
    }

    @Override
    public TripDTO getById(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {

            Trip entityFound = em.find(Trip.class, id);
            if (entityFound == null) {
                logger.error("Entity with ID: " + id + " not found");
                throw new ApiException(404, "Entity with ID: " + id + " not found");
            }
            return new TripDTO(entityFound);

        } catch (ApiException e) {
            logger.error("Entity not found", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred in getById method", e);
            throw new ApiException(500, "Unexpected error occurred in getById method: " + e.getMessage());
        }
    }

    @Override
    public TripDTO create(TripDTO dto) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {

            Trip entity = new Trip(dto);

            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();

            return new TripDTO(entity);
        } catch (Exception e) {
            logger.error("Unexpected error occurred in create method", e);
            throw new ApiException(500, "Unexpected error occurred in create method: " + e.getMessage());
        }
    }

    @Override
    public TripDTO update(Integer id, TripDTO dto) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {

            Trip entityFound = em.find(Trip.class, id);
            if (entityFound == null) {
                throw new ApiException(404, "Entity with ID: " + id + " not found");
            }

            em.getTransaction().begin();
            entityFound.setStartTime(dto.getStartTime());
            entityFound.setEndTime(dto.getEndTime());
            entityFound.setStartPosition(dto.getStartPosition());
            entityFound.setName(dto.getName());
            entityFound.setPrice(dto.getPrice());
            entityFound.setCategory(dto.getCategory());

            em.getTransaction().commit();

            return new TripDTO(entityFound);
        } catch (ApiException e) {
            logger.error("Entity not found", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred in update method", e);
            throw new ApiException(500, "Unexpected error occurred in update method: " + e.getMessage());
        }
    }

    @Override
    public TripDTO delete(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {

            Trip entityFound = em.find(Trip.class, id);
            if (entityFound == null) {
                throw new ApiException(404, "Entity with ID: " + id + " not found");
            }

            em.getTransaction().begin();
            em.remove(entityFound);
            em.getTransaction().commit();

            return new TripDTO(entityFound);
        } catch (ApiException e) {
            logger.error("Entity not found", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred in delete method", e);
            throw new ApiException(500, "Unexpected error occurred in delete method: " + e.getMessage());
        }
    }

    @Override
    public void addGuideToTrip(int tripId, int guideId) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {

            Trip trip = em.find(Trip.class, tripId);
            if (trip == null) {
                throw new ApiException(404, "Trip with ID: " + tripId + " not found");
            }

            Guide guide = em.find(Guide.class, guideId);
            if (guide == null) {
                throw new ApiException(404, "Guide with ID: " + guideId + " not found");
            }

            em.getTransaction().begin();
            trip.addGuide(guide);
            em.getTransaction().commit();

        } catch (ApiException e) {
            logger.error("Entity not found", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred in addGuideToTrip method", e);
            throw new ApiException(500, "Unexpected error occurred in addGuideToTrip method: " + e.getMessage());
        }
    }

    @Override
    public Set<TripDTO> getTripsByGuide(int guideId) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, guideId);
            if (guide == null) {
                throw new ApiException(404, "Guide with ID: " + guideId + " not found");
            }
            return guide.getTrips().stream()
                    .map(TripDTO::new)
                    .collect(Collectors.toSet());
        } catch (ApiException e) {
            logger.error("Entity not found", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred in getTripsByGuide method", e);
            throw new ApiException(500, "Unexpected error occurred in getTripsByGuide method: " + e.getMessage());
        }
    }

    public List<TripDTO> populate() {
        Populate.populateDB(emf);
        return getAll();
    }
}
