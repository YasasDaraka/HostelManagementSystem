package lk.ijse.gdse.hostelManagement.dao.custom;

import lk.ijse.gdse.hostelManagement.dao.CrudDAO;
import lk.ijse.gdse.hostelManagement.entity.Room;


public interface RoomDAO<T,ID> extends CrudDAO<Room,String> {
    String roomQty(String id);
    boolean checkRoom(String resId,String roomId);
    int roomCount();
}
