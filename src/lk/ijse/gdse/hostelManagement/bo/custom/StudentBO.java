package lk.ijse.gdse.hostelManagement.bo.custom;

import lk.ijse.gdse.hostelManagement.bo.SuperBO;
import lk.ijse.gdse.hostelManagement.dto.StudentDTO;
import org.hibernate.Transaction;

import java.util.List;

public interface StudentBO extends SuperBO {

     boolean saveStudent(StudentDTO dto);

     boolean updateStudent(StudentDTO dto);

     boolean deleteStudent(StudentDTO dto);

     StudentDTO getStudent(String id) throws Exception;

     List<StudentDTO> loadAll();
}
