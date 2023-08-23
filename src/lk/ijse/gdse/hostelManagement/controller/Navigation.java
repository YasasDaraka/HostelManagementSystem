package lk.ijse.gdse.hostelManagement.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {

    private static AnchorPane anchorPane;

    public static void navigate(Routes routes, AnchorPane anchorPane) throws IOException {
        Navigation.anchorPane=anchorPane;
        Navigation.anchorPane.getChildren().clear();
        Stage window=(Stage)Navigation.anchorPane.getScene().getWindow();

        switch (routes) {
            case STUDENT:
                window.setTitle ("MANAGE STUDENTS");
                iniUi("studentForm.fxml");
                break;
            case DASHBOARD:
                window.setTitle ("DASHBOARD");
                iniUi("dashboardForm.fxml");
                break;
            case ROOM:
                window.setTitle ("ROOM MANAGE");
                iniUi("roomForm.fxml");
                break;
            case RESERVATION:
                window.setTitle ("ROOM MANAGE");
                iniUi("reservationForm.fxml");
                break;
            default:
                System.out.println ("Fxml Not Loading");
        }
    }

    private static void iniUi(String location) throws IOException {
        Navigation.anchorPane.getChildren().add(FXMLLoader.load(Navigation.class.getResource("/lk/ijse/gdse/hostelManagement/view/"+location)));
    }
}
