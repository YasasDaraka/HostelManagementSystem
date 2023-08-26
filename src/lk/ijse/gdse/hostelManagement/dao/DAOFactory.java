package lk.ijse.gdse.hostelManagement.dao;

import lk.ijse.gdse.hostelManagement.dao.custom.impl.StudentDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory(): daoFactory;
    }

    public enum DAOTypes{
        STUDENT,ROOM,RESERVATION
    }
    public <T extends SuperDAO> T getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case STUDENT:
                return (T) new StudentDAOImpl();
            default:
                return null;
        }
    }
}