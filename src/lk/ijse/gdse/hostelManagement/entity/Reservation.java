package lk.ijse.gdse.hostelManagement.entity;

import lk.ijse.gdse.hostelManagement.dto.ReservationDTO;
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
    @JoinColumn(name = "student_id",nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name ="room_id",nullable = false)
    private Room room;

    @Column(name = "status")
    private String status;

    public ReservationDTO toDto() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setResId(this.resId);
        reservationDTO.setStudentDTO(this.student.toDto());
        reservationDTO.setRoomDTO(this.room.toDto());
        reservationDTO.setStatus(this.status);
        reservationDTO.setDate(this.date);
        return reservationDTO;
    }
}
