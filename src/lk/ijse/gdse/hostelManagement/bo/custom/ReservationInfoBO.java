package lk.ijse.gdse.hostelManagement.bo.custom;

import lk.ijse.gdse.hostelManagement.bo.SuperBO;
import lk.ijse.gdse.hostelManagement.dto.ReservationDTO;
import lk.ijse.gdse.hostelManagement.dto.ReservationProDTO;
import lk.ijse.gdse.hostelManagement.dto.tm.ReservationProTM;
import lk.ijse.gdse.hostelManagement.projection.ReservationProjection;

import java.util.List;

public interface ReservationInfoBO extends SuperBO {

    List<ReservationProDTO> loadAll(String id);
    ReservationDTO getRes(String id) throws Exception;
    List<ReservationProDTO> checkInfo(String id) throws Exception;
    boolean checkStudent(String id);
}
