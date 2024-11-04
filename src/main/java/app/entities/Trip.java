package app.entities;

import app.dtos.GuideDTO;
import app.dtos.TripDTO;
import app.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@ToString
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalTime startTime;
    private LocalTime endTime;
    private String startPosition;
    private String name;
    private int price;
    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @ToString.Exclude
    private Guide guide;

    public void addGuide(Guide guide) {
        this.guide = guide;
        guide.getTrips().add(this);
    }

    //used in test class
    public Trip(int id, LocalTime startTime, LocalTime endTime, String startPosition, String name, int price, Category category) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Trip(LocalTime startTime, LocalTime endTime, String startPosition, String name, int price, Category category) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Trip(TripDTO dto) {
        this.id = dto.getId();
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
        this.startPosition = dto.getStartPosition();
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.category = dto.getCategory();
//        this.guide = new Guide(dto.getGuideDTO());
    }
}
