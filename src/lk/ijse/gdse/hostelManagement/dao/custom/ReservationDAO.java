package lk.ijse.gdse.hostelManagement.dao.custom;

import lk.ijse.gdse.hostelManagement.dao.CrudDAO;
import lk.ijse.gdse.hostelManagement.entity.Reservation;

import java.util.List;

public interface ReservationDAO<T,ID> extends CrudDAO<Reservation,String> {
    ID loadResId();

}