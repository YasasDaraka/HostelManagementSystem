package lk.ijse.gdse.hostelManagement.bo.custom;

import lk.ijse.gdse.hostelManagement.bo.SuperBO;
import lk.ijse.gdse.hostelManagement.dto.ReservationDTO;
import lk.ijse.gdse.hostelManagement.dto.RoomDTO;
import lk.ijse.gdse.hostelManagement.dto.StudentDTO;

import java.util.List;

public interface ReservationBO extends SuperBO {

     String loadResId();

     List<String> getRoomIds();

     List<String> getStIds();

     StudentDTO getStudent(String id) throws Exception;

     RoomDTO getRoom(String id) throws Exception;

     ReservationDTO getRes(String id) throws Exception;

     boolean saveRes(ReservationDTO dto) throws Exception;

     boolean updateRes(ReservationDTO dto);

     boolean deleteRes(ReservationDTO dto);

     boolean checkRoom(String resId,String roomId);

     boolean checkStudent(String id);

     int roomQty(String id);

     boolean updateWithRoom(String room, String roomId, ReservationDTO updateRes);

     boolean checkStudentWithMiss(String id ,String resId);

     List<ReservationDTO> loadAll();
}
