package lk.ijse.gdse.hostelManagement.dao.custom;
import lk.ijse.gdse.hostelManagement.dao.CrudDAO;
import lk.ijse.gdse.hostelManagement.entity.Student;

import java.util.List;

public interface StudentDAO<T,ID> extends CrudDAO<Student,String> {
    boolean checkStudentWithMiss(String id ,String resId);

    boolean checkStudent(String id);

}
