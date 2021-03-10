package io.hari.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;

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
public class Slot extends BaseEntity{
    String status;

    //other metadata

    @JsonProperty
    public Long getSlotId() {
        return super.getId();
    }
}
