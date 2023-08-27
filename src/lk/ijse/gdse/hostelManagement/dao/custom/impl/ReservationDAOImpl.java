package lk.ijse.gdse.hostelManagement.dao.custom.impl;

import lk.ijse.gdse.hostelManagement.dao.custom.ReservationDAO;
import lk.ijse.gdse.hostelManagement.entity.Reservation;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ReservationDAOImpl implements ReservationDAO<Reservation,String> {
    private Session session;
    @Override
    public void setSession(Session session) {
        this.session=session;
    }

    @Override
    public String save(Reservation object) {
        return null;
    }

    @Override
    public void update(Reservation object) {

    }

    @Override
    public void delete(Reservation object) {

    }

    @Override
    public Reservation get(String s) throws Exception {
        return null;
    }

    @Override
    public List loadAll() {
        return null;
    }

    @Override
    public List<String> getIds() {
        return null;
    }

    @Override
    public String loadResId() {

        Query query = session.createQuery ("select resId from Reservation order by resId desc");
        String nextId = "R001";
        if (query.list ().size () == 0) {
            return nextId;
        } else {
            String id = (String) query.list ().get (0);
            String[] SUs = id.split ("R00");
            for (String a : SUs) {
                id = a;
            }
            int idNum = Integer.parseInt (id);
            id = "R00" + (idNum + 1);
            return id;
        }
    }
}
