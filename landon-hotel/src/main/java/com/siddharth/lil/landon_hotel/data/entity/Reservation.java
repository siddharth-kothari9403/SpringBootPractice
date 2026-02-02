package com.siddharth.lil.landon_hotel.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.sql.Date;

@Data
@ToString
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @Column(name = "res_date")
    private Date reservationDate;
}
