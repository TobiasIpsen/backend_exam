package app.dtos;

import app.entities.Guide;
import app.entities.Trip;
import app.enums.Category;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {

    private int id;
    private LocalTime startTime;
    private LocalTime endTime;
    private String startPosition;
    private String name;
    private int price;
    private Category category;

    @ToString.Exclude
    private GuideDTO guideDTO;

    private List<ItemDTO> packingItems;

    public TripDTO(Trip entity) {
        this.id = entity.getId();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
        this.startPosition = entity.getStartPosition();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.category = entity.getCategory();

        if (entity.getGuide() != null) {
            this.guideDTO = new GuideDTO(entity.getGuide());
        }
    }

    public TripDTO(LocalTime startTime, LocalTime endTime, String startPosition, String name, int price, Category category) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.name = name;
        this.price = price;
        this.category = category;
    }
}
