package lk.ijse.gdse.hostelManagement.entity;

import lk.ijse.gdse.hostelManagement.dto.ReservationDTO;
import lk.ijse.gdse.hostelManagement.dto.StudentDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @Column(name = "reservation_id")
    private String resId;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name ="room_id")
    private Room room;

    @Column(name = "status")
    private String status;

    public ReservationDTO toDto() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setResId(this.resId);
        reservationDTO.setStudentId(this.student.getStId());
        reservationDTO.setRoomId(this.room.getRoomId());
        reservationDTO.setStatus(this.status);
        return reservationDTO;
    }
}
