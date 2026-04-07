package com.example.consultants.mapper;

import com.example.consultants.entity.Reservation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReservationMapper {

    @Insert("INSERT INTO reservation(name, gender, phone, communication_time, province, estimated_score) " +
            "VALUES(#{name}, #{gender}, #{phone}, #{communicationTime}, #{province}, #{estimatedScore})")
    void insert(Reservation reservation);

    @Select("SELECT * FROM reservation WHERE phone = #{phone}")
    Reservation findByPhone(String phone);
}
