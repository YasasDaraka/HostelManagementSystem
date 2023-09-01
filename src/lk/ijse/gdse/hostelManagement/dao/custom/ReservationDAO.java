package lk.ijse.gdse.hostelManagement.dao.custom;

import lk.ijse.gdse.hostelManagement.dao.CrudDAO;
import lk.ijse.gdse.hostelManagement.entity.Reservation;
import org.hibernate.query.Query;


public interface ReservationDAO<T,ID> extends CrudDAO<Reservation,String> {

    Query loadResId();

    String reservationCount();

}