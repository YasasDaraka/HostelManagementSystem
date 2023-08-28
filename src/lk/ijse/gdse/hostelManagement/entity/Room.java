package lk.ijse.gdse.hostelManagement.entity;
import lk.ijse.gdse.hostelManagement.dto.RoomDTO;
import lombok.*;
import javax.persistence.*;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "room")
    private List<Reservation> reservationList;

    public Room(String roomId) {
        this.roomId=roomId;
    }

    public RoomDTO toDto() {
        RoomDTO room = new RoomDTO();
        room.setRoomId(this.roomId);
        room.setType(this.type);
        room.setKeyMoney(this.keyMoney);
        room.setQty(this.qty);
        return room;
    }
}
