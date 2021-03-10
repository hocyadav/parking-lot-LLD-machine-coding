package io.hari.demo.util;

import io.hari.demo.entity.Slot;

/**
 * @Author Hariom Yadav
 * @create 08-03-2021
 */
public class TestUtils {

    public static Slot getSlot(String status) {
        return Slot.builder().status(status).build();
    }
}
