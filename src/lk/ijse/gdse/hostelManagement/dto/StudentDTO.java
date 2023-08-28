package lk.ijse.gdse.hostelManagement.dto;
import lk.ijse.gdse.hostelManagement.dto.tm.StudentTM;
import lk.ijse.gdse.hostelManagement.entity.Student;
import lombok.*;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentDTO {

    private String stId;
    private String stName;
    private String stAddress;
    private String stContact;
    private Date dob;
    private String gender;


    public Student toEntity() {
        Student student = new Student();
        student.setStId(this.stId);
        student.setStName(this.stName);
        student.setStAddress(this.stAddress);
        student.setStContact(this.stContact);
        student.setDob(this.dob);
        student.setGender(this.gender);
        return student;
    }

    public StudentTM toTM() {
        StudentTM studentTM = new StudentTM();
        studentTM.setStId(this.stId);
        studentTM.setStName(this.stName);
        studentTM.setStAddress(this.stAddress);
        studentTM.setStContact(this.stContact);
        studentTM.setDob(this.dob);
        studentTM.setGender(this.gender);
        return studentTM;
    }
}
