package lk.ijse.gdse.hostelManagement.controller;

import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.gdse.hostelManagement.bo.BOFactory;
import lk.ijse.gdse.hostelManagement.bo.custom.StudentBO;
import lk.ijse.gdse.hostelManagement.dto.StudentDTO;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {

    @FXML
    public AnchorPane root;
    @FXML
    private TableView tblStudent;
    @FXML
    private TableColumn colStId;
    @FXML
    private TableColumn colStName;
    @FXML
    private TableColumn colGender;
    @FXML
    private TableColumn colAddress;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colDob;
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
        if(!txtId.getText().isEmpty() && !txtname.getText().isEmpty() && cmbGender.getValue()!= null && datePick.getValue() != null && !txtAddress.getText().isEmpty() && !txtContact.getText().isEmpty()) {
            String dob = String.valueOf(datePick.getValue());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(dob);
            String gender = cmbGender.getValue ().toString ();
            String id = txtId.getText();
            StudentDTO studentDTO = new StudentDTO (id, txtname.getText (), txtAddress.getText (), txtContact.getText (),parsedDate, gender);
            if(check()){
                StudentDTO student= studentBO.getStudent(id);
                if(student != null){
                    new Alert(Alert.AlertType.ERROR, "Student ID Alredy Registerd!").show();
                }else{
                   boolean  isSaved = studentBO.saveStudent(studentDTO);
                   if(isSaved){
                       new Alert(Alert.AlertType.CONFIRMATION, "Student Register Succesfully!").show();
                   }else{
                       new Alert(Alert.AlertType.ERROR, "Student Not Saved!").show();
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
    private void btnUpdateOnAction(ActionEvent actionEvent) throws Exception {
        if(!txtId.getText().isEmpty() && !txtname.getText().isEmpty() && cmbGender.getValue()!= null && datePick.getValue() != null && !txtAddress.getText().isEmpty() && !txtContact.getText().isEmpty()) {
            String dob = String.valueOf(datePick.getValue());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(dob);
            String gender = cmbGender.getValue ().toString ();
            String id = txtId.getText();
            StudentDTO studentDTO = new StudentDTO (id, txtname.getText (), txtAddress.getText (), txtContact.getText (),parsedDate, gender);
            if(check()){
                StudentDTO student= studentBO.getStudent(id);
                if(student != null){
                    boolean  isUpdate = studentBO.updateStudent(studentDTO);
                    if(isUpdate){
                        new Alert(Alert.AlertType.CONFIRMATION, "Student Update Succesfully!").show();
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Student Not Updated!").show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Student Not Registerd!").show();
                }
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent actionEvent) throws Exception {
        String id = txtId.getText();
        if(!txtId.getText().isEmpty()) {
                StudentDTO student= studentBO.getStudent(id);
                if(student != null){
                    boolean  isDelete = studentBO.deleteStudent(student);
                    if(isDelete){
                        new Alert(Alert.AlertType.CONFIRMATION, "Student Delete Succesfully!").show();
                        clear();
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Student Not Deleted!").show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Student Not Registerd!").show();
                }

        }else{
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }
    }

    @FXML
    private void btnSearchOnAction(ActionEvent actionEvent) throws Exception {
        String id = txtId.getText();
        if(!txtId.getText().isEmpty()) {
            StudentDTO st= studentBO.getStudent(id);
            if(st != null) {
                txtId.setText(st.getStId());
                txtname.setText(st.getStName());
                txtAddress.setText(st.getStAddress());
                txtContact.setText(st.getStContact());
                cmbGender.setValue(st.getGender());
                Date sqlDate = st.getDob();
                java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
                LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                datePick.setValue(localDate);

            }else{
                new Alert(Alert.AlertType.ERROR, "Student Not Registerd!").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }
    }

    @FXML
    private void btnClearOnAction(ActionEvent actionEvent) {
        clear();
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

    @FXML
    private void searchOnAction(ActionEvent actionEvent) throws Exception {
        String id = txtId.getText();
        if(!txtId.getText().isEmpty()) {
            StudentDTO st= studentBO.getStudent(id);
            if(st != null) {
                txtId.setText(st.getStId());
                txtname.setText(st.getStName());
                txtAddress.setText(st.getStAddress());
                txtContact.setText(st.getStContact());
                cmbGender.setValue(st.getGender());
                Date sqlDate = st.getDob();
                java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
                LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                datePick.setValue(localDate);
                new Alert(Alert.AlertType.CONFIRMATION, "Student Alredy Registerd!").show();
            }
        }
    }

    private void clear(){
        txtId.clear();
        txtname.clear();
        txtAddress.clear();
        txtContact.clear();
        cmbGender.setValue(null);
        datePick.setValue(null);
    }
   /*private void search(){
        tblRes.getItems().stream()
                .filter(item -> item.getResId().equals(txtResId.getText()) )
                .findAny()
                .ifPresent(item -> {
                    tblRes.getSelectionModel().select(item);
                    tblRes.scrollTo(item);
                });

    }*/
}
