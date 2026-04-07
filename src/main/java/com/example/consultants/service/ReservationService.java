package com.example.consultants.service;

import com.example.consultants.mapper.ReservationMapper;
import com.example.consultants.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    public void insert(Reservation reservation) {
        reservationMapper.insert(reservation);
    }

    public Reservation findByPhone(String phone) {
        return reservationMapper.findByPhone(phone);
    }
}
