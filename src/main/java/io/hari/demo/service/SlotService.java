package io.hari.demo.service;

import io.hari.demo.dao.SlotDao;
import io.hari.demo.entity.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Hariom Yadav
 * @create 08-03-2021
 */
@Service
public class SlotService {

    @Autowired
    SlotDao slotDao;

    public List<Slot> getAvailableSlot() {
        final List<Slot> list = slotDao.findAll();
        System.out.println("list = " + list);
        final List<Slot> available = list.stream()
                .filter(i -> i.getStatus().equals("available")).collect(Collectors.toList());
        return available;
    }
}
