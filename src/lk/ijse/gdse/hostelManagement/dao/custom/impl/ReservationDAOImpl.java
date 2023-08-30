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
        System.out.println(object.getStudent().getStId());
        System.out.println(object.getRoom().getRoomId());
        return String.valueOf(session.save(object));
    }

    @Override
    public void update(Reservation object) {
        session.update (object);
    }

    @Override
    public void delete(Reservation object) {
        session.delete (object);
    }

    @Override
    public Reservation get(String id) throws Exception {
        return session.get(Reservation.class,id);
    }

    @Override
    public List<Reservation> loadAll() {
        String sqlQuery="FROM Reservation";
        Query query = session.createQuery(sqlQuery);
        List list =query.list ();
        session.close();
        return list;
    }

    @Override
    public List<String> getIds() {
        return null;
    }

    @Override
    public Query loadResId() {
        Query query = session.createQuery ("select resId from Reservation order by resId desc");
        return query;
    }

}
