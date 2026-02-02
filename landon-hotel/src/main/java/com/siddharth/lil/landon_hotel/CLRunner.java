package com.siddharth.lil.landon_hotel;

import com.siddharth.lil.landon_hotel.data.entity.Guest;
import com.siddharth.lil.landon_hotel.data.entity.Reservation;
import com.siddharth.lil.landon_hotel.data.entity.Room;
import com.siddharth.lil.landon_hotel.data.repository.GuestRepository;
import com.siddharth.lil.landon_hotel.data.repository.ReservationRepository;
import com.siddharth.lil.landon_hotel.data.repository.RoomRepository;
import com.siddharth.lil.landon_hotel.service.RoomReservationService;
import com.siddharth.lil.landon_hotel.service.model.RoomReservation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CLRunner implements CommandLineRunner {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final RoomReservationService roomReservationService;

    public CLRunner(RoomRepository roomRepository, ReservationRepository reservationRepository, GuestRepository guestRepository, RoomReservationService roomReservationService) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.roomReservationService = roomReservationService;
    }

    @Override
    public void run(String... args) throws Exception {
//        List<Room> rooms = roomRepository.findAll();
//        List<Reservation> reservations = reservationRepository.findAll();
//        List<Guest> guests = guestRepository.findAll();
//        System.out.println("*** ROOMS ***");
//        rooms.forEach(System.out::println);
//        System.out.println("*** GUESTS ***");
//        guests.forEach(System.out::println);
//        System.out.println("*** RESERVATIONS ***");
//        reservations.forEach(System.out::println);

        List<RoomReservation> roomReservations = this.roomReservationService.getRoomReservationsForDate("2023-08-28");
        roomReservations.forEach(System.out::println);
    }
}
