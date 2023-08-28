package lk.ijse.gdse.hostelManagement.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoomTM {
    private String roomId;
    private String type;
    private String keyMoney;
    private int qty;
}
