package lk.ijse.gdse.hostelManagement.bo;

import lk.ijse.gdse.hostelManagement.bo.custom.impl.ReservationBOImpl;
import lk.ijse.gdse.hostelManagement.bo.custom.impl.ReservtionInfoBOImpl;
import lk.ijse.gdse.hostelManagement.bo.custom.impl.RoomBOImpl;
import lk.ijse.gdse.hostelManagement.bo.custom.impl.StudentBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {
    }

    public static BOFactory  getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        STUDENT,ROOM,RESERVATION,RESERVATION_INFO
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
            default:
                return null;
        }
    }
}
