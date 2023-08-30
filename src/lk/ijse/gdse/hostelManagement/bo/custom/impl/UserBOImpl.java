package lk.ijse.gdse.hostelManagement.bo.custom.impl;

import lk.ijse.gdse.hostelManagement.bo.custom.UserBO;
import lk.ijse.gdse.hostelManagement.config.SessionFactoryConfig;
import lk.ijse.gdse.hostelManagement.dao.DAOFactory;
import lk.ijse.gdse.hostelManagement.dao.custom.StudentDAO;
import lk.ijse.gdse.hostelManagement.dao.custom.UserDAO;
import lk.ijse.gdse.hostelManagement.dto.StudentDTO;
import lk.ijse.gdse.hostelManagement.dto.UserDTO;
import lk.ijse.gdse.hostelManagement.entity.Student;
import lk.ijse.gdse.hostelManagement.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    private Session session;
    UserDAO userDAO= DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean saveUser(UserDTO dto) {
        session= SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();

        try{
            userDAO.setSession(session);
            String id=userDAO.save(dto.toEntity());
            transaction.commit ();
            session.close ();
            if (id!=null){
                return true;
            }
        }catch (Exception e){
            transaction.rollback ();
        }
        return false;
    }

    @Override
    public UserDTO getUser(String id) throws Exception {
        try{
            session= SessionFactoryConfig.getInstance ().getSession ();
            userDAO.setSession (session);
            User user= (User) userDAO.get (id);
            session.close();
            if(user != null) {
                return user.toDto();
            }
        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    @Override
    public List<UserDTO> loadAll() {
        try{
            session=SessionFactoryConfig.getInstance ().getSession ();
            userDAO.setSession (session);
            List<User> stList=userDAO.loadAll ();
            List<UserDTO> list=new ArrayList<>();
            for (User user:stList) {
                list.add(user.toDto());
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
}
