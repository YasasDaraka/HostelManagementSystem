package lk.ijse.gdse.hostelManagement.dao.custom.impl;

import lk.ijse.gdse.hostelManagement.dao.custom.StudentDAO;
import lk.ijse.gdse.hostelManagement.entity.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        session.delete (student);
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
}
