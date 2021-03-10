package io.hari.demo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @Author Hariom Yadav
 * @create 08-03-2021
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {}, callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Vehicle extends BaseEntity{
    String registrationNum;

    @Enumerated(value = EnumType.STRING)
    VehicleColor color;

    //other metadata

}
