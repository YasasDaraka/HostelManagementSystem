package lk.ijse.gdse.hostelManagement.dto;

import lk.ijse.gdse.hostelManagement.dto.tm.RoomTM;
import lk.ijse.gdse.hostelManagement.entity.Room;
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
    public RoomTM toTM() {
        RoomTM roomTM = new RoomTM();
        roomTM.setRoomId(this.roomId);
        roomTM.setType(this.type);
        roomTM.setKeyMoney(this.keyMoney);
        roomTM.setQty(this.qty);
        return roomTM;
    }
}
