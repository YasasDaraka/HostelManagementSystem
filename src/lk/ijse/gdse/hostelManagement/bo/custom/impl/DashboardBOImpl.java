package lk.ijse.gdse.hostelManagement.bo.custom.impl;

import lk.ijse.gdse.hostelManagement.bo.custom.DashboardBO;
import lk.ijse.gdse.hostelManagement.config.SessionFactoryConfig;
import lk.ijse.gdse.hostelManagement.dao.DAOFactory;
import lk.ijse.gdse.hostelManagement.dao.custom.ReservationDAO;
import lk.ijse.gdse.hostelManagement.dao.custom.RoomDAO;
import lk.ijse.gdse.hostelManagement.dao.custom.StudentDAO;
import org.hibernate.Session;

public class DashboardBOImpl implements DashboardBO {
    private Session session;
    StudentDAO studentDAO= DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    RoomDAO roomDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    ReservationDAO reservationDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);
    @Override
    public String studentCount() {
        try{
            session= SessionFactoryConfig.getInstance ().getSession ();
            studentDAO.setSession (session);
            String student= studentDAO.studentCount();
            session.close();
                return student;
        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public int roomCount() {
        try{
            session= SessionFactoryConfig.getInstance ().getSession ();
            roomDAO.setSession (session);
            int room= roomDAO.roomCount();
            session.close();
            return room;
        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return 0;
    }

    @Override
    public String reservationCount() {
        try{
            session= SessionFactoryConfig.getInstance ().getSession ();
            reservationDAO.setSession (session);
            String student= reservationDAO.reservationCount();
            session.close();
            return student;
        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
