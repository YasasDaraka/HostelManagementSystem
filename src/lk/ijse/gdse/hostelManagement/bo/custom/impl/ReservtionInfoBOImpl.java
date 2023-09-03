package lk.ijse.gdse.hostelManagement.bo.custom.impl;

import lk.ijse.gdse.hostelManagement.bo.custom.ReservationInfoBO;
import lk.ijse.gdse.hostelManagement.config.SessionFactoryConfig;
import lk.ijse.gdse.hostelManagement.dao.DAOFactory;
import lk.ijse.gdse.hostelManagement.dao.custom.QueryDAO;
import lk.ijse.gdse.hostelManagement.dao.custom.ReservationDAO;
import lk.ijse.gdse.hostelManagement.dao.custom.StudentDAO;
import lk.ijse.gdse.hostelManagement.dto.ReservationDTO;
import lk.ijse.gdse.hostelManagement.dto.ReservationProDTO;
import lk.ijse.gdse.hostelManagement.entity.Reservation;
import lk.ijse.gdse.hostelManagement.projection.ReservationProjection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ReservtionInfoBOImpl implements ReservationInfoBO {
    private Session session;
    QueryDAO queryDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);
    ReservationDAO reservationDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);
    StudentDAO studentDAO= DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    @Override
    public List<ReservationProDTO> loadAll(String id) {
        try{
            session=SessionFactoryConfig.getInstance ().getSession ();
            queryDAO.setSession (session);
            List<ReservationProjection> list=queryDAO.loadAllInfo (id);
            List<ReservationProDTO> listDto=new ArrayList<>();
            for (ReservationProjection rs:list) {
                listDto.add(rs.toDto());
            }
            if(listDto != null) {
                return listDto;
            }
        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    @Override
    public ReservationDTO getRes(String id) throws Exception {
        session = SessionFactoryConfig.getInstance().getSession();
        reservationDAO.setSession(session);
        try {
            Reservation reservation = (Reservation) reservationDAO.get(id);
            session.close();
            if (reservation != null) {
                return reservation.toDto();
            }
        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<ReservationProDTO> checkInfo(String id) throws Exception {
        session = SessionFactoryConfig.getInstance().getSession();
        queryDAO.setSession(session);
        try {
            List<ReservationProjection> reservation = queryDAO.checkInfo(id);
            List<ReservationProDTO> listDto=new ArrayList<>();
            session.close();
            for (ReservationProjection rs:reservation) {
                listDto.add(rs.toDto());
            }
            if(listDto != null) {
                return listDto;
            }
        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public boolean checkStudent(String id) {
        session=SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();
        try{
            studentDAO.setSession(session);
            boolean st = studentDAO.checkStudent(id);
            transaction.commit ();
            session.close ();
            return st;
        }catch (Exception e){
            transaction.rollback ();
            e.printStackTrace();
        }
        return false;
    }
}
