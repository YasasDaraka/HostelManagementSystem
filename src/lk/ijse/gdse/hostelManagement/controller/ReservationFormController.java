package lk.ijse.gdse.hostelManagement.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.gdse.hostelManagement.bo.BOFactory;
import lk.ijse.gdse.hostelManagement.bo.custom.ReservationBO;
import lk.ijse.gdse.hostelManagement.dto.ReservationDTO;
import lk.ijse.gdse.hostelManagement.dto.RoomDTO;
import lk.ijse.gdse.hostelManagement.dto.StudentDTO;
import lk.ijse.gdse.hostelManagement.dto.tm.ReservationTM;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationFormController implements Initializable {

    @FXML
    public AnchorPane root;
    @FXML
    private TableView <ReservationTM>tblRes;
    @FXML
    private TableColumn <ReservationTM,String> colResId;
    @FXML
    private TableColumn <ReservationTM,String> colStId;
    @FXML
    private TableColumn <ReservationTM,String> colStName;
    @FXML
    private TableColumn <ReservationTM,String> colRoomId;
    @FXML
    private TableColumn <ReservationTM,String> colRoomType;
    @FXML
    private TableColumn <ReservationTM,String> colStatus;
    @FXML
    private Label lblQty;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private TextField txtResId;
    @FXML
    private ComboBox cmbRmId;
    @FXML
    private ComboBox cmdStId;
    @FXML
    private ComboBox cmbStatus;
    @FXML
    private Label lblStudentName;
    @FXML
    private Label lblRoomType;

    ReservationBO reservationBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> data = FXCollections.observableArrayList ("PAID","UNPAID");
        cmbStatus.setItems (data);
        root.requestFocus();
        setValueFactory();
        nextResId();
        loadRoomId();
        loadStId();
        setSt();
        setRoom();
        setDate();
        setTime();
        loadAll();
        setSelectToTxt();
    }
    private void setValueFactory() {
        colResId.setCellValueFactory (new PropertyValueFactory<>("resId"));
        colStId.setCellValueFactory (new PropertyValueFactory<> ("stId"));
        colStName.setCellValueFactory (new PropertyValueFactory<> ("stName"));
        colRoomId.setCellValueFactory (new PropertyValueFactory<> ("roomId"));
        colRoomType.setCellValueFactory (new PropertyValueFactory<> ("roomType"));
        colStatus.setCellValueFactory (new PropertyValueFactory<> ("status"));

    }
    private void setSelectToTxt() {
        tblRes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtResId.setText(newSelection.getResId());
                cmbRmId.setValue(newSelection.getRoomId());
                cmdStId.setValue(newSelection.getStId());
                cmbStatus.setValue(newSelection.getStatus());
                lblStudentName.setText(newSelection.getStName());
                lblRoomType.setText(newSelection.getRoomType());

            }
        });
    }

    private void loadAll() {
        try {
            List<ReservationDTO> all = reservationBO.loadAll ();
            ObservableList<ReservationTM> resList = FXCollections.observableArrayList ();
            for (ReservationDTO dto : all) {
                resList.add (dto.toTM());
            }
            tblRes.setItems (resList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDate() {
        date.setText(String.valueOf(LocalDate.now()));
    }

    private void setTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e ->{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
            time.setText(LocalTime.now().format(formatter));
        }),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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
    private void navigateToHome(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.DASHBOARD,root);
    }
    @FXML
    private void navigateToStudent(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.STUDENT,root);
    }

    @FXML
    private void navigateToRoom(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.ROOM,root);
    }
    @FXML
    private void btnSaveOnAction(ActionEvent actionEvent) throws Exception {

        if(!txtResId.getText().isEmpty() && cmdStId.getValue()!= null && cmbRmId.getValue() != null  && cmbStatus.getValue() != null) {
            String resId = txtResId.getText();
            String status = String.valueOf(cmbStatus.getValue());
            String st = String.valueOf(cmdStId.getValue());
            String room = String.valueOf(cmbRmId.getValue());
            ReservationDTO res = reservationBO.getRes(resId);
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setStId(st);
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setRoomId(room);
            if (res == null){
               // if (checkReg(resId,room,st)) {
                    ReservationDTO saveRes = new ReservationDTO(resId, studentDTO, roomDTO, status,null);
                    if (reservationBO.roomQty(room) > 0) {
                        boolean isSaved = reservationBO.saveRes(saveRes);
                        if (isSaved) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Reservation Register Succesfully!").show();
                            loadAll();
                            setValueFactory();
                            nextResId();
                            setRoom();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Reservation Not Saved!").show();
                        }
                    }else {
                        new Alert(Alert.AlertType.ERROR, "Alredy all Rooms are Full!").show();
                        cmbRmId.requestFocus();
                    }
               // }
            }else {
                new Alert(Alert.AlertType.ERROR, "Reservation ID Alredy Registerd!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }lblQty.setText(null);

    }
    //if can use to register reservation by with one student only have one room
    /*    private boolean checkReg(String resId,String roomId,String stid) {
        boolean room = reservationBO.checkRoom(resId,roomId);
        boolean student = reservationBO.checkStudent(stid);

        if (student && room){
            new Alert(Alert.AlertType.ERROR, "Alredy Student & Room Registerd").show();
            cmdStId.requestFocus();
            return false;
        }if (student) {
            new Alert(Alert.AlertType.ERROR, "Alredy Student Registerd").show();
            cmdStId.requestFocus();
            return false;
        }if (room) {
            new Alert(Alert.AlertType.ERROR, "Alredy Room Registerd").show();
            cmbRmId.requestFocus();
            return false;
        }
        if(!student && !room) {
            return true;
        }
        return false;
    }*/
    @FXML
    private void btnUpdateOnAction(ActionEvent actionEvent) throws Exception {
        if(!txtResId.getText().isEmpty() && cmdStId.getValue()!= null && cmbRmId.getValue() != null  && cmbStatus.getValue() != null) {
            String resId = txtResId.getText();
            String status = String.valueOf(cmbStatus.getValue());
            String st = String.valueOf(cmdStId.getValue());
            String room = String.valueOf(cmbRmId.getValue());
            ReservationDTO res = reservationBO.getRes(resId);
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setStId(st);
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setRoomId(room);
            ReservationDTO updateRes = new ReservationDTO(resId, studentDTO, roomDTO, status,res.getDate());
        if (res != null) {
            if (res.getStudentDTO().getStId().equals(st) && res.getRoomDTO().getRoomId().equals(room)) {

                boolean isupdate = reservationBO.updateRes(updateRes);
                if (isupdate) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Reservation Update Succesfully!").show();
                    tblRes.refresh();
                    setRoom();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Reservation Not Update!").show();
                }
            } else {
                if (reservationBO.checkStudentWithMiss(st,resId)) {
                    new Alert(Alert.AlertType.ERROR, "Alredy Student Registerd").show();
                    cmdStId.requestFocus();
                } else {
                    if (!res.getRoomDTO().getRoomId().equals(room)) {

                        if (reservationBO.roomQty(room) > 0) {

                            boolean isupdate = reservationBO.updateWithRoom(room, res.getRoomDTO().getRoomId(), updateRes);
                            if (isupdate) {
                                new Alert(Alert.AlertType.CONFIRMATION, "Reservation Update Succesfully!").show();
                                loadAll();
                                setValueFactory();
                                setRoom();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Reservation Not Update!").show();
                            }

                        } else {
                            new Alert(Alert.AlertType.ERROR, "Alredy all Rooms are Full!").show();
                            cmbRmId.requestFocus();
                        }

                    } else {
                        boolean isUpdate = reservationBO.updateRes(updateRes);
                        if (isUpdate) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Reservation Update Succesfully!").show();
                            tblRes.refresh();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Reservation Not Update!").show();
                        }
                    }

                }
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Reservation ID Not Registerd!").show();
        }

        }else{
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }lblQty.setText(null);
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent actionEvent) throws Exception {
        String id = txtResId.getText();
        if(!txtResId.getText().isEmpty()) {
            ReservationDTO res= reservationBO.getRes(id);
            if(res != null){
                boolean  isDelete = reservationBO.deleteRes(res);
                if(isDelete){
                    new Alert(Alert.AlertType.CONFIRMATION, "Reservation Delete Succesfully!").show();
                    clear();
                    loadAll();
                    setValueFactory();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Reservation Not Deleted!").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Reservation Not Registerd!").show();
            }

        }else{
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }lblQty.setText(null);
    }

    private void clear() {
        try {
            nextResId();
            cmdStId.setValue(null);
            cmbRmId.setValue(null);
            cmbStatus.setValue(null);
            lblQty.setText(null);
            lblRoomType.setText(null);
            lblStudentName.setText(null);
            loadAll();
            setValueFactory();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void btnSearchOnAction(ActionEvent actionEvent) throws Exception {
        String id = txtResId.getText();
        if(!txtResId.getText().isEmpty()) {
            ReservationDTO res= reservationBO.getRes(id);
            if(res != null) {
                search();
                txtResId.setText(res.getResId());
                cmdStId.setValue(res.getStudentDTO().getStId());
                cmbRmId.setValue(res.getRoomDTO().getRoomId());
                cmbStatus.setValue(res.getStatus());

                setSt();
                setRoom();

            }else{
                new Alert(Alert.AlertType.ERROR, "Room Not Registerd!").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }
    }

    @FXML
    private void btnClearOnAction(ActionEvent actionEvent) {
        clear();
        tblRes.refresh();
    }
    private void nextResId() {
       String id = reservationBO.loadResId();
       txtResId.setText(id);
    }
    private void loadRoomId() {
        List<String> roomIds = reservationBO.getRoomIds();
        ObservableList room = FXCollections.observableArrayList (roomIds);
        cmbRmId.setItems (room);
    }
    private void loadStId() {
        List<String> stIds = reservationBO.getStIds();
        ObservableList student = FXCollections.observableArrayList (stIds);
        cmdStId.setItems (student);
    }
    private void setSt(){
        cmdStId.setOnAction (event -> {
            String stId = cmdStId.getValue().toString ();
            try {
                StudentDTO st = reservationBO.getStudent(stId);
                lblStudentName.setText (st.getStName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void setRoom(){
        cmbRmId.setOnAction (event -> {
            String stId = cmbRmId.getValue().toString ();
            try {
                RoomDTO room = reservationBO.getRoom(stId);
                lblRoomType.setText (room.getType());
                lblQty.setText (String.valueOf (room.getQty ()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void search(){
        tblRes.getItems().stream()
                .filter(item -> item.getResId().equals(txtResId.getText()) )
                .findAny()
                .ifPresent(item -> {
                    tblRes.getSelectionModel().select(item);
                    tblRes.scrollTo(item);
                });
    }

    @FXML
    private void searchOnAction(ActionEvent actionEvent) {
    }
}
