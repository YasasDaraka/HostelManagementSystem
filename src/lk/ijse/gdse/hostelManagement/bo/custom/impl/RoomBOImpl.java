package lk.ijse.gdse.hostelManagement.bo.custom.impl;

import lk.ijse.gdse.hostelManagement.bo.custom.RoomBO;
import lk.ijse.gdse.hostelManagement.config.SessionFactoryConfig;
import lk.ijse.gdse.hostelManagement.dao.DAOFactory;
import lk.ijse.gdse.hostelManagement.dao.custom.RoomDAO;
import lk.ijse.gdse.hostelManagement.dto.RoomDTO;
import lk.ijse.gdse.hostelManagement.entity.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class RoomBOImpl implements RoomBO {
    RoomDAO roomDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    private Session session;
    @Override
    public boolean saveRoom(RoomDTO dto) {
        session= SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();
        try{
            roomDAO.setSession(session);
            roomDAO.save (dto.toEntity());
            transaction.commit();
            session.close ();
            return true;
        }catch (Exception e){
            transaction.rollback ();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateRoom(RoomDTO dto) {
        session=SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();
        try {
            roomDAO.setSession (session);
            roomDAO.update (dto.toEntity());
            transaction.commit ();
            session.close ();
            return true;
        }catch (Exception e){
            transaction.rollback ();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteRoom(RoomDTO dto) {
        session=SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();
        try{
            roomDAO.setSession (session);
            roomDAO.delete (dto.toEntity());
            transaction.commit ();
            session.close ();
            return true;
        }catch (Exception e){
            transaction.rollback ();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<RoomDTO> loadAll() {
    try{
        session=SessionFactoryConfig.getInstance ().getSession ();
        roomDAO.setSession (session);
        List<Room> roList=roomDAO.loadAll ();
        List<RoomDTO> list=new ArrayList<>();
        for (Room room:roList) {
            list.add(room.toDto());
        }
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
    public RoomDTO getRoom(String id) throws Exception {
        try {
            session = SessionFactoryConfig.getInstance().getSession();
            roomDAO.setSession(session);
            Room room = (Room) roomDAO.get(id);
            session.close();
            if (room != null) {
                return room.toDto();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

}
