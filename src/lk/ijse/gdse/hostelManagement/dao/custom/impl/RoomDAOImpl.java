package lk.ijse.gdse.hostelManagement.dao.custom.impl;

import lk.ijse.gdse.hostelManagement.dao.custom.RoomDAO;
import lk.ijse.gdse.hostelManagement.entity.Room;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RoomDAOImpl implements RoomDAO<Room,String> {
    private Session session;

    @Override
    public void setSession(Session session) {
            this.session=session;
    }

    @Override
    public String save(Room room) {
        return String.valueOf(session.save(room));
    }

    @Override
    public void update(Room room) {
        session.update (room);
    }

    @Override
    public void delete(Room room) {
        session.delete (room);
    }

    @Override
    public Room get(String id) throws Exception {
        return session.get(Room.class,id);
    }

    @Override
    public List<Room> loadAll() {
        String sql = "SELECT C FROM Room AS C";
        Query query = session.createQuery(sql);
        List list = query.list();
        session.close();
        return list;
    }
    @Override
    public List<String> getIds() {

        String hql = "SELECT roomId from Room ";
        Query<String> query=session.createQuery (hql);
        List<String> results = query.list();
        session.close();
        return results;

    }
    @Override
    public String roomQty(String id){
        String hql = "SELECT r.qty FROM Room r WHERE r.roomId = :roomId";
        Query<Integer> query = session.createQuery(hql, Integer.class);
        query.setParameter("roomId", id);
        Integer quantity = query.uniqueResult();
        return quantity != null ? String.valueOf(quantity) : null;
    }
    @Override
    public boolean checkRoom(String resId,String roomId) {
        String hql = "SELECT COUNT(r) FROM Reservation r WHERE r.resId = :reservationId AND r.room.roomId = :roomId";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("reservationId", resId);
        query.setParameter("roomId", roomId);
        Long count = query.uniqueResult();
        boolean roomExists = count > 0;
        return roomExists;
    }
}
