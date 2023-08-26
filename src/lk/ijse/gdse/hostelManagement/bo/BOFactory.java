package lk.ijse.gdse.hostelManagement.bo;

import lk.ijse.gdse.hostelManagement.bo.custom.impl.StudentBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {
    }

    public static BOFactory  getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        STUDENT,ROOM,RESERVATION
    }

    public <T extends SuperBO> T getBO(BOTypes boTypes){
        switch (boTypes){
            case STUDENT:
                return (T) new StudentBOImpl();
            default:
                return null;
        }
    }
}
