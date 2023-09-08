package lk.ijse.gdse.hostelManagement.dao.custom.impl;

import lk.ijse.gdse.hostelManagement.dao.custom.StudentDAO;
import lk.ijse.gdse.hostelManagement.entity.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class StudentDAOImpl implements StudentDAO<Student,String> {
    private Session session;

    @Override
    public void setSession(Session session) {
        this.session=session;
    }
    @Override
    public String save(Student student) {
        return String.valueOf(session.save(student));

    }
    @Override
    public void update(Student student) {
        session.update (student);
    }

    @Override
    public void delete(Student student) {
        String id = student.getStId();
        Student studentToDelete = session.get(Student.class, id);
        session.delete(studentToDelete);
    }

    @Override
    public Student get(String id) throws Exception {
        return session.get(Student.class,id);
    }
    @Override
    public List<Student> loadAll() {
        String sql = "SELECT C FROM Student AS C";
        Query query = session.createQuery(sql);
        List list = query.list();
        session.close();
        return list;
    }
    @Override
    public List<String> getIds() {

        String hql = "SELECT stId from Student ";
        Query<String> query=session.createQuery (hql);
        List<String> results = query.list();
        session.close();
        return results;

    }
    @Override
    public boolean checkStudentWithMiss(String id ,String resId) {
        String hql = "SELECT COUNT(r) FROM Reservation r WHERE r.student.stId = :stId AND NOT (r.resId = :resId)";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("stId", id);
        query.setParameter("resId", resId);
        Long count = query.uniqueResult();
        boolean exists = count > 0;
        return exists;
    }
    @Override
    public boolean checkStudent(String id) {
        String hql = "SELECT COUNT(s) FROM Reservation s WHERE s.student.stId = :stId";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("stId", id);
        Long count = query.uniqueResult();
        boolean exists = count > 0;
        return exists;
    }

    @Override
    public String studentCount() {
        String jpql = "SELECT COUNT(s) FROM Student s";
        Query query = session.createQuery(jpql);
        long count = (long) query.getSingleResult();
        return String.valueOf(count);
    }
}
