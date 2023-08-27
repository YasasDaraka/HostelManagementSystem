package lk.ijse.gdse.hostelManagement.bo.custom;

import lk.ijse.gdse.hostelManagement.bo.SuperBO;

import java.util.List;

public interface ReservationBO extends SuperBO {

     String loadResId();
     List<String> getRoomIds();
     List<String> getStIds();
}
