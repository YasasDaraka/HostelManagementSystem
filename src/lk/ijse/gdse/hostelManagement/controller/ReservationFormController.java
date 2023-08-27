package lk.ijse.gdse.hostelManagement.controller;

import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.gdse.hostelManagement.bo.BOFactory;
import lk.ijse.gdse.hostelManagement.bo.custom.ReservationBO;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationFormController implements Initializable {

    @FXML
    public AnchorPane root;
    @FXML
    private TextField txtResId;
    @FXML
    private ComboBox cmbRmId;
    @FXML
    private ComboBox cmdStId;
    @FXML
    private ComboBox cmbStatus;
    @FXML
    private TextField txtQty;
    @FXML
    private Label lblStudentName;
    @FXML
    private Label lblRoomType;

    ReservationBO reservationBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> data = FXCollections.observableArrayList ("PAID","UNPAID");
        cmbStatus.setItems (data);
        nextResId();
        loadRoomId();
        loadStId();
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
    private void btnSaveOnAction(ActionEvent actionEvent) {

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

    public void searchOnAction(ActionEvent actionEvent) {
    }
}
