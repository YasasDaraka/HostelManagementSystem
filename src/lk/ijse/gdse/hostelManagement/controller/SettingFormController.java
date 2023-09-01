package lk.ijse.gdse.hostelManagement.controller;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.gdse.hostelManagement.bo.BOFactory;
import lk.ijse.gdse.hostelManagement.bo.custom.UserBO;
import lk.ijse.gdse.hostelManagement.dto.UserDTO;
import lk.ijse.gdse.hostelManagement.util.CodeGenarator;
import lk.ijse.gdse.hostelManagement.util.Sender;
import org.controlsfx.control.Notifications;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingFormController implements Initializable {
    @FXML
    public AnchorPane root;

    //detail pane
    @FXML
    private Pane detailPane;
    @FXML
    private ImageView imgDetailcloseEye;
    @FXML
    private ImageView imgDetailOpenEye;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtMail;
    @FXML
    private TextField txtDetailPassShow;
    @FXML
    private PasswordField txtDetailHidePass;
    @FXML
    private Label verificationLabel;
    //verify pane
    @FXML
    private Pane verifyPane;
    @FXML
    private TextField txtVerifyCode;
    //Update Pane
    @FXML
    private Pane updatepane;
    @FXML
    private TextField txtUpdateName;
    @FXML
    private TextField txtUpdatePassShow;
    @FXML
    private PasswordField txtUpdateHidePass;
    @FXML
    private Label lblUpdateId;
    @FXML
    private ImageView imgUpdatecloseEye;
    @FXML
    private ImageView imgUpdateOpenEye;

    private  String code;
    private  String logId;
    UserBO userBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {
            detailPane.setVisible(true);
            verifyPane.setVisible(false);
            updatepane.setVisible(false);
        });
    }

    @FXML
    private void logout(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.LOGIN,root);
    }

    @FXML
    private void navigateToHome(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.DASHBOARD,root);
    }

    @FXML
    private void playMouseEnterAnimation(MouseEvent mouseEvent) {
        Platform.runLater(() -> {
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
        });

    }

    @FXML
    private void playMouseExitAnimation(MouseEvent mouseEvent) {
        Platform.runLater(() -> {
            if (mouseEvent.getSource() instanceof ImageView) {
                ImageView icon = (ImageView) mouseEvent.getSource();
                ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
                scaleT.setToX(1);
                scaleT.setToY(1);
                scaleT.play();
                icon.setEffect(null);
            }
        });

    }
    //Detail
    @FXML
    private void DetailcloseEyeOnAction(MouseEvent mouseEvent) {
        Platform.runLater(() -> {
            imgDetailcloseEye.setVisible (false);
            imgDetailOpenEye.setVisible (true);
            txtDetailPassShow.setVisible (false);
            txtDetailHidePass.setVisible (true);
            txtDetailHidePass.setText (txtDetailPassShow.getText ());
        });

    }

    @FXML
    private void DetailopenEyeOnAction(MouseEvent mouseEvent) {
        Platform.runLater(() -> {
            imgDetailcloseEye.setVisible (true);
            imgDetailOpenEye.setVisible (false);
            txtDetailPassShow.setVisible (true);
            txtDetailHidePass.setVisible (false);
            txtDetailPassShow.setText (txtDetailHidePass.getText ());
        });

    }

    @FXML
    private void UpdatecloseEyeOnAction(MouseEvent mouseEvent) {
        Platform.runLater(() -> {
            imgUpdatecloseEye.setVisible (false);
            imgUpdateOpenEye.setVisible (true);
            txtUpdatePassShow.setVisible (false);
            txtUpdateHidePass.setVisible (true);
            txtUpdateHidePass.setText (txtUpdatePassShow.getText ());
        });

    }

    @FXML
    private void UpdateopenEyeOnAction(MouseEvent mouseEvent) {
        Platform.runLater(() -> {
            imgUpdatecloseEye.setVisible (true);
            imgUpdateOpenEye.setVisible (false);
            txtUpdatePassShow.setVisible (true);
            txtUpdateHidePass.setVisible (false);
            txtUpdatePassShow.setText (txtUpdateHidePass.getText ());
        });

    }

    @FXML
    private void requestOnAction(ActionEvent actionEvent) throws Exception {

        if(!txtId.getText().isEmpty() && !txtMail.getText().isEmpty() && !txtName.getText().isEmpty() && !txtDetailPassShow.getText().isEmpty() || !txtDetailHidePass.getText().isEmpty()) {
            String id = txtId.getText();
            String pass = txtDetailHidePass.getText();
            String mail = txtMail.getText();

            if(imgDetailcloseEye.isVisible()){
                txtDetailHidePass.setText (txtDetailPassShow.getText ());
            }else {
                txtDetailPassShow.setText (txtDetailHidePass.getText ());
            }

            UserDTO userDTO= userBO.getUser(id);
            if(userDTO != null){
               String usPass = userDTO.getPassword();
                 if (usPass.equals(pass)){
                     String text="Your Verification Code";
                     code = String.valueOf (CodeGenarator.verifyCode ());
                     try {
                         Sender.outMail (code,mail,text);
                     } catch (MessagingException e) {
                         e.printStackTrace();
                         new Alert(Alert.AlertType.ERROR, "Invalid Details!").show();
                     }
                     detailPane.setVisible(false);
                     verifyPane.setVisible(true);
                     new Alert (Alert.AlertType.INFORMATION, "Check your G-mail inbox").show ();
                     logId = userDTO.getUserId();
                     lblUpdateId.setText(logId);
                 }else{
                     new Alert(Alert.AlertType.ERROR, "Wrong password!").show();
                 }

             }else{
                 new Alert(Alert.AlertType.ERROR, "User ID not found!").show();
             }

        }else {
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }
    }
    //Verify
    @FXML
    private void confirmOnAction(ActionEvent actionEvent) {
        if(!txtVerifyCode.getText().isEmpty()) {
                String txtCode = txtVerifyCode.getText();
            if (txtCode.equals(code)) {
                verifyPane.setVisible(false);
                updatepane.setVisible(true);
                new Alert(Alert.AlertType.INFORMATION, "verifying Success").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Check your verify code").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }
    }

    @FXML
    private void updateOnAction(ActionEvent actionEvent) {
        if(!txtUpdateName.getText().isEmpty() && !txtUpdatePassShow.getText().isEmpty() || !txtUpdateHidePass.getText().isEmpty()) {
            lblUpdateId.setText(logId);
                String name = txtUpdateName.getText();
            if(imgUpdatecloseEye.isVisible()){
                txtUpdateHidePass.setText (txtUpdatePassShow.getText ());
            }else {
                txtUpdatePassShow.setText (txtUpdateHidePass.getText ());
            }
                String pas = txtUpdateHidePass.getText();
                UserDTO userDTO = new UserDTO (logId,name,pas);
                    boolean  isUpdate = userBO.updateUser(userDTO);
                    if(isUpdate){
                        new Alert(Alert.AlertType.CONFIRMATION, "User Update Succesfully!").show();
                    }else{
                        new Alert(Alert.AlertType.ERROR, "User Not Updated!").show();
                    }
        }else{
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }
    }

    @FXML
    private void DeleteOnAction(ActionEvent actionEvent) throws Exception {
        lblUpdateId.setText(logId);
            UserDTO user= userBO.getUser(logId);
                boolean  isDelete = userBO.deleteUser(user);
                if(isDelete){
                    showCountdownPopup();
                }else{
                    new Alert(Alert.AlertType.ERROR, "User Not Deleted!").show();
                }
    }

    private void showCountdownPopup() {
        Notifications.create()
                .title("D24 HOSTEL\n\n")
                .text("User Delete Succesfully! Log out in 5 seconds").position(Pos.CENTER).darkStyle()
                .hideAfter(Duration.seconds(5))
                .show();

    }

}
