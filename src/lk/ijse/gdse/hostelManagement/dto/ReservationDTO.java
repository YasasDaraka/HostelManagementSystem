package lk.ijse.gdse.hostelManagement.dto;
import lk.ijse.gdse.hostelManagement.dto.tm.ReservationTM;
import lk.ijse.gdse.hostelManagement.entity.Reservation;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationDTO {

        private String resId;
        private StudentDTO studentDTO;
        private RoomDTO roomDTO;
        private String status;
        private Date date;


    public Reservation toEntity() {
        Reservation reservation = new Reservation();
        reservation.setResId(this.resId);
        reservation.setStudent(this.studentDTO.toEntity());
        reservation.setRoom(this.roomDTO.toEntity());
        reservation.setStatus(this.status);
        reservation.setDate(this.date);
        return reservation;
    }

    public ReservationTM toTM() {
        ReservationTM reservation = new ReservationTM();
        reservation.setResId(this.resId);
        reservation.setStId(this.studentDTO.getStId());
        reservation.setStName(this.studentDTO.getStName());
        reservation.setRoomId(this.roomDTO.getRoomId());
        reservation.setRoomType(this.roomDTO.getType());
        reservation.setStatus(this.status);
        return reservation;
    }
}
