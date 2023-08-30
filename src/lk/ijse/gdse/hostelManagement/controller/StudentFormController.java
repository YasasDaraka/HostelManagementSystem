package lk.ijse.gdse.hostelManagement.controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import lk.ijse.gdse.hostelManagement.bo.BOFactory;
import lk.ijse.gdse.hostelManagement.bo.custom.StudentBO;
import lk.ijse.gdse.hostelManagement.dto.StudentDTO;
import lk.ijse.gdse.hostelManagement.dto.tm.StudentTM;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable ,Runnable{

    @FXML
    public AnchorPane root;
    @FXML
    private TableView <StudentTM> tblStudent;
    @FXML
    private TableColumn <StudentTM,String> colStId;
    @FXML
    private TableColumn <StudentTM,String> colStName;
    @FXML
    private TableColumn <StudentTM,String> colGender;
    @FXML
    private TableColumn <StudentTM,String> colAddress;
    @FXML
    private TableColumn <StudentTM,String> colContact;
    @FXML
    private TableColumn <StudentTM,String> colDob;
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

    @FXML
    private ImageView panePhoto;

    @FXML
    private JFXButton btnCapture;

    private volatile boolean isCapturing;
    private Boolean isCameraEnabled = true;
    private FrameGrabber grabber;
    private Java2DFrameConverter converter;
    private BufferedImage image;
    private FileChooser fileChooser = new FileChooser();

    StudentBO studentBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> gen = FXCollections.observableArrayList("Male", "Female");
        cmbGender.setItems(gen);
        root.requestFocus();
        loadAll();
        setValueFactory();
        setSelectToTxt();

        try {
            grabber = new VideoInputFrameGrabber(0);
            converter = new Java2DFrameConverter();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    void onCapture(ActionEvent event) {
        if (!isCapturing) {
            isCapturing = true;
            Thread captureThread = new Thread(this);
            captureThread.start();
            btnCapture.setText("Take Picture");
            btnCapture.setStyle("-fx-background-color: #c93329;" +
                    "-fx-background-radius: 50;");
        } else {
            isCapturing = false;
            btnCapture.setText("Open Camera");
            btnCapture.setStyle("-fx-background-color: #158392;" +
                    "-fx-background-radius: 50;");
        }
    }
    @Override
    public void run() {
        try {
            grabber.start();
            while (isCapturing) {
                Frame frame = grabber.grab();
                image = converter.getBufferedImage(frame);
                Image fxImage = convertToJavaFXImage(image);
                double circleRadius = Math.min(fxImage.getWidth(), fxImage.getHeight()) / 2.0;

                WritableImage croppedImage = new WritableImage(
                        (int) (circleRadius * 2),
                        (int) (circleRadius * 2)
                );
                Circle mask = new Circle(circleRadius, circleRadius, circleRadius);

                for (int y = 0; y < croppedImage.getHeight(); y++) {
                    for (int x = 0; x < croppedImage.getWidth(); x++) {
                        if (mask.contains(x, y)) {
                            croppedImage.getPixelWriter().setColor(x, y, fxImage.getPixelReader().getColor((int) x, (int) y));
                        }
                    }
                }
                panePhoto.setImage(croppedImage);
            }
            grabber.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private Image convertToJavaFXImage(BufferedImage bufferedImage) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            return new Image(new ByteArrayInputStream(outputStream.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void loadAll() {
        try {
            List<StudentDTO> all = studentBO.loadAll ();
            ObservableList<StudentTM> resList = FXCollections.observableArrayList ();
            for (StudentDTO dto : all) {
                resList.add (dto.toTM());
            }
            tblStudent.setItems (resList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setValueFactory() {
        colStId.setCellValueFactory (new PropertyValueFactory<>("stId"));
        colStName.setCellValueFactory (new PropertyValueFactory<> ("stName"));
        colAddress.setCellValueFactory (new PropertyValueFactory<> ("stAddress"));
        colContact.setCellValueFactory (new PropertyValueFactory<> ("stContact"));
        colDob.setCellValueFactory (new PropertyValueFactory<> ("dob"));
        colGender.setCellValueFactory (new PropertyValueFactory<> ("gender"));

    }
    private void setSelectToTxt() {
        tblStudent.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getStId());
                txtname.setText(newSelection.getStName());
                txtAddress.setText(newSelection.getStAddress());
                txtContact.setText(newSelection.getStContact());
                Date date = newSelection.getDob();
                java.util.Date utilDate = new java.util.Date(date.getTime());
                LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                datePick.setValue(localDate);
                cmbGender.setValue(newSelection.getGender());

            }
        });
    }
    @FXML
    private void btnSaveOnAction(ActionEvent actionEvent) throws Exception {
        if(!txtId.getText().isEmpty() && !txtname.getText().isEmpty() && cmbGender.getValue()!= null && datePick.getValue() != null && !txtAddress.getText().isEmpty() && !txtContact.getText().isEmpty()) {
                String dob = String.valueOf(datePick.getValue());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = dateFormat.parse(dob);
                String gender = cmbGender.getValue().toString();
                String id = txtId.getText();
                StudentDTO studentDTO = new StudentDTO(id, txtname.getText(), txtAddress.getText(), txtContact.getText(), parsedDate, gender);
                if (check()) {
                  if (btnCapture.getText().equals("Open Camera") && panePhoto.getImage() != null) {
                    StudentDTO student = studentBO.getStudent(id);
                    if (student != null) {
                        new Alert(Alert.AlertType.ERROR, "Student ID Alredy Registerd!").show();
                    } else {
                        boolean isSaved = studentBO.saveStudent(studentDTO);
                        if (isSaved) {
                            String imageName = id + ".png";
                            String filePath = "D:\\New folder (6)\\HostelManagementSystem\\src\\lk\\ijse\\gdse\\hostelManagement\\view\\assests\\images\\capture\\" + imageName;
                            File outputFile = new File(filePath);
                            try {
                                Image im = panePhoto.getImage();
                                PixelReader pixelReader = im.getPixelReader();
                                int width = (int) im.getWidth();
                                int height = (int) im.getHeight();
                                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

                                for (int y = 0; y < height; y++) {
                                    for (int x = 0; x < width; x++) {
                                        int argb = pixelReader.getArgb(x, y);
                                        image.setRGB(x, y, argb);
                                    }
                                }
                                ImageIO.write(image, "png", outputFile);
                            } catch (IOException e) {
                                new Alert(Alert.AlertType.ERROR, "Failed to save the image!").show();
                                return;
                            }
                            new Alert(Alert.AlertType.CONFIRMATION, "Student Register Succesfully!").show();
                            loadAll();
                            setValueFactory();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Student Not Saved!").show();
                        }
                    }
                }else{new Alert(Alert.AlertType.ERROR, "Please Take Picture For Save Details!").show(); }
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
                        loadAll();
                        setValueFactory();
                        if (btnCapture.getText().equals("Open Camera") && panePhoto.getImage() != null){
                            String imageName = id +".png";
                            String filePath = "D:\\New folder (6)\\HostelManagementSystem\\src\\lk\\ijse\\gdse\\hostelManagement\\view\\assests\\images\\capture\\" + imageName;
                            File outputFile = new File(filePath);
                            try {
                                Image im = panePhoto.getImage();
                                PixelReader pixelReader = im.getPixelReader();
                                int width = (int) im.getWidth();
                                int height = (int) im.getHeight();
                                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

                                for (int y = 0; y < height; y++) {
                                    for (int x = 0; x < width; x++) {
                                        int argb = pixelReader.getArgb(x, y);
                                        image.setRGB(x, y, argb);
                                    }
                                }
                                ImageIO.write(image, "png", outputFile);
                            } catch (IOException e) {
                                new Alert(Alert.AlertType.ERROR, "Failed to save the image!").show();
                                return;
                            }
                        }
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
                        String imageName = id + ".png";
                        String filePath = "D:\\New folder (6)\\HostelManagementSystem\\src\\lk\\ijse\\gdse\\hostelManagement\\view\\assests\\images\\capture\\" + imageName;
                        File outputFile = new File(filePath);
                        if (outputFile.delete()) {
                            System.out.println("Image file deleted successfully!");
                        } else {
                            System.out.println("Failed to delete image file!");
                        }
                        new Alert(Alert.AlertType.CONFIRMATION, "Student Delete Succesfully!").show();
                        clear();
                        loadAll();
                        setValueFactory();
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
                search();
                txtId.setText(st.getStId());
                txtname.setText(st.getStName());
                txtAddress.setText(st.getStAddress());
                txtContact.setText(st.getStContact());
                cmbGender.setValue(st.getGender());
                Date sqlDate = st.getDob();
                java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
                LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                datePick.setValue(localDate);

                String imageName = id + ".png";
                String filePath = "D:\\New folder (6)\\HostelManagementSystem\\src\\lk\\ijse\\gdse\\hostelManagement\\view\\assests\\images\\capture\\" + imageName;
                File outputFile = new File(filePath);
                if (outputFile.exists()) {
                    System.out.println("Get image");
                    Image image = new Image(outputFile.toURI().toString());
                    isCameraEnabled = false;
                    isCapturing = false;
                    btnCapture.setText("Open Camera");
                    btnCapture.setStyle("-fx-background-color: #158392;" +
                            "-fx-background-radius: 50;");
                    panePhoto.setImage(image);
                    if (panePhoto.getParent() instanceof Pane) {
                        isCameraEnabled = false;
                        isCapturing = false;
                        btnCapture.setText("Open Camera");
                        btnCapture.setStyle("-fx-background-color: #158392;" +
                                "-fx-background-radius: 50;");
                    }
                } else {
                    panePhoto.setImage(null);
                }
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
                search();
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
        try {
            txtId.clear();
            txtname.clear();
            txtAddress.clear();
            txtContact.clear();
            cmbGender.setValue(null);
            datePick.setValue(null);
            loadAll();
            setValueFactory();
            isCapturing = false;
            isCameraEnabled = false;
            panePhoto.setImage(null);
            btnCapture.setText("Open Camera");
            btnCapture.setStyle("-fx-background-color:    #158392;");
            btnCapture.setStyle("-fx-background-color: #158392;" +
                    "-fx-background-radius: 50;");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

   private void search(){
        tblStudent.getItems().stream()
                .filter(item -> item.getStId().equals(txtId.getText()) )
                .findAny()
                .ifPresent(item -> {
                    tblStudent.getSelectionModel().select(item);
                    tblStudent.scrollTo(item);
                });

    }
}
