package com.cottonsoft.mediclinic.controller;

import com.cottonsoft.mediclinic.db.Database;
import com.cottonsoft.mediclinic.dto.DoctorDTO;
import com.cottonsoft.mediclinic.dto.UserDTO;
import com.cottonsoft.mediclinic.enums.Gender;
import com.cottonsoft.mediclinic.enums.UIPanes;
import com.cottonsoft.mediclinic.utility.common.MediClinicConstant;
import com.cottonsoft.mediclinic.utility.common.Session;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class DoctorRegistrationFormController {


    public AnchorPane doctorRegistrationContext;
    public JFXButton btnLogout;
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public JFXTextField txtNIC;
    public JFXTextField txtContactNo;
    public JFXTextField txtEmail;
    public JFXTextField txtSpecialization;
    public RadioButton rbtMale;
    public RadioButton rbtFemale;
    public TextArea txtAddress;
    public JFXButton btnSubmitData;
    public Label lblChat;
    public Label lblChat1;

    public void initialize() throws IOException {
        Session.setVisiblePane(doctorRegistrationContext);
        loadDoctorDetails();
    }

    private void loadDoctorDetails() {
        UserDTO selectedUser = Session.getSelectedUser();
        txtFirstName.setText(selectedUser.getFirstName());
        txtLastName.setText(selectedUser.getLastName());
        txtNIC.setText(selectedUser.getNic());
        txtEmail.setText(selectedUser.getEmail());
        lblChat.setText(String.format("Hi! Dr %s",selectedUser.getFirstName()));

    }

    public void logoutBtnOnAction(ActionEvent actionEvent) throws IOException {
        int result = JOptionPane.showConfirmDialog (null, "Do you want to Logout without completing the Step?","Warning",JOptionPane.YES_NO_OPTION);
        if(JOptionPane.YES_OPTION==result){
            Session.setSelectedDoctor(null);
            Session.setSelectedUser(null);
            lodeUI(UIPanes.LOGIN_FORM);
        }
    }

    public void chooseProfPicBtnOnAction(ActionEvent actionEvent) {
        // TODO: 3/19/2023  
    }

    public void submitDataBtnOnAction(ActionEvent actionEvent) throws IOException {

        if(!MediClinicConstant.EMPTY_STRING.equals(txtContactNo.getText())
            && !MediClinicConstant.EMPTY_STRING.equals(txtSpecialization.getText())
            && !MediClinicConstant.EMPTY_STRING.equals(txtAddress.getText())){
            DoctorDTO newDoctorDetails = new DoctorDTO(txtFirstName.getText().trim(),
                    txtLastName.getText().trim(),
                    txtNIC.getText().trim(),
                    txtContactNo.getText().trim(),
                    txtEmail.getText().trim(),
                    txtSpecialization.getText().trim(),
                    txtAddress.getText().trim(),
                    rbtMale.isSelected() ? Gender.MALE : Gender.FEMALE);
            Database.doctorTable.add(newDoctorDetails);
            Session.setSelectedDoctor(newDoctorDetails);
            new Alert(Alert.AlertType.CONFIRMATION,"Registration Successful!").show();
            lodeUI(UIPanes.DOCTOR_DASHBOARD_FORM);
        }else {
            new Alert(Alert.AlertType.WARNING,"Complete The All Fields!").show();
        }
    }
    private void lodeUI(UIPanes uiPane) throws IOException {

        Stage stage= (Stage) Session.getVisiblePane().getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(String.format(".%s",uiPane.getUiPath())))));
        stage.centerOnScreen();
    }
}
