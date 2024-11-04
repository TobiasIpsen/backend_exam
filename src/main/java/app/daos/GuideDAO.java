package app.daos;

import app.dtos.GuideDTO;
import app.dtos.TripDTO;
import app.entities.Guide;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class GuideDAO implements IDAO<GuideDTO, Integer> {
    private static GuideDAO instance;
    private static EntityManagerFactory emf;
    Logger logger = LoggerFactory.getLogger(GuideDAO.class);

    @Synchronized //Allow only one thread to access it at once
    public static GuideDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GuideDAO();
        }
        return instance;
    }

    @Override
    public List<GuideDTO> getAll() throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Guide> query = em.createQuery("SELECT g FROM Guide g", Guide.class);
            List<Guide> resultList = query.getResultList();
            return resultList.stream()
                    .map(GuideDTO::new)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Failed to fetch all Guides", e);
            throw new ApiException(400, "Something went wrong in getAll method: " + e.getMessage());
        }
    }

    public List<Map<String, Integer>> guidesTotalPrice() {
        try (EntityManager em = emf.createEntityManager()) {
            List<Map<String, Integer>> guidePriceList = new ArrayList<>();

            TypedQuery<Guide> query = em.createQuery("SELECT g FROM Guide g", Guide.class);
            List<Guide> resultList = query.getResultList();

            for (Guide guide : resultList) {

                Map<String, Integer> guidePriceMap  = new HashMap<>();

                int totalPrice = guide.getTrips().stream()
                        .mapToInt(trip -> trip.getPrice())
                        .sum();

                guidePriceMap .put("guideId", guide.getId());
                guidePriceMap .put("totalPrice", totalPrice);

                guidePriceList.add(guidePriceMap);
            }

            return guidePriceList;
        } catch (Exception e) {
            logger.error("Failed to fetch all Guides", e);
            throw new ApiException(400, "Something went wrong in getAll method: " + e.getMessage());
        }
    }

    @Override
    public GuideDTO getById(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {

            Guide entityFound = em.find(Guide.class, id);
            if (entityFound == null) {
                logger.error("Entity with ID: " + id + " not found");
                throw new ApiException(404, "Entity with ID: " + id + " not found");
            }
            return new GuideDTO(entityFound);

        } catch (ApiException e) {
            logger.error("Entity not found", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred in getById method", e);
            throw new ApiException(500, "Unexpected error occurred in getById method: " + e.getMessage());
        }
    }

    @Override
    public GuideDTO create(GuideDTO dto) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {

            Guide entity = new Guide(dto);

            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();

            return new GuideDTO(entity);
        } catch (Exception e) {
            logger.error("Unexpected error occurred in create method", e);
            throw new ApiException(500, "Unexpected error occurred in create method: " + e.getMessage());
        }
    }

    @Override
    public GuideDTO update(Integer id, GuideDTO dto) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {

            Guide entityFound = em.find(Guide.class, id);
            if (entityFound == null) {
                throw new ApiException(404, "Entity with ID: " + id + " not found");
            }

            em.getTransaction().begin();

            entityFound.setFirstName(dto.getFirstName());
            entityFound.setLastName(dto.getLastName());
            entityFound.setEmail(dto.getEmail());
            entityFound.setPhone(dto.getPhone());
            entityFound.setYearsOfExperience(dto.getYearsOfExperience());

            em.getTransaction().commit();

            return new GuideDTO(entityFound);
        } catch (ApiException e) {
            logger.error("Entity not found", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred in update method", e);
            throw new ApiException(500, "Unexpected error occurred in update method: " + e.getMessage());
        }
    }

    @Override
    public GuideDTO delete(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {

            Guide entityFound = em.find(Guide.class, id);
            if (entityFound == null) {
                throw new ApiException(404, "Entity with ID: " + id + " not found");
            }

            em.getTransaction().begin();
            em.remove(entityFound);
            em.getTransaction().commit();

            return new GuideDTO(entityFound);
        } catch (ApiException e) {
            logger.error("Entity not found", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred in delete method", e);
            throw new ApiException(500, "Unexpected error occurred in delete method: " + e.getMessage());
        }
    }

}
