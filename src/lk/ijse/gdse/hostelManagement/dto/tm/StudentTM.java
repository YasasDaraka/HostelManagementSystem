package lk.ijse.gdse.hostelManagement.dto.tm;

import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentTM {

    private String stId;
    private String stName;
    private String stAddress;
    private String stContact;
    private Date dob;
    private String gender;
}
