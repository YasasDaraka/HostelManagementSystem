package lk.ijse.gdse.hostelManagement.entity;

import lk.ijse.gdse.hostelManagement.dto.UserDTO;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "user")
public class User {

        @Id
        @Column(name = "user_name")
        private String userName;
        @Column(name = "password")
        private String password;

 public UserDTO toDto() {
         UserDTO userDTO = new UserDTO();
         userDTO.setUserName(this.userName);
         userDTO.setPassword(this.password);
         return userDTO;
   }
}
