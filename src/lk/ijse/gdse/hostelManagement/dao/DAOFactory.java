package lk.ijse.gdse.hostelManagement.dao;

import lk.ijse.gdse.hostelManagement.dao.custom.impl.QueryDAOImpl;
import lk.ijse.gdse.hostelManagement.dao.custom.impl.ReservationDAOImpl;
import lk.ijse.gdse.hostelManagement.dao.custom.impl.RoomDAOImpl;
import lk.ijse.gdse.hostelManagement.dao.custom.impl.StudentDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory(): daoFactory;
    }

    public enum DAOTypes{
        STUDENT,ROOM,RESERVATION,QUERY
    }
    public <T extends SuperDAO> T getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case STUDENT:
                return (T) new StudentDAOImpl();
            case ROOM:
                return (T) new RoomDAOImpl();
            case RESERVATION:
                return (T) new ReservationDAOImpl();
            case QUERY:
                return (T) new QueryDAOImpl();

            default:
                return null;
        }
    }
}
