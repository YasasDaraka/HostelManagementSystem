package lk.ijse.gdse.hostelManagement.controller;

import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.gdse.hostelManagement.bo.BOFactory;
import lk.ijse.gdse.hostelManagement.bo.custom.StudentBO;
import lk.ijse.gdse.hostelManagement.config.SessionFactoryConfig;
import lk.ijse.gdse.hostelManagement.dto.StudentDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {

    @FXML
    public AnchorPane root;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtname;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtContact;
    @FXML
    private ComboBox cmbGender;
    @FXML
    private DatePicker datePick;

    StudentBO studentBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> gen = FXCollections.observableArrayList("Male", "Female");
        cmbGender.setItems(gen);
    }
    @FXML
    private void btnSaveOnAction(ActionEvent actionEvent) throws Exception {
        String dob = String.valueOf(datePick.getValue());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = dateFormat.parse(dob);
        String gender = cmbGender.getValue ().toString ();
        String id = txtId.getText();
        StudentDTO studentDTO = new StudentDTO (id, txtname.getText (), txtAddress.getText (), txtContact.getText (),parsedDate, gender);
        if(!txtId.getText().isEmpty() && !txtname.getText().isEmpty() && cmbGender.getValue()!= null && datePick.getValue() != null && !txtAddress.getText().isEmpty() && !txtContact.getText().isEmpty()) {
            if(check()){
                StudentDTO student= studentBO.getStudent(id);
                if(student != null){
                    new Alert(Alert.AlertType.ERROR, "Student ID alredy registerd!").show();
                }else{
                   boolean  isSaved = studentBO.saveStudent(studentDTO);
                   if(isSaved){
                       new Alert(Alert.AlertType.CONFIRMATION, "Student register succesfully!").show();
                   }else{
                       new Alert(Alert.AlertType.ERROR, "Student not saved!").show();
                   }
                }
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }
    }
    private boolean check(){
        String addressText = txtAddress.getText();
        String contactText = txtContact.getText();

        if (!contactText.matches("\\d{10}") && !addressText.matches(".{5,}")){
            new Alert(Alert.AlertType.ERROR, "Invalid Contact & Address").show();
            txtContact.requestFocus();
            return false;
        }if (!addressText.matches(".{5,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 5 characters long").show();
            txtAddress.requestFocus();
            return false;
        }if (!contactText.matches("\\d{10}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact").show();
            txtContact.requestFocus();
            return false;
        }
        else {
            return true;
        }

    }
    @FXML
    private void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnSearchOnAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnClearOnAction(ActionEvent actionEvent) {
    }

    @FXML
    private void navigateToHome(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.DASHBOARD,root);
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
