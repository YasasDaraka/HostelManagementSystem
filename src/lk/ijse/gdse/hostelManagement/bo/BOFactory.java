package lk.ijse.gdse.hostelManagement.bo;

import lk.ijse.gdse.hostelManagement.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {
    }

    public static BOFactory  getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        STUDENT,ROOM,RESERVATION,RESERVATION_INFO,USER,DASHBOARD
    }

    public <T extends SuperBO> T getBO(BOTypes boTypes){
        switch (boTypes){
            case STUDENT:
                return (T) new StudentBOImpl();
            case ROOM:
                return (T) new RoomBOImpl();
            case RESERVATION:
                return (T) new ReservationBOImpl();
            case RESERVATION_INFO:
                return (T) new ReservtionInfoBOImpl();
            case USER:
                return (T) new UserBOImpl();
            case DASHBOARD:
                return (T) new DashboardBOImpl();
            default:
                return null;
        }
    }
}
