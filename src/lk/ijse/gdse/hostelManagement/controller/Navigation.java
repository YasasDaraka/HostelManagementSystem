package lk.ijse.gdse.hostelManagement.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Navigation {

    private static AnchorPane anchorPane;

    public static void navigate(Routes routes, AnchorPane anchorPane) throws IOException {
        Navigation.anchorPane=anchorPane;
        Navigation.anchorPane.getChildren().clear();
        Stage window=(Stage)Navigation.anchorPane.getScene().getWindow();

        switch (routes) {
            case STUDENT:
                System.gc();
                window.setTitle ("Manage Students");
                iniUi("studentForm.fxml");
                break;
            case DASHBOARD:
                System.gc();
                window.setTitle ("Dashboard");
                iniUi("dashboardForm.fxml");
                break;
            case ROOM:
                System.gc();
                window.setTitle ("Rooms Manage");
                iniUi("roomForm.fxml");
                break;
            case RESERVATION:
                System.gc();
                window.setTitle ("Reservation Manage");
                iniUi("reservationForm.fxml");
                break;
            case SETTING:
                System.gc();
                window.setTitle ("Settings Manage");
                iniUi("settingForm.fxml");
                break;
            case PAYMENTS:
                System.gc();
                window.setTitle ("View Payments");
                iniUi("paymentForm.fxml");
                break;
            case CREATE_USER:
                System.gc();
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(Navigation.class.getResource("/lk/ijse/gdse/hostelManagement/view/createUserAccForm.fxml"))));
                stage.setTitle ("User Sign In");
                stage.show();

                Stage stage2 = (Stage) anchorPane.getScene().getWindow();
                stage2.close();
                break;
            case ADMIN:
                System.gc();
                FXMLLoader fxmlLoader=new FXMLLoader(Navigation.class.getResource("/lk/ijse/gdse/hostelManagement/view/adminForm.fxml"));
                Parent parent=fxmlLoader.load();
                Stage stage3=new Stage();
                stage3.setScene(new Scene(parent));
                stage3.setTitle ("Admin LogIn");
                stage3.show();

                Stage stage4 = (Stage) anchorPane.getScene().getWindow();
                stage4.close();
                break;
            case LOGIN:
                System.gc();
                window.setTitle ("Log In");
                iniUi("loginForm.fxml");
                break;
            default:
                System.out.println ("Fxml Not Loading");
        }
    }

    private static void iniUi(String location) throws IOException {
        Navigation.anchorPane.getChildren().add(FXMLLoader.load(Navigation.class.getResource("/lk/ijse/gdse/hostelManagement/view/"+location)));
    }
}
