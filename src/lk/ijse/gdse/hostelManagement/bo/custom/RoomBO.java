package lk.ijse.gdse.hostelManagement.bo.custom;

import lk.ijse.gdse.hostelManagement.bo.SuperBO;
import lk.ijse.gdse.hostelManagement.dto.RoomDTO;

import java.util.List;

public interface RoomBO extends SuperBO {

    boolean saveRoom(RoomDTO dto);

    boolean updateRoom(RoomDTO dto);

    boolean deleteRoom(RoomDTO dto);

    List<RoomDTO> loadAll();

    RoomDTO getRoom(String id) throws Exception;
}
