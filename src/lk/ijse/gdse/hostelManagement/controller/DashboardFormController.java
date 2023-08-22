package lk.ijse.gdse.hostelManagement.controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class DashboardFormController {

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

    @FXML
    private void logout(ActionEvent actionEvent) {
    }

    @FXML
    private void roomManage(MouseEvent mouseEvent) {
    }
    
    public void studentManage(MouseEvent mouseEvent) {
    }

    public void roomMange(MouseEvent mouseEvent) {
    }

    public void reservationMange(MouseEvent mouseEvent) {
    }

    public void settingMange(MouseEvent mouseEvent) {
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
}
