package io.hari.demo.service;

import io.hari.demo.dao.SlotDao;
import io.hari.demo.dao.TicketDao;
import io.hari.demo.entity.Slot;
import io.hari.demo.entity.Ticket;
import io.hari.demo.entity.Vehicle;
import io.hari.demo.entity.VehicleColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * @Author Hariom Yadav
 * @create 08-03-2021
 */
@Service
public class TicketService {

    @Autowired
    TicketDao ticketDao;

    @Autowired
    SlotService slotService;

    @Autowired
    SlotDao slotDao;

    public List<Slot> findSlotForColor(VehicleColor color) {
        final List<Ticket> tickets = ticketDao.findAllByVehicle_Color(color);
        final List<Slot> slots = tickets.stream().map(i -> i.getSlot()).collect(Collectors.toList());
        return slots;
    }

    public void bookTicket(Vehicle vehicle) {
        final List<Slot> availableSlot = slotService.getAvailableSlot();

        PriorityQueue<Slot> priorityQueue = new PriorityQueue<>(Comparator.comparing(Slot::getId));//1,2,3,4, natural order
//        PriorityQueue<Slot> priorityQueue = new PriorityQueue<>(Comparator.comparing(Slot::getId).reversed());//4,3,2,1
        availableSlot.forEach(i -> priorityQueue.add(i));
        System.out.println("priorityQueue = " + priorityQueue);

        //print priorityQueue and see output
//        while (!priorityQueue.isEmpty()) {
//            final Slot poll = priorityQueue.poll();
//            System.out.println("poll.getId() = " + poll.getId());
//        }

        final Optional<Slot> first = priorityQueue.stream().findFirst();
        first.ifPresent(i -> {
            final Slot i1 = i;
            final Ticket build = Ticket.builder().slot(i1).vehicle(vehicle).build();
            ticketDao.save(build);
            i1.setStatus("booked");
            slotDao.save(i1);
        });
    }

    public void freeSlot(Long ticketId) {
          //get slot -> set available
        final Optional<Ticket> byId = ticketDao.findById(ticketId);
        byId.ifPresent(i -> {
            final Ticket ticket = i;
            final Slot slot = ticket.getSlot();//no need to fetch from db again with id coz fetch type is eager
            slot.setStatus("available");
            slotDao.save(slot);
        });
    }
}
