package lk.ijse.gdse.hostelManagement.dto;

import lk.ijse.gdse.hostelManagement.entity.User;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private String userId;
    private String userName;
    private String password;

public User toEntity() {
    User user = new User();
    user.setUserId(this.userId);
    user.setUserName(this.userName);
    user.setPassword(this.password);
    return user;
    }
}
