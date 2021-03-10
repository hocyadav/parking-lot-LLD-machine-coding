package io.hari.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @Author Hariom Yadav
 * @create 08-03-2021
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"slot", "vehicle"}, callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Ticket extends BaseEntity{
    @OneToOne(cascade = CascadeType.ALL)//TODO DONE : we want run time value and create obj in db
    Vehicle vehicle;

    @OneToOne// we will use old db value and pass
    Slot slot;

    //other metadata


    @JsonProperty
    public String getRegistrationNum() {
        return vehicle.getRegistrationNum();
    }

    @JsonProperty
    public Long slotId() {
        return slot.getId();
    }
}
