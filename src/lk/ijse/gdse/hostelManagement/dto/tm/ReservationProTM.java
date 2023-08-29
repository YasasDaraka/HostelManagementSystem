package lk.ijse.gdse.hostelManagement.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ReservationProTM {
    private String resId;
    private String studentId;
    private String studentName;
    private String roomId;
    private String roomType;
    private String keyMoney;
    private String status;
    private String contact;
    private String address;
}
