package lk.ijse.gdse.hostelManagement.bo.custom;
import lk.ijse.gdse.hostelManagement.bo.SuperBO;
import lk.ijse.gdse.hostelManagement.dto.RoomDTO;
import lk.ijse.gdse.hostelManagement.dto.UserDTO;

import java.util.List;

public interface UserBO extends SuperBO {
    boolean saveUser(UserDTO dto);

    UserDTO getUser(String id) throws Exception;

    List<UserDTO> loadAll();

    boolean updateUser(UserDTO dto);

    boolean deleteUser(UserDTO dto);
}
