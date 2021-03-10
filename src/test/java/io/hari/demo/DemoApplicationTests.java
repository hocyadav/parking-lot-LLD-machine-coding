package io.hari.demo;

import io.hari.demo.config.AppConfig;
import io.hari.demo.dao.ParkingLotDao;
import io.hari.demo.dao.TicketDao;
import io.hari.demo.dao.VehicleDao;
import io.hari.demo.entity.*;
import io.hari.demo.service.SlotService;
import io.hari.demo.service.TicketService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static io.hari.demo.util.TestUtils.getSlot;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
//@SpringBootTest(classes = {})
class DemoApplicationTests {

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


	@Test
	@Order(1)
	void contextLoads() {
		assertThat("hari om yadav")
				.isNotNull()
				.startsWith("hari")
				.contains("om")
				.endsWith("yadav");
	}

	@Test
	@Order(2)
	public void foo() {
		final Slot slot1 = getSlot("available");
		final Slot slot2 = getSlot("available");
		final Slot slot3 = getSlot("available");
		final Slot slot4 = getSlot("available");

		final List<ParkingLot> PARKING_LOT_BEFORE = parkingLotDao.findAll();
		assertThat(PARKING_LOT_BEFORE.get(0)).isNotNull();//coz in main method we have save some data

		parkingLotDao.save(ParkingLot.builder().type("ground_floor")
				.slots(Arrays.asList(slot1, slot2, slot3, slot4)).build());
		final List<ParkingLot> PARKING_LOT_AFTER = parkingLotDao.findAll();
		System.out.println("PARKING_LOT_AFTER = " + PARKING_LOT_AFTER);
		System.out.println("slots1 = " + PARKING_LOT_AFTER.get(0).getSlots());

		assertThat(PARKING_LOT_AFTER).isNotNull();
		assertThat(PARKING_LOT_AFTER.get(0).getSlots().size())
				.isEqualTo(4);
	}

	@Test
	@Order(3)
	public void foo2() {
		final Vehicle vehicle1 = Vehicle.builder().registrationNum("1234").color(VehicleColor.red).build();
		final Vehicle vehicle2 = Vehicle.builder().registrationNum("5678").color(VehicleColor.white).build();
//		vehicleDao.save(vehicle1);//not required since added ALL cascade
//		vehicleDao.save(vehicle2);

		final List<Slot> slots = slotService.getAvailableSlot();
		System.out.println("slots = " + slots);

		//1 use case - pending min number slot booking
		ticketService.bookTicket(vehicle1);
		ticketService.bookTicket(vehicle2);
		ticketService.bookTicket(Vehicle.builder().registrationNum("9999").color(VehicleColor.black).build());
	}

	@Test
	@Order(4)
	public void useCase2() {
		final List<Slot> slotForColor = ticketService.findSlotForColor(VehicleColor.red);
		System.out.println("slotForColor = " + slotForColor);
	}

	@Test
	@Order(5)
	public void useCase3() {
		//use case 3 : slot to vehicle
		final Long slotId = Long.valueOf(1);
		final Ticket ticket = ticketDao.findSlotVehicle(slotId);
		final Vehicle vehicle = ticket.getVehicle();
		System.out.println("vehicle = " + vehicle);
	}

	@Test
	@Order(6)
	public void useCase4() {
		//use case 3 : m2
		final List<Vehicle> slotVehicle2 = vehicleDao.findSlotVehicle2(1L);
		System.out.println("slotVehicle2 = " + slotVehicle2);

	}

}
