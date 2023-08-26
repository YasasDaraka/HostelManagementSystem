package lk.ijse.gdse.hostelManagement.dao.custom;
import lk.ijse.gdse.hostelManagement.dao.CrudDAO;
import lk.ijse.gdse.hostelManagement.entity.Student;
import org.hibernate.Session;

public interface StudentDAO<T,ID> extends CrudDAO<Student,String> {

}
