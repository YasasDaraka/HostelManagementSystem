package lk.ijse.gdse.hostelManagement.dao.custom.impl;

import lk.ijse.gdse.hostelManagement.dao.custom.QueryDAO;
import lk.ijse.gdse.hostelManagement.projection.ReservationProjection;
import org.hibernate.Session;
import javax.persistence.NoResultException;
import java.util.List;

public class QueryDAOImpl implements QueryDAO<ReservationProjection,String> {
    private Session session;


    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public List<ReservationProjection> loadAllInfo(String status) {

        String hql = "SELECT new lk.ijse.gdse.hostelManagement.projection.ReservationProjection" +
                "(r.resId, s.stId, s.stName, ro.roomId, ro.type, ro.keyMoney, r.status,s.stContact,s.stAddress) " +
                "FROM Reservation r " +
                "JOIN r.student s " +
                "JOIN r.room ro " +
                "WHERE r.status = :statusParam";

        List<ReservationProjection> resultList = session.createQuery(hql, ReservationProjection.class)
                .setParameter("statusParam", status)
                .getResultList();

        return resultList;
    }

    @Override
    public ReservationProjection checkInfo(String id) {

        String hql = "SELECT new lk.ijse.gdse.hostelManagement.projection.ReservationProjection" +
                "(r.resId, s.stId, s.stName, ro.roomId, ro.type, ro.keyMoney, r.status,s.stContact,s.stAddress) " +
                "FROM Reservation r " +
                "JOIN r.student s " +
                "JOIN r.room ro " +
                "WHERE s.stId = :statusParam";

        try {
            ReservationProjection result = session.createQuery(hql, ReservationProjection.class)
                    .setParameter("statusParam", id)
                    .getSingleResult();
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
