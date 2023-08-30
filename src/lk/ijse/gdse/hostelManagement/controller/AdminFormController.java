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
    private void navigateToHome(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.LOGIN, root);
    }
}
