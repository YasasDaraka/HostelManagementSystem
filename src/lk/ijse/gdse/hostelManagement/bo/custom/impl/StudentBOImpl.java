package lk.ijse.gdse.hostelManagement.bo.custom.impl;

import lk.ijse.gdse.hostelManagement.bo.custom.StudentBO;
import lk.ijse.gdse.hostelManagement.config.SessionFactoryConfig;
import lk.ijse.gdse.hostelManagement.dao.DAOFactory;
import lk.ijse.gdse.hostelManagement.dao.custom.RoomDAO;
import lk.ijse.gdse.hostelManagement.dao.custom.StudentDAO;
import lk.ijse.gdse.hostelManagement.dto.StudentDTO;
import lk.ijse.gdse.hostelManagement.entity.Reservation;
import lk.ijse.gdse.hostelManagement.entity.Room;
import lk.ijse.gdse.hostelManagement.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {
    private Session session;
    StudentDAO studentDAO= DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    RoomDAO roomDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    @Override
    public boolean saveStudent(StudentDTO dto) {
        session= SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();
        try{
            studentDAO.setSession(session);
            studentDAO.save (dto.toEntity());
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
    public boolean updateStudent(StudentDTO dto) {
        session=SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();
        try {
            studentDAO.setSession (session);
            studentDAO.update (dto.toEntity());
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
    public boolean deleteStudent(StudentDTO dto) {
        session = null;
        Transaction transaction = null;

        try {
            session = SessionFactoryConfig.getInstance().getSession();
            transaction = session.beginTransaction();

            String id = dto.getStId();
            Student studentToDelete = session.get(Student.class, id);

            if (studentToDelete != null) {
                List<Reservation> reservationsToDelete = studentToDelete.getReservationList();

                if (reservationsToDelete != null) {
                    for (Reservation reservation : reservationsToDelete) {
                        Room room = reservation.getRoom();
                        room.setQty(room.getQty() + 1);
                        session.update(room);
                    }
                }

                session.delete(studentToDelete);

                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return false;
    }

    @Override
    public StudentDTO getStudent(String id) throws Exception {
    try{
        session= SessionFactoryConfig.getInstance ().getSession ();
        studentDAO.setSession (session);
        Student student= (Student) studentDAO.get (id);
        session.close();
        if(student != null) {
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
    public List<StudentDTO> loadAll() {
    try{
        session=SessionFactoryConfig.getInstance ().getSession ();
        studentDAO.setSession (session);
        List<Student> stList=studentDAO.loadAll ();
        List<StudentDTO> list=new ArrayList<>();
        for (Student student:stList) {
            list.add(student.toDto());
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
