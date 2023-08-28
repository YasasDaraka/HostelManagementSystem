package lk.ijse.gdse.hostelManagement.dto;
import lk.ijse.gdse.hostelManagement.entity.Reservation;
import lk.ijse.gdse.hostelManagement.entity.Room;
import lk.ijse.gdse.hostelManagement.entity.Student;
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
}
