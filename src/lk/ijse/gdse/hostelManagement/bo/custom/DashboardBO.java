package lk.ijse.gdse.hostelManagement.bo.custom;

import lk.ijse.gdse.hostelManagement.bo.SuperBO;

public interface DashboardBO extends SuperBO {

    String studentCount();
    int roomCount();
    String reservationCount();
}
