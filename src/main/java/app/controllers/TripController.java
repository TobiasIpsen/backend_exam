package app.controllers;

import app.config.HibernateConfig;
import app.daos.GuideDAO;
import app.daos.TripDAO;
import app.dtos.FetchDTO;
import app.dtos.GuideDTO;
import app.dtos.ItemDTO;
import app.dtos.TripDTO;
import app.enums.Category;
import app.exceptions.ApiException;
import app.service.ApiFetch;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TripController {

    ObjectMapper om = new ObjectMapper();
    TripDAO tripDAO;
    GuideDAO guideDAO;

    public TripController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
        this.tripDAO = TripDAO.getInstance(emf);
        this.guideDAO = GuideDAO.getInstance(emf);
    }

    public void getAll(Context ctx) throws ApiException {
        List<TripDTO> dtoList = tripDAO.getAll();
        ctx.status(200); //OK
        ctx.json(dtoList);
    }

    public void getById(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TripDTO dto = tripDAO.getById(id);

            FetchDTO fetchDTO = ApiFetch.get(dto.getCategory().toString());
            dto.setPackingItems(fetchDTO.getItems());

            ctx.status(200); //OK
            ctx.json(dto);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid id"); //Bad Request
        }
    }

    public void create(Context ctx) throws ApiException {
        TripDTO dto;
        try {
            dto = ctx.bodyAsClass(TripDTO.class);
        } catch (Exception e) {
            throw new ApiException(400, "Invalid request body"); //Bad Request
        }

        if (dto.getStartTime() == null ||
                dto.getEndTime() == null ||
                dto.getStartPosition() == null ||
                dto.getName() == null ||
                dto.getPrice() < 0) {
            throw new ApiException(400, "Trip is missing required fields"); //Bad Request
        }

        try {
            Category.valueOf(dto.getCategory().toString());
        } catch (IllegalArgumentException e) {
            throw new ApiException(400, "Invalid category"); //Bad Request
        }

        if (dto.getName().isEmpty() || dto.getStartPosition().isEmpty()) {
            throw new ApiException(400, "Trip is missing required fields"); //Bad Request
        }

        TripDTO createdDTO = tripDAO.create(dto);
        ctx.status(200); //OK
        ctx.json(createdDTO);
    }

    public void update(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TripDTO dto;
            try {
                dto = ctx.bodyAsClass(TripDTO.class);
            } catch (Exception e) {
                throw new ApiException(400, "Invalid request body"); //Bad Request
            }

            TripDTO updatedDTO = tripDAO.update(id, dto);
            ctx.status(200); //OK
            ctx.json(updatedDTO);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid id"); //Bad Request
        }
    }

    public void delete(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            tripDAO.delete(id);
            ctx.status(204); //No Content
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid id"); //Bad Request
        }
    }

    public void addGuide(Context ctx) throws ApiException {
        try {
            int tripId = Integer.parseInt(ctx.pathParam("tripId"));
            int guideId = Integer.parseInt(ctx.pathParam("guideId"));
            tripDAO.addGuideToTrip(tripId, guideId);
            ctx.status(204); //No Content
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid id"); //Bad Request
        }
    }

    public void populate(Context ctx) {
        List<TripDTO> dtoList = tripDAO.populate();
        ctx.status(200); //OK
        ctx.json(dtoList);
    }

    public void tripsByGuide(Context ctx) throws ApiException {
        try {
            int guideId = Integer.parseInt(ctx.pathParam("guideId"));
            Set<TripDTO> dtoList = tripDAO.getTripsByGuide(guideId);
            ctx.status(200); //OK
            ctx.json(dtoList);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid id"); //Bad Request
        }
    }

    public void tripsByCategory(Context ctx) throws ApiException {
        try {
            Category category = Category.valueOf(ctx.pathParam("category"));
            List<TripDTO> dtoList = tripDAO.getAll();
            dtoList = dtoList.stream()
                    .filter(tripDTO -> tripDTO.getCategory().equals(category))
                    .collect(Collectors.toList());
            ctx.status(200); //OK
            ctx.json(dtoList);
        } catch (IllegalArgumentException e) {
            throw new ApiException(400, "Invalid category"); //Bad Request
        }
    }

    public void guidesTotalPrice(Context ctx) throws ApiException {
        List<Map<String, Integer>> guidesTotalPrice = guideDAO.guidesTotalPrice();
        ctx.status(200); //OK
        ctx.json(guidesTotalPrice);
    }


    public void tripsPackingList(Context ctx) {
        String category = ctx.pathParam("category");
        ctx.status(200); //OK
        ctx.json(ApiFetch.get(category));
    }

    public void tripsPackingWeight(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TripDTO dto = tripDAO.getById(id);
            FetchDTO fetchDTO = ApiFetch.get(dto.getCategory().toString());

            int totalWeight = fetchDTO.getItems().stream()
                    .mapToInt(ItemDTO::getWeightInGrams)
                    .sum();

            Map<String, Integer> map = new HashMap<>();
            map.put("totalWeight", totalWeight);

            ctx.status(200); //OK
            ctx.json(map);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid id"); //Bad Request
        }
    }
}
