package io.hari.demo;

import io.hari.demo.config.AppConfig;
import io.hari.demo.dao.ParkingLotDao;
import io.hari.demo.dao.TicketDao;
import io.hari.demo.dao.VehicleDao;
import io.hari.demo.entity.*;
import io.hari.demo.service.SlotService;
import io.hari.demo.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	AppConfig config;
	@Autowired
	VehicleDao vehicleDao;
	@Autowired
	SlotService slotService;
	@Autowired
	ParkingLotDao parkingLotDao;
	@Autowired
	TicketService ticketService;
	@Autowired
	TicketDao ticketDao;

	@Override
	public void run(String... args) throws Exception {
		final Slot slot1 = Slot.builder().status("available").build();
		final Slot slot2 = Slot.builder().status("available").build();
		final Slot slot3 = Slot.builder().status("available").build();
		final Slot slot4 = Slot.builder().status("available").build();
//		slotDao.save(slot1);
//		slotDao.save(slot2);
//		slotDao.save(slot3);
//		slotDao.save(slot4);//comment then add cascade in parking log one to many mapping

		parkingLotDao.save(ParkingLot.builder().type("ground_floor")
				.slots(Arrays.asList(slot1, slot2, slot3, slot4)).build());


		final Vehicle vehicle1 = Vehicle.builder().registrationNum("1234").color(VehicleColor.red).build();
		final Vehicle vehicle2 = Vehicle.builder().registrationNum("5678").color(VehicleColor.white).build();
//		vehicleDao.save(vehicle1);
//		vehicleDao.save(vehicle2);//this is run time so not required db call save via other entity that it belongs,
//		comment then add cascade all

		//M1: get slots by parking lot - not direclty, if we want ground floor slots then we cant get via slotdao
//		final List<Slot> availableSlot = slotService.getAvailableSlot();
//		System.out.println("availableSlot = " + availableSlot);

		//M2: find all ground floor parting slots
		final Optional<ParkingLot> parkingLot = parkingLotDao.findById(1L);
		final List<Slot> availableSlot = parkingLot.get().getParkingSlots();
		System.out.println("ground floor parkingSlots = " + availableSlot);

		//1 use case - pending min number slot booking
		ticketService.bookTicket(vehicle1);
		ticketService.bookTicket(vehicle2);
		ticketService.bookTicket(Vehicle.builder().registrationNum("9999").color(VehicleColor.black).build());//run time vehicle obj,

		//2 use case
		final List<Slot> slotForColor = ticketService.findSlotForColor(VehicleColor.red);
		System.out.println("slotForColor = " + slotForColor);

		//use case 3 : slot to vehicle
		final Long slotId = Long.valueOf(1);
		final Ticket ticket = ticketDao.findSlotVehicle(slotId);
		final Vehicle vehicle = ticket.getVehicle();
		System.out.println("vehicle = " + vehicle);

		//use case 3 : m2
		final List<Vehicle> slotVehicle2 = vehicleDao.findSlotVehicle2(slotId);
		System.out.println("slotVehicle2 = " + slotVehicle2);

		//use case 4 : free slots for ticket 1 and 3
		ticketService.freeSlot(1L);
		ticketService.freeSlot(3L);
		//todo : ticket will create at first time when user enters with status TICKET_TEMP,
		// and store info of parking lot id(from parking lot obj), slot id (from slot obj) and vehicle obj
		//when user exit and paid the money then change the ticket status to TICKET_DONE
		//and using parking lot id + slot id go to slot_lock table and mark the status of that slot available
		// (use same concept as book my show)
		//TODO OR we can create a DAO SQL in spot dao class like see below
//		@Query(value = "select * from seats where id in (select seat_id from seat_lock where show_id = :showId and lock_status = 'available')",
//				nativeQuery = true)
//		List<Seat> findAllAvailableShowSeats(Long showId);

		final List<Slot> slots = slotService.getAvailableSlot();
		System.out.println("slots = " + slots);
	}
}
