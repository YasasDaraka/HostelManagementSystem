package lk.ijse.gdse.hostelManagement.controller;

import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.gdse.hostelManagement.bo.BOFactory;
import lk.ijse.gdse.hostelManagement.bo.custom.ReservationInfoBO;
import lk.ijse.gdse.hostelManagement.dto.ReservationProDTO;
import lk.ijse.gdse.hostelManagement.dto.tm.ReservationProTM;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {
    @FXML
    public AnchorPane root;
    @FXML
    private ImageView imgMoneyTw;
    @FXML
    private Label lblPaymentTw;
    @FXML
    private Label lblPayment;
    @FXML
    private ImageView imgMoney;
    @FXML
    private AnchorPane changePane;
    @FXML
    private AnchorPane hidePane;

    @FXML
    private ImageView panePhoto;
    @FXML
    private Label lblResId;
    @FXML
    private Label lblStId;
    @FXML
    private Label blastName;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblContact;
    @FXML
    private Label lblRmId;
    @FXML
    private Label lblMoney;
    @FXML
    private Label lblStatus;
    @FXML
    private Label lblType;
    @FXML
    private Label lblPaid;
    @FXML
    private Label lblUnpaid;

    @FXML
    private TextField txtSearch;
    @FXML
    private TableView <ReservationProTM> tblRes;
    @FXML
    private TableColumn <ReservationProTM,String> colResId;
    @FXML
    private TableColumn <ReservationProTM,String> colStId;
    @FXML
    private TableColumn <ReservationProTM,String> colStName;
    @FXML
    private TableColumn <ReservationProTM,String> colRmId;
    @FXML
    private TableColumn <ReservationProTM,String> colRmType;
    @FXML
    private TableColumn <ReservationProTM,String> colMoney;
    @FXML
    private TableColumn <ReservationProTM,String> colStatus;
    @FXML
    private TableColumn <ReservationProTM,String> colAddress;
    @FXML
    private TableColumn <ReservationProTM,String> colContact;

    ReservationInfoBO reservationBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION_INFO);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root.requestFocus();
        changePane.setVisible(true);
        hidePane.setVisible(false);
        imgMoney.setVisible(false);
        lblPayment.setVisible(false);
        imgMoneyTw.setVisible(true);
        lblPaymentTw.setVisible(true);
        setValueFactory();
        loadAll("UNPAID");

    }

    private void setValueFactory() {
        colResId.setCellValueFactory (new PropertyValueFactory<>("resId"));
        colStId.setCellValueFactory (new PropertyValueFactory<> ("studentId"));
        colStName.setCellValueFactory (new PropertyValueFactory<> ("studentName"));
        colContact.setCellValueFactory (new PropertyValueFactory<> ("contact"));
        colAddress.setCellValueFactory (new PropertyValueFactory<> ("address"));
        colRmId.setCellValueFactory (new PropertyValueFactory<> ("roomId"));
        colRmType.setCellValueFactory (new PropertyValueFactory<> ("roomType"));
        colStatus.setCellValueFactory (new PropertyValueFactory<> ("status"));
        colMoney.setCellValueFactory (new PropertyValueFactory<> ("keyMoney"));

    }
    private void loadAll(String id) {
        try {
            List<ReservationProDTO> all = reservationBO.loadAll (id);
            ObservableList<ReservationProTM> resList = FXCollections.observableArrayList ();
            for (ReservationProDTO dto : all) {
                resList.add (dto.toTM());
            }
            tblRes.setItems (resList);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private void navigateToHome(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.DASHBOARD,root);
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
    private void searchOnAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnClearOnAction(ActionEvent actionEvent) {
        changePane.setVisible(true);
        hidePane.setVisible(false);
        imgMoney.setVisible(false);
        lblPayment.setVisible(false);
        imgMoneyTw.setVisible(true);
        lblPaymentTw.setVisible(true);
        lblResId.setText(null);
        lblStId.setText(null);
        blastName.setText(null);
        lblAddress.setText(null);
        lblContact.setText(null);
        lblRmId.setText(null);
        lblMoney.setText(null);
        lblType.setText(null);
        txtSearch.clear();
        tblRes.refresh();
    }

    @FXML
    private void btnSearchOnAction(ActionEvent actionEvent) throws Exception {
        String id = txtSearch.getText();
        if(!txtSearch.getText().isEmpty()) {
            changePane.setVisible(false);
            hidePane.setVisible(true);
            imgMoney.setVisible(true);
            lblPayment.setVisible(true);
            imgMoneyTw.setVisible(false);
            lblPaymentTw.setVisible(false);
            if(reservationBO.checkStudent(id)) {
            ReservationProDTO res = reservationBO.checkInfo(id);
            if (res != null) {
                lblResId.setText(res.getResId());
                lblStId.setText(res.getStudentId());
                blastName.setText(res.getStudentName());
                lblAddress.setText(res.getAddress());
                lblContact.setText(res.getContact());
                lblRmId.setText(res.getRoomId());
                lblMoney.setText(res.getKeyMoney());
                lblType.setText(res.getRoomType());
                if(res.getStatus().equals("UNPAID")){
                    lblStatus.setTextFill(Color.web("#a80000"));
                    lblStatus.setText(res.getStatus());
                }else{
                    lblStatus.setTextFill(Color.web("#424b4f"));
                    lblStatus.setText(res.getStatus());
                }

                new Alert(Alert.AlertType.CONFIRMATION, "Student Found!").show();

                tblRes.getItems().stream()
                        .filter(item -> item.getResId().equals(res.getResId()) )
                        .findAny()
                        .ifPresent(item -> {
                            tblRes.getSelectionModel().select(item);
                            tblRes.scrollTo(item);
                        });

                String imageName = id + ".png";
                String filePath = "D:\\New folder (6)\\HostelManagementSystem\\src\\lk\\ijse\\gdse\\hostelManagement\\view\\assests\\images\\capture\\" + imageName;
                File outputFile = new File(filePath);
                if (outputFile.exists()) {
                    System.out.println("Get image");
                    Image image = new Image(outputFile.toURI().toString());
                    panePhoto.setImage(image);
                } else {
                    panePhoto.setImage(null);
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Reservation Not Registerd!").show();
            }
        }else {
                new Alert(Alert.AlertType.ERROR, "Student Not Registerd!").show();
            }
        }
    }

    @FXML
    private void UnPaidOnAcrtin(MouseEvent mouseEvent) {
        lblUnpaid.setTextFill(Color.web("#195372"));
        lblUnpaid.setUnderline(true);
        lblUnpaid.setOpacity(0.8);
        lblPaid.setTextFill(Color.web("#424b4f"));
        lblPaid.setUnderline(false);
        lblPaid.setOpacity(0.6);
        try {
            setValueFactory();
            loadAll("UNPAID");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void paidOnAction(MouseEvent mouseEvent) {
        lblPaid.setTextFill(Color.web("#195372"));
        lblPaid.setUnderline(true);
        lblPaid.setOpacity(0.8);
        lblUnpaid.setTextFill(Color.web("#424b4f"));
        lblUnpaid.setUnderline(false);
        lblUnpaid.setOpacity(0.6);
        try {
            setValueFactory();
            loadAll("PAID");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
