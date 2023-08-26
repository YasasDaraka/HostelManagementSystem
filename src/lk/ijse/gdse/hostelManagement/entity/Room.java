package lk.ijse.gdse.hostelManagement.entity;
import lk.ijse.gdse.hostelManagement.dto.RoomDTO;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "room_id")
    private String roomId;
    @Column(name = "type")
    private String type;
    @Column(name = "key_money")
    private String keyMoney;
    @Column(name = "qty")
    private int qty;

    public RoomDTO toDto() {
        RoomDTO room = new RoomDTO();
        room.setRoomId(this.roomId);
        room.setType(this.type);
        room.setKeyMoney(this.keyMoney);
        room.setQty(this.qty);
        return room;
    }
}
