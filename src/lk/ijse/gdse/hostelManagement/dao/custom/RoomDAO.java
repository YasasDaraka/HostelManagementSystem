package lk.ijse.gdse.hostelManagement.dao.custom;

import lk.ijse.gdse.hostelManagement.dao.CrudDAO;
import lk.ijse.gdse.hostelManagement.entity.Room;

import java.util.List;

public interface RoomDAO<T,ID> extends CrudDAO<Room,String> {
    String roomQty(String id);
    boolean checkRoom(String resId,String roomId);

}
