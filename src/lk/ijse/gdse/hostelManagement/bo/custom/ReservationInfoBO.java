package lk.ijse.gdse.hostelManagement.bo.custom;

import lk.ijse.gdse.hostelManagement.bo.SuperBO;
import lk.ijse.gdse.hostelManagement.dto.ReservationDTO;
import lk.ijse.gdse.hostelManagement.dto.ReservationProDTO;
import lk.ijse.gdse.hostelManagement.dto.tm.ReservationProTM;

import java.util.List;

public interface ReservationInfoBO extends SuperBO {

    List<ReservationProDTO> loadAll(String id);
}
