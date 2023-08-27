package lk.ijse.gdse.hostelManagement.bo.custom.impl;
import lk.ijse.gdse.hostelManagement.bo.custom.ReservationBO;
import lk.ijse.gdse.hostelManagement.config.SessionFactoryConfig;
import lk.ijse.gdse.hostelManagement.dao.DAOFactory;
import lk.ijse.gdse.hostelManagement.dao.custom.ReservationDAO;
import lk.ijse.gdse.hostelManagement.dao.custom.RoomDAO;
import lk.ijse.gdse.hostelManagement.dao.custom.StudentDAO;
import org.hibernate.Session;

import java.util.List;

public class ReservationBOImpl implements ReservationBO {
    private Session session;
    ReservationDAO reservationDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);
    StudentDAO studentDAO= DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    RoomDAO roomDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    @Override
    public String loadResId() {
        session= SessionFactoryConfig.getInstance().getSession();
        reservationDAO.setSession (session);
        String id = String.valueOf(reservationDAO.loadResId());
        session.close();
        if(id != null) {
            return id;
        }
        return null;
    }
    @Override
    public List<String> getRoomIds() {
        session= SessionFactoryConfig.getInstance().getSession();
        roomDAO.setSession (session);
        List<String> list = roomDAO.getIds();
        if(list != null) {
            return list;
        }
        return null;

    }
    @Override
    public List<String> getStIds() {

        session= SessionFactoryConfig.getInstance().getSession();
        studentDAO.setSession (session);
        List<String> list = studentDAO.getIds();
        if(list != null) {
            return list;
        }
        return null;

    }
}
