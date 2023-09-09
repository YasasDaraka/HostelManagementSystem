package lk.ijse.gdse.hostelManagement.controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.gdse.hostelManagement.bo.BOFactory;
import lk.ijse.gdse.hostelManagement.bo.custom.UserBO;
import lk.ijse.gdse.hostelManagement.dto.UserDTO;
import lk.ijse.gdse.hostelManagement.util.Sender;

import java.io.IOException;

public class CreateUserAccFormController {
    @FXML
    public AnchorPane root;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtRePass;
    @FXML
    private TextField txtMail;
    @FXML
    private TextField txtName;

    UserBO userBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    private void createOnAction(ActionEvent actionEvent) throws Exception {
     if (!txtName.getText().isEmpty() && !txtMail.getText().isEmpty() && !txtPassword.getText().isEmpty() && !txtRePass.getText().isEmpty()) {
         String pass = txtRePass.getText();
         String mail = txtMail.getText();
         String rePass = txtRePass.getText();
         String userName = txtName.getText();
         UserDTO user = userBO.getUser(userName);

           if(pass.equals(rePass)) {
               if(check()) {
                   if (user != null) {
                       new Alert(Alert.AlertType.ERROR, "Username Alredy Registerd!").show();
                   } else {
                   UserDTO userDTO = new UserDTO(userName, pass);
                   boolean isSaved = userBO.saveUser(userDTO);
                   if (isSaved) {
                       new Alert(Alert.AlertType.CONFIRMATION, "User Register Succesfully!").show();
                       Sender.outMail ("Now you are user in D24HOSTEL SYSTEM",mail,"D24HOSTEL");
                   } else {
                       new Alert(Alert.AlertType.ERROR, "User Not Saved!").show();
                   }
               }
           }
           }else{
               new Alert (Alert.AlertType.ERROR, "Passwords Not Maching").show ();

         }

     } else {
         new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
     }

 }
    private boolean check() {
        String mail=txtMail.getText ();

        if (!mail.matches("^([a-z0-9]{2,})([@])([a-z]{2,9})([.])([a-z]{2,})$")) {
            new Alert (Alert.AlertType.ERROR, "Invalid G-mail address").show ();
            txtMail.requestFocus ();
            return false;
        } else {
            return true;
        }
    }

    @FXML
    private void navigateToHome(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.LOGIN, root);
    }

    @FXML
    private void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    @FXML
    private void playMouseExitAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
    }

}
