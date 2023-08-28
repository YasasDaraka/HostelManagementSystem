package lk.ijse.gdse.hostelManagement.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationTM {

    private String resId;

    private String stId;

    private String stName;

    private String roomId;

    private String roomType;

    private String status;
}
