package lk.ijse.gdse.hostelManagement.dto;

import lk.ijse.gdse.hostelManagement.dto.tm.ReservationProTM;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationProDTO {

        private String resId;
        private String studentId;
        private String studentName;
        private String roomId;
        private String roomType;
        private String keyMoney;
        private String status;
        private String contact;
        private String address;

    public ReservationProTM toTM() {
        ReservationProTM reservation = new ReservationProTM();
        reservation.setResId(this.resId);
        reservation.setStudentId(this.studentId);
        reservation.setStudentName(this.studentName);
        reservation.setRoomId(this.roomId);
        reservation.setRoomType(this.roomType);
        reservation.setKeyMoney(this.keyMoney);
        reservation.setStatus(this.status);
        reservation.setContact(this.contact);
        reservation.setAddress(this.address);
        return reservation;
    }
}
