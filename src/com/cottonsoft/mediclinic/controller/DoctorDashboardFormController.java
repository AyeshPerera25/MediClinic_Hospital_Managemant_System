package com.cottonsoft.mediclinic.controller;

import com.cottonsoft.mediclinic.db.Database;
import com.cottonsoft.mediclinic.dto.DoctorDTO;
import com.cottonsoft.mediclinic.dto.UserDTO;
import com.cottonsoft.mediclinic.enums.DoctorDashboardContent;
import com.cottonsoft.mediclinic.enums.Gender;
import com.cottonsoft.mediclinic.enums.UIPanes;
import com.cottonsoft.mediclinic.utility.common.Session;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class DoctorDashboardFormController {

    public AnchorPane doctorDashboardContext;
    public Label lblDate;
    public Label lblTime;
    public Label lblDoctorName;
    public Label lblChat;
    public ImageView imgFemaleDoctor;
    public ImageView imgMaleDoctor;
    public ImageView imgPatientsManagment;
    public JFXButton btnLogout;
    public JFXButton btnIncomeReports;
    public JFXButton btnPatientsReport;
    public JFXButton btnPrescriptions;
    public JFXButton btnAllAppointments;
    public JFXButton btnPatientManagment;
    public ImageView imgWaitingPatient;
    private static ImageView visibleImage;
    private static boolean focusLock;
    private static Timeline dashBoardTimeline;
    public ImageView imgDoctorAndPrescriptions;
    public ImageView imgDoctorCheckingIncome;
    public ImageView imgDoctorCheckingPatientReport;

    public void initialize() throws IOException {

        initializeData();
        Session.setVisiblePane(doctorDashboardContext);
    }

    private void initializeData() throws IOException {
        UserDTO userDTO =Session.getSelectedUser();
        DoctorDTO doctorDto = Session.getSelectedDoctor();
       //Date Initiate
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Clock Time initiate
        Timeline timeline =new Timeline(
                new KeyFrame(Duration.seconds(0), event -> {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
                    lblTime.setText(LocalTime.now().format(dateTimeFormatter));
                }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        lblDoctorName.setText(String.format("Dr. %s",userDTO.getFirstName()+ " "+ userDTO.getLastName()));
        lblChat.setText(String.format("HI! Welcome to your dashboard Dr. %s",userDTO.getFirstName()));
        setDoctorImage(doctorDto.getGender());
        dashBoardTimeline =new Timeline();
        initiateProperties();

    }

    private void setDoctorImage(Gender gender) {
        if(Gender.MALE.equals(gender)){
            imgMaleDoctor.setVisible(true);
            imgFemaleDoctor.setVisible(false);
            visibleImage=imgMaleDoctor;
        } else {
            imgMaleDoctor.setVisible(false);
            imgFemaleDoctor.setVisible(true);
            visibleImage=imgFemaleDoctor;
        }
    }

    private void checkDoctor() throws IOException {

        if(null==Session.getSelectedDoctor()){
            Optional<DoctorDTO> doctorDto = Database.doctorTable.stream().filter(e -> e.getNic().equals(Session.getSelectedUser().getNic())).findFirst();
            if(doctorDto.isPresent()){
                Session.setSelectedDoctor(doctorDto.get());
            }else {
                lodeUI(UIPanes.DOCTOR_REGISTRATION_FORM);
            }
        }
    }
    public void checkUser() throws IOException {
        if (null == Session.getSelectedUser()){
           lodeUI(UIPanes.LOGIN_FORM);
        }
    }
    private void lodeUI(UIPanes uiPane) throws IOException {
        Stage stage= (Stage) Session.getVisiblePane().getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(String.format(".%s",uiPane.getUiPath())))));
        stage.centerOnScreen();
    }
    // TODO: 3/19/2023
    public void logoutBtnOnAction(ActionEvent actionEvent) throws IOException {
        int result = JOptionPane.showConfirmDialog (null, "Do you want to Logout from your Dashboard?","Warning",JOptionPane.YES_NO_OPTION);
        if(JOptionPane.YES_OPTION==result){
            Session.setSelectedDoctor(null);
            Session.setSelectedUser(null);
            lodeUI(UIPanes.LOGIN_FORM);
        }
    }

    private synchronized void imageTransition(){
        ImageView selectedDocImage = imgMaleDoctor.isVisible()? imgMaleDoctor:imgFemaleDoctor;
        imageTransition(selectedDocImage, visibleImage);
    }
    private synchronized void imageTransition(ImageView inImage , ImageView outImage){

        if(outImage.isVisible()){
            if(dashBoardTimeline.getStatus() ==Animation.Status.RUNNING){
                dashBoardTimeline.stop();
            }
            dashBoardTimeline.getKeyFrames().setAll(fadeInOutImageKeyFrames(inImage,outImage));
            //Timeline timeline =new Timeline( );
            dashBoardTimeline.play();
        }
    }
    private void initiateProperties(){
        btnPatientManagment.hoverProperty()
                .addListener((observable, oldValue, newValue) -> {
                            dashboardContentBtnFocused(DoctorDashboardContent.PATIENTS_MANAGEMENT,newValue);
                });
        btnAllAppointments.hoverProperty()
                .addListener((observable, oldValue, newValue) -> {
                            dashboardContentBtnFocused(DoctorDashboardContent.ALL_APPOINTMENTS,newValue);
                });
        btnPrescriptions.hoverProperty()
                .addListener((observable, oldValue, newValue) -> {
                    dashboardContentBtnFocused(DoctorDashboardContent.PRESCRIPTIONS,newValue);
                });
        btnPatientsReport.hoverProperty()
                .addListener((observable, oldValue, newValue) -> {
                    dashboardContentBtnFocused(DoctorDashboardContent.PATIENT_REPORTS,newValue);
                });
        btnIncomeReports.hoverProperty()
                .addListener((observable, oldValue, newValue) -> {
                    dashboardContentBtnFocused(DoctorDashboardContent.INCOME_REPORTS,newValue);
                });

    }
    private void dashboardContentBtnFocused(DoctorDashboardContent selectedContent, Boolean isFocused){

        switch (selectedContent){
            case PATIENTS_MANAGEMENT:
                if(isFocused){
                    imageTransition(imgPatientsManagment, visibleImage);
                }else{
                    imageTransition();
                }
                break;

            case ALL_APPOINTMENTS:
                if(isFocused){
                    imageTransition(imgWaitingPatient, visibleImage);
                }else{
                    imageTransition();
                }
                break;

            case PRESCRIPTIONS:
                if(isFocused){
                    imageTransition(imgDoctorAndPrescriptions, visibleImage);
                }else{
                    imageTransition();
                }
                break;

            case PATIENT_REPORTS:
                if(isFocused){
                    imageTransition(imgDoctorCheckingPatientReport, visibleImage);
                }else{
                    imageTransition();
                }

                break;
            case INCOME_REPORTS:
                if(isFocused){
                    imageTransition(imgDoctorCheckingIncome, visibleImage);
                }else{
                    imageTransition();
                }
                break;

            default:
                imageTransition();
        }


    }

    private KeyFrame[] fadeInOutImageKeyFrames(ImageView inImage,ImageView outImage) {

        KeyFrame k1 = new KeyFrame(Duration.seconds(0),new KeyValue(outImage.opacityProperty(),outImage.getOpacity()));
        KeyFrame k2= new KeyFrame(Duration.seconds(0.8), new KeyValue(outImage.opacityProperty(), 0.0));
        KeyFrame k3= new KeyFrame(Duration.seconds(0.8) ,event -> {visibleImage=inImage;});
        KeyFrame k4= new KeyFrame(Duration.seconds(0.8), new KeyValue(inImage.opacityProperty(), inImage.getOpacity()));
        KeyFrame k5= new KeyFrame(Duration.seconds(1.6), new KeyValue(inImage.opacityProperty(), 1.0));

        return new KeyFrame[]{k1,k2,k3,k4,k5};
    }

    public void patientManagementButtonOnAction(ActionEvent actionEvent) throws IOException {

        lodeUI(UIPanes.PATIENT_MANAGEMENT_FORM);



    }
}
