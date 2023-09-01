package lk.ijse.gdse.hostelManagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.hostelManagement.bo.BOFactory;
import lk.ijse.gdse.hostelManagement.bo.custom.UserBO;
import lk.ijse.gdse.hostelManagement.dto.UserDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    @FXML
    public AnchorPane root;
    @FXML
    private ImageView imgcloseEye;
    @FXML
    private ImageView imgOpenEye;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtShowPass;
    @FXML
    private PasswordField txtHidePass;

    UserBO userBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgcloseEye.setVisible (false);
        imgOpenEye.setVisible (true);
        txtHidePass.setVisible (true);
        txtShowPass.setVisible (false);
    }

    @FXML
    private void loginOnAction(ActionEvent actionEvent) throws Exception {
        if(!txtId.getText().isEmpty() && !txtShowPass.getText().isEmpty() || !txtHidePass.getText().isEmpty()) {
            String id = txtId.getText();
            String pass = txtHidePass.getText();

            UserDTO userDTO= userBO.getUser(id);
            if(userDTO != null){
                String usPass = userDTO.getPassword();
                if (usPass.equals(pass)){
                    Navigation.navigate(Routes.DASHBOARD, root);
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

    @FXML
    private void createAccOnAction(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.ADMIN, root);
    }

    @FXML
    private void openEyeOnAction(MouseEvent mouseEvent) {
        imgcloseEye.setVisible (true);
        imgOpenEye.setVisible (false);
        txtShowPass.setVisible (true);
        txtHidePass.setVisible (false);
        txtShowPass.setText (txtHidePass.getText ());
    }

    @FXML
    private void closeEyeOnAction(MouseEvent mouseEvent) {
        imgcloseEye.setVisible (false);
        imgOpenEye.setVisible (true);
        txtShowPass.setVisible (false);
        txtHidePass.setVisible (true);
        txtHidePass.setText (txtShowPass.getText ());
    }
}
