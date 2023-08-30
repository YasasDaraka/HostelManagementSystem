package lk.ijse.gdse.hostelManagement.dao;

import org.hibernate.Session;
import java.util.List;

public interface CrudDAO<T,ID> extends SuperDAO{
     void setSession(Session session);

     String save(T object);

     void update(T object);

     void delete(T object);

     T get(ID id) throws Exception;

     List<T> loadAll();

     List<ID> getIds();

}
