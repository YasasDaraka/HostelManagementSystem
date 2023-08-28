package lk.ijse.gdse.hostelManagement.bo.custom.impl;
import lk.ijse.gdse.hostelManagement.bo.custom.ReservationBO;
import lk.ijse.gdse.hostelManagement.config.SessionFactoryConfig;
import lk.ijse.gdse.hostelManagement.dao.DAOFactory;
import lk.ijse.gdse.hostelManagement.dao.custom.ReservationDAO;
import lk.ijse.gdse.hostelManagement.dao.custom.RoomDAO;
import lk.ijse.gdse.hostelManagement.dao.custom.StudentDAO;
import lk.ijse.gdse.hostelManagement.dto.ReservationDTO;
import lk.ijse.gdse.hostelManagement.dto.RoomDTO;
import lk.ijse.gdse.hostelManagement.dto.StudentDTO;
import lk.ijse.gdse.hostelManagement.entity.Reservation;
import lk.ijse.gdse.hostelManagement.entity.Room;
import lk.ijse.gdse.hostelManagement.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ReservationBOImpl implements ReservationBO {
    private Session session;
    ReservationDAO reservationDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);
    StudentDAO studentDAO= DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    RoomDAO roomDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);

    @Override
    public String loadResId() {
    try {
        session= SessionFactoryConfig.getInstance().getSession();
        reservationDAO.setSession (session);
        Query query = reservationDAO.loadResId();

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
            session.close();
            return id;
        }
        }catch(Exception e) {
        e.printStackTrace();
        } finally {
        session.close();
    }
        return null;
    }

    @Override
    public List<String> getRoomIds() {
    try {
        session= SessionFactoryConfig.getInstance().getSession();
        roomDAO.setSession (session);
        List<String> list = roomDAO.getIds();
        if(list != null) {
            return list;
        }
        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<String> getStIds() {
    try {
        session= SessionFactoryConfig.getInstance().getSession();
        studentDAO.setSession (session);
        List<String> list = studentDAO.getIds();
        if(list != null) {
            return list;
        }
        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;

    }

    @Override
    public StudentDTO getStudent(String id) throws Exception {
    try {
        session = SessionFactoryConfig.getInstance().getSession();
        studentDAO.setSession(session);
        Student student = (Student) studentDAO.get(id);
        session.close();
        if (student != null) {
            return student.toDto();
        }
        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public RoomDTO getRoom(String id) throws Exception {
     try {
         session = SessionFactoryConfig.getInstance().getSession();
         roomDAO.setSession(session);
         Room room = (Room) roomDAO.get(id);
         session.close();
         if (room != null) {
             return room.toDto();
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
    public boolean saveRes(ReservationDTO dto) throws Exception {
        session= SessionFactoryConfig.getInstance ().getSession ();
       Session sessionUpdate= SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();
        Transaction updateTrans=sessionUpdate.beginTransaction ();

        try {
            reservationDAO.setSession(session);
           String isRes = reservationDAO.save(dto.toEntity());
            transaction.commit();
            if (isRes != null) {
                roomDAO.setSession(sessionUpdate);
                Room room = (Room) roomDAO.get(dto.getRoomDTO().getRoomId());
                room.setQty (room.getQty() - 1);
                roomDAO.update(room);
                updateTrans.commit();
                return true;
            }else{
                transaction.rollback();
                return false;
            }
        }catch (Exception e) {
            transaction.rollback();
            updateTrans.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
            sessionUpdate.close();
        }
    }

    @Override
    public boolean updateRes(ReservationDTO dto) {
        session=SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();
        try {
            reservationDAO.setSession (session);
            reservationDAO.update (dto.toEntity());
            transaction.commit ();
            session.close ();
            return true;
        }catch (Exception e){
            transaction.rollback ();
            e.printStackTrace();
        }finally {
            session.close ();
        }
        return false;
    }

    @Override
    public boolean deleteRes(ReservationDTO dto) {
        session=SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();

        Session sessionUpdate= SessionFactoryConfig.getInstance ().getSession ();
        Transaction updateTrans=sessionUpdate.beginTransaction ();
        try{
            reservationDAO.setSession (session);
            reservationDAO.delete (dto.toEntity());
            transaction.commit ();
            session.close ();

            roomDAO.setSession(sessionUpdate);
            Room newRoom = (Room) roomDAO.get(dto.getRoomDTO().getRoomId());
            newRoom.setQty (newRoom.getQty() + 1);
            roomDAO.update(newRoom);
            updateTrans.commit();

            return true;
        }catch (Exception e){
            transaction.rollback ();
            updateTrans.rollback();
            e.printStackTrace();
        }finally {
            session.close();
            sessionUpdate.close();
        }
        return false;
    }
    @Override
    public boolean checkRoom(String resId,String roomId) {
        session=SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();
        try{
            reservationDAO.setSession(session);
            boolean room = roomDAO.checkRoom(resId,roomId);
            transaction.commit ();
            session.close ();
            return room;
        }catch (Exception e){
            transaction.rollback ();
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean checkStudent(String id) {
        session=SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();
        try{
            reservationDAO.setSession(session);
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
    @Override
    public int roomQty(String id) {
        session=SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();
        try{
            roomDAO.setSession(session);
            String roomQty = roomDAO.roomQty(id);
            transaction.commit ();
            session.close ();
            int qty = 0;
            if (roomQty != null){
                qty = Integer.parseInt(roomQty);
            }
            return qty;
        }catch (Exception e){
            transaction.rollback ();
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean updateWithRoom(String room, String roomId, ReservationDTO updateRes) {
        session= SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();

        Session sessionUpdate= SessionFactoryConfig.getInstance ().getSession ();
        Transaction updateTrans=sessionUpdate.beginTransaction ();

        Session secondUpdate= SessionFactoryConfig.getInstance ().getSession ();
        Transaction secondUpdateTrans=secondUpdate.beginTransaction ();

        try {
            reservationDAO.setSession(session);
            reservationDAO.update(updateRes.toEntity());
            transaction.commit();

                roomDAO.setSession(sessionUpdate);
                Room newRoom = (Room) roomDAO.get(room);
                newRoom.setQty (newRoom.getQty() - 1);
                roomDAO.update(newRoom);
                updateTrans.commit();

                roomDAO.setSession(secondUpdate);
                Room oldRoom = (Room) roomDAO.get(roomId);
                oldRoom.setQty (oldRoom.getQty() + 1);
                roomDAO.update(oldRoom);
                secondUpdateTrans.commit();
                return true;

        }catch (Exception e) {
            transaction.rollback();
            updateTrans.rollback();
            secondUpdateTrans.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
            sessionUpdate.close();
            secondUpdate.close();
        }
    }

    @Override
    public boolean checkStudentWithMiss(String id, String resId) {
        session=SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();
        try{
            reservationDAO.setSession(session);
            boolean st = studentDAO.checkStudentWithMiss(id,resId);
            transaction.commit ();
            session.close ();
            return st;
        }catch (Exception e){
            transaction.rollback ();
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public List<ReservationDTO> loadAll() {
        session=SessionFactoryConfig.getInstance ().getSession ();

        reservationDAO.setSession (session);
        List<Reservation>list= reservationDAO. loadAll ();
        List<ReservationDTO>resList= new ArrayList<>();
        for (Reservation res :list) {
            resList.add(res.toDto());
        }
        return resList;
    }

}
