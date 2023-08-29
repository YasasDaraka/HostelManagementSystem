package lk.ijse.gdse.hostelManagement.dao.custom;

import lk.ijse.gdse.hostelManagement.dao.SuperDAO;
import org.hibernate.Session;

import java.util.List;

public interface QueryDAO<T,ID> extends SuperDAO {

    void setSession(Session session);

    List<T> loadAllInfo(String status);
}
