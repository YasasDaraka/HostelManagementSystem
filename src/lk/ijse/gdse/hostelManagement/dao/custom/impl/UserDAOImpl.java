package lk.ijse.gdse.hostelManagement.dao.custom.impl;

import lk.ijse.gdse.hostelManagement.dao.custom.UserDAO;
import lk.ijse.gdse.hostelManagement.entity.Room;
import lk.ijse.gdse.hostelManagement.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class UserDAOImpl implements UserDAO<User,String> {
    private Session session;
    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String save(User user) {
        return String.valueOf(session.save(user));
    }

    @Override
    public void update(User user) {
        session.update (user);
    }

    @Override
    public void delete(User user) {
        session.delete (user);
    }

    @Override
    public User get(String id) throws Exception {
        return session.get (User.class,id);
    }

    @Override
    public List<User> loadAll() {
        String sql = "SELECT C FROM User AS C";
        Query query = session.createQuery(sql);
        List list = query.list();
        session.close();
        return list;
    }

    @Override
    public List<String> getIds() {
        return null;
    }
}
