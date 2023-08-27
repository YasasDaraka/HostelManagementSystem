package lk.ijse.gdse.hostelManagement.entity;

import lk.ijse.gdse.hostelManagement.dto.StudentDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "student_id")
    private String stId;
    @Column(name = "name")
    private String stName;
    @Column(name = "address")
    private String stAddress;
    @Column(name = "contact_no")
    private String stContact;
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Column(name = "gender")
    private String gender;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "student")
    private List<Reservation> reservationList;

    public Student(String studentId) {
        this.stId=studentId;
    }

    public StudentDTO toDto() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStId(this.stId);
        studentDTO.setStName(this.stName);
        studentDTO.setStAddress(this.stAddress);
        studentDTO.setStContact(this.stContact);
        studentDTO.setDob(this.dob);
        studentDTO.setGender(this.gender);
        return studentDTO;
    }
}
