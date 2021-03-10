package io.hari.demo.dao;

import io.hari.demo.entity.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Hariom Yadav
 * @create 08-03-2021
 */
@Repository
public interface VehicleDao extends BaseDao<Vehicle>{

    @Query(value = "select * from vehicle where id in (select vehicle_id from ticket where slot_id = :slotId)",
            nativeQuery = true)
    List<Vehicle> findSlotVehicle2(Long slotId);
}
