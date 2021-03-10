package io.hari.demo.dao;

import io.hari.demo.entity.Ticket;
import io.hari.demo.entity.Vehicle;
import io.hari.demo.entity.VehicleColor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author Hariom Yadav
 * @create 08-03-2021
 */
@Repository
public interface TicketDao extends BaseDao<Ticket>{
    List<Ticket> findAllByVehicle_Color(VehicleColor color);

    @Query(value = "select * from ticket where slot_id = :slotId",
            nativeQuery = true)
    Ticket findSlotVehicle(Long slotId);//we can store in list also it will store all result, here it will store 1st data

//    @Query(value = "select * from vehicle where id in (select vehicle_id from ticket where slot_id = :slotId)",
//            nativeQuery = true)
//    List<Vehicle> findSlotVehicle2(Long slotId);
// error coz this class will expect return type as Ticket
//    else error convert, move this logic to vehicle class

}
