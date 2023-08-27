package lk.ijse.gdse.hostelManagement.controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.gdse.hostelManagement.bo.BOFactory;
import lk.ijse.gdse.hostelManagement.bo.custom.RoomBO;
import lk.ijse.gdse.hostelManagement.dto.RoomDTO;
import java.io.IOException;
public class RoomFormController {

    @FXML
    public AnchorPane root;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtKeyMoney;
    @FXML
    private TextField txtQty;
    RoomBO roomBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);
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
    private void btnSaveOnAction(ActionEvent actionEvent) throws Exception {
        if(!txtId.getText().isEmpty() && !txtType.getText().isEmpty() && !txtKeyMoney.getText().isEmpty() && !txtQty.getText().isEmpty()) {
            if(check()){
                String id = txtId.getText();
                int qty = Integer.parseInt(txtQty.getText());
                RoomDTO roomDTO = new RoomDTO (txtId.getText (),txtType.getText(),txtKeyMoney.getText(),qty);
                RoomDTO room= roomBO.getRoom(id);
                if(room != null){
                    new Alert(Alert.AlertType.ERROR, "Room ID Alredy Registerd!").show();
                }else{
                    boolean  isSaved = roomBO.saveRoom(roomDTO);
                    if(isSaved){
                        new Alert(Alert.AlertType.CONFIRMATION, "Room Register Succesfully!").show();
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Room Not Saved!").show();
                    }
                }
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }
    }
    private boolean check(){
        String key = txtKeyMoney.getText();
        String qty = txtQty.getText();

        if (!key.matches("^\\d+$") && !qty.matches("^\\d+$")){
            new Alert(Alert.AlertType.ERROR, "Invalid Key-money Value & Quantity Value").show();
            txtKeyMoney.requestFocus();
            return false;
        }if (!key.matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Key-money Value").show();
            txtKeyMoney.requestFocus();
            return false;
        }if (!qty.matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Quantity Value").show();
            txtQty.requestFocus();
            return false;
        }
        else {
            return true;
        }

    }
    @FXML
    private void btnUpdateOnAction(ActionEvent actionEvent) throws Exception {
        if(!txtId.getText().isEmpty() && !txtType.getText().isEmpty() && !txtKeyMoney.getText().isEmpty() && !txtQty.getText().isEmpty()) {
            if(check()){
                int qty = Integer.parseInt(txtQty.getText());
                String id = txtId.getText();
                RoomDTO roomDTO = new RoomDTO (txtId.getText (),txtType.getText(),txtKeyMoney.getText(),qty);
                RoomDTO room= roomBO.getRoom(id);
                if(room != null){
                    boolean  isUpdate = roomBO.updateRoom(roomDTO);
                    if(isUpdate){
                        new Alert(Alert.AlertType.CONFIRMATION, "Room Update Succesfully!").show();
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Room Not Updated!").show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Room Not Registerd!").show();
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
            RoomDTO roomDTO= roomBO.getRoom(id);
            if(roomDTO != null){
                boolean  isDelete = roomBO.deleteRoom(roomDTO);
                if(isDelete){
                    new Alert(Alert.AlertType.CONFIRMATION, "Room Delete Succesfully!").show();
                    clear();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Room Not Deleted!").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Room Not Registerd!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }
    }

    @FXML
    private void btnSearchOnAction(ActionEvent actionEvent) throws Exception {
        String id = txtId.getText();
        if(!txtId.getText().isEmpty()) {
            RoomDTO room= roomBO.getRoom(id);
            if(room != null) {
                txtId.setText(room.getRoomId());
                txtType.setText(room.getType());
                txtKeyMoney.setText(room.getKeyMoney());
                txtQty.setText(String.valueOf(room.getQty()));

            }else{
                new Alert(Alert.AlertType.ERROR, "Room Not Registerd!").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }

    }
    @FXML
    private void searchOnAction(ActionEvent actionEvent) throws Exception {
        String id = txtId.getText();
        if(!txtId.getText().isEmpty()) {
            RoomDTO room= roomBO.getRoom(id);
            if(room != null) {
                txtId.setText(room.getRoomId());
                txtType.setText(room.getType());
                txtKeyMoney.setText(room.getKeyMoney());
                txtQty.setText(String.valueOf(room.getQty()));
                new Alert(Alert.AlertType.CONFIRMATION, "Room Alredy Registerd!").show();
            }
        }
    }
    @FXML
    private void btnClearOnAction(ActionEvent actionEvent) {
        clear();
    }
    private void clear(){
        txtId.clear();
        txtType.clear();
        txtKeyMoney.clear();
        txtQty.clear();
    }
}
