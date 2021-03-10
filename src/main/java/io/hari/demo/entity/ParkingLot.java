package io.hari.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Hariom Yadav
 * @create 08-03-2021
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"slots"}, callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class ParkingLot extends BaseEntity{
    String type;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Slot> slots = new ArrayList<>();

    //other metadata
    @JsonProperty
    public List<Slot> getParkingSlots() {
        return slots;
    }
}
