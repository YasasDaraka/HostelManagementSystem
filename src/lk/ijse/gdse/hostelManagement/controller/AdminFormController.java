package lk.ijse.gdse.hostelManagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class AdminFormController {
    public AnchorPane root;
    @FXML
    private TextField txtName;
    @FXML
    private PasswordField txtPassword;
    private String saveName="yasas";
    private String savePass="1234";
    @FXML
    private void playMouseEnterAnimation(MouseEvent mouseEvent) {
    }

    @FXML
    private void playMouseExitAnimation(MouseEvent mouseEvent) {
    }

    @FXML
    private void logInOnAction(ActionEvent actionEvent) throws IOException {
        if(!txtName.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
                String name = txtName.getText();
                String pass = txtPassword.getText();

                if (name.equals(saveName) && pass.equals(savePass)) {
                    Navigation.navigate(Routes.CREATE_USER, root);
                }if (!name.equals(saveName) && pass.equals(savePass)) {
                    new Alert(Alert.AlertType.ERROR, "Username Invaild!").show();
                }if (name.equals(saveName) && !pass.equals(savePass)) {
                    new Alert(Alert.AlertType.ERROR, "Password Invaild!").show();
                }if (!name.equals(saveName) && !pass.equals(savePass)) {
                    new Alert(Alert.AlertType.ERROR, "Username & Password Invaild!").show();
                }

        }else{new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show(); }
    }

    @FXML
    private void navigateToHome(MouseEvent mouseEvent) {
    }
}
