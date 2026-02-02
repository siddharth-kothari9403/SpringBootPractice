package com.siddharth.lil.landon_hotel.service.model;

import com.siddharth.lil.landon_hotel.data.entity.Guest;
import com.siddharth.lil.landon_hotel.data.entity.Room;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoomReservation {
    private long roomId;
    private String roomName;
    private String roomNumber;
    private long guestId;
    private String firstName;
    private String lastName;
    private long reservationId;
    private String reservationDate;
}
