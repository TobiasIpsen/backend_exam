package app.entities;

import app.dtos.GuideDTO;
import app.dtos.TripDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@ToString
public class Guide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int yearsOfExperience;

    @OneToMany(mappedBy = "guide")
    @ToString.Exclude
    private List<Trip> trips = new ArrayList<>();

    //used in populate method/class
    public Guide(int id, String firstName, String lastName, String email, String phone, int yearsOfExperience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }

    public Guide(String firstName, String lastName, String email, String phone, int yearsOfExperience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }

    public Guide(GuideDTO dto){
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.yearsOfExperience = dto.getYearsOfExperience();
//        if (dto.getTripsDTO() != null) {
//            dto.getTripsDTO().forEach(tripDTO -> this.trips.add(new Trip(tripDTO)));
//        }
    }
}
