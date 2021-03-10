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

import java.util.Arrays;
import java.util.List;

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

		final List<Slot> availableSlot = slotService.getAvailableSlot();
		System.out.println("availableSlot = " + availableSlot);

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

		final List<Slot> slots = slotService.getAvailableSlot();
		System.out.println("slots = " + slots);
	}
}
