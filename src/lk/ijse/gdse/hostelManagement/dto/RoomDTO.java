package lk.ijse.gdse.hostelManagement.dto;

import lk.ijse.gdse.hostelManagement.entity.Room;
import lk.ijse.gdse.hostelManagement.entity.Student;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoomDTO {

    private String roomId;
    private String type;
    private String keyMoney;
    private int qty;

    public Room toEntity() {
        Room room = new Room();
        room.setRoomId(this.roomId);
        room.setType(this.type);
        room.setKeyMoney(this.keyMoney);
        room.setQty(this.qty);
        return room;
    }
}
