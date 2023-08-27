package lk.ijse.gdse.hostelManagement.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {
    @FXML
    public AnchorPane root;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private ImageView imgStudent;
    @FXML
    private ImageView imgRoom;
    @FXML
    private ImageView imgReservation;
    @FXML
    private ImageView imgSetting;
    @FXML
    private Label lblMenu;
    @FXML
    private Label lblDescription;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        setTime();
    }

    @FXML
    private void logout(ActionEvent actionEvent) {
    }

    @FXML
    private void studentManage(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.STUDENT,root);
    }

    @FXML
    private void roomMange(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.ROOM,root);
    }

    @FXML
    private void reservationMange(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.RESERVATION,root);
    }

    @FXML
    private void settingMange(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.SETTING,root);

    }

    @FXML
    private void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            switch (icon.getId()) {
                case "imgStudent":
                    lblMenu.setText("Manage Student");
                    lblDescription.setText("Click to Add, Update, Delete, Search Students");
                    break;
                case "imgRoom":
                    lblMenu.setText("Manage Rooms");
                    lblDescription.setText("Click to Add, Update, Delete, Search Rooms");
                    break;
                case "imgReservation":
                    lblMenu.setText("Manage Reservation");
                    lblDescription.setText("Click here if you want to manage reservation");
                    break;
                case "imgSetting":
                    lblMenu.setText("Manage Settings");
                    lblDescription.setText("Click here if you want to manage settings");
                    break;
            }

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
    private void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }
    @FXML
    private void setDate() {
        date.setText(String.valueOf(LocalDate.now()));
    }
    
    @FXML
    private void setTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e ->{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
            time.setText(LocalTime.now().format(formatter));
        }),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
