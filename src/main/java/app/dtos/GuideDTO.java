package app.dtos;

import app.entities.Guide;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuideDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int yearsOfExperience;

//    @ToString.Exclude
//    private List<TripDTO> tripsDTO = new ArrayList<>();

    public GuideDTO(Guide entity) {
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
        this.yearsOfExperience = entity.getYearsOfExperience();
//        if (entity.getTrips() != null) {
//            entity.getTrips().forEach(trip -> this.tripsDTO.add(new TripDTO(trip)));
//        }
    }
}
