package lk.ijse.gdse.hostelManagement.projection;

import lk.ijse.gdse.hostelManagement.dto.ReservationProDTO;
import lk.ijse.gdse.hostelManagement.dto.tm.ReservationProTM;
import lk.ijse.gdse.hostelManagement.dto.tm.ReservationTM;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationProjection {
    private String resId;
    private String studentId;
    private String studentName;
    private String roomId;
    private String roomType;
    private String keyMoney;
    private String status;
    private String contact;
    private String address;

    public ReservationProDTO toDto() {
        ReservationProDTO reservation = new ReservationProDTO();
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
