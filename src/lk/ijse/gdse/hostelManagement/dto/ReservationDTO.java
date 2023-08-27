package lk.ijse.gdse.hostelManagement.dto;
import lk.ijse.gdse.hostelManagement.entity.Reservation;
import lk.ijse.gdse.hostelManagement.entity.Room;
import lk.ijse.gdse.hostelManagement.entity.Student;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationDTO {

        private String resId;
        private String studentId;
        private String roomId;
        private String status;

    public Reservation toEntity() {
        Reservation reservation = new Reservation();
        reservation.setResId(this.resId);
        reservation.setStudent(new Student(this.studentId));
        reservation.setRoom(new Room(this.roomId));
        reservation.setStatus(this.status);
        return reservation;
    }
}
