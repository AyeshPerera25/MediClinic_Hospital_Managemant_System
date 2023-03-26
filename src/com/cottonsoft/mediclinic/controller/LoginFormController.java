package com.cottonsoft.mediclinic.controller;

import com.cottonsoft.mediclinic.db.Database;
import com.cottonsoft.mediclinic.dto.DoctorDTO;
import com.cottonsoft.mediclinic.dto.UserDTO;
import com.cottonsoft.mediclinic.enums.AccountType;
import com.cottonsoft.mediclinic.enums.UIPanes;
import com.cottonsoft.mediclinic.utility.common.Session;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginFormController {

    public JFXTextField txtEmail;
    public JFXRadioButton rBtnDoctor;
    public JFXRadioButton rBtnPatient;
    public AnchorPane loginContext;
    public JFXPasswordField txtPwd;

    public void initialize(){
        Session.setVisiblePane(loginContext);
    }

    public void signInOnAction(ActionEvent actionEvent) throws IOException {
        String email = txtEmail.getText().trim().toLowerCase();
        String password = txtPwd.getText().trim();
        AccountType accountType= rBtnDoctor.isSelected()? AccountType.DOCTOR : AccountType.PATIENT;

        Optional<UserDTO> selectedUser = Database.userTable.stream().filter(user -> user.getEmail().equals(email)).findFirst(); //filter the user
        if(selectedUser.isPresent()){
            if(selectedUser.get().getPassword().equals(password) && selectedUser.get().getAccountType().equals(accountType)){
                Session.setSelectedUser(selectedUser.get());
                checkLoginUser();
            }else {
                new Alert(Alert.AlertType.WARNING,"Password or User Type Incorrect! Please Check Again").show();
            }
        }else {
            new Alert(Alert.AlertType.WARNING,String.format("User email %s not registered on system! Please Create an Account ",email)).show();
        }
    }

    private void checkLoginUser() throws IOException {
        if (AccountType.DOCTOR.equals(Session.getSelectedUser().getAccountType())){
            checkDoctor();
        }else {
            checkPatient();
        }
    }

    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        lodeUI(UIPanes.SIGNUP_FORM);
    }

    private void lodeUI(UIPanes uiPane) throws IOException {

        Stage stage= (Stage) Session.getVisiblePane().getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(String.format(".%s",uiPane.getUiPath())))));
        stage.centerOnScreen();
    }

    private void checkDoctor() throws IOException {

        if(null==Session.getSelectedDoctor()){
            Optional<DoctorDTO> doctorDto = Database.doctorTable.stream().filter(e -> e.getNic().equals(Session.getSelectedUser().getNic())).findFirst();
            if(doctorDto.isPresent()){
                Session.setSelectedDoctor(doctorDto.get());
                new Alert(Alert.AlertType.CONFIRMATION,"Login Successful!").show();
                lodeUI(UIPanes.DOCTOR_DASHBOARD_FORM);
            }else {
                lodeUI(UIPanes.DOCTOR_REGISTRATION_FORM);
            }
        }else if (Session.getSelectedDoctor().getNic().equals(Session.getSelectedUser().getNic())){
            new Alert(Alert.AlertType.CONFIRMATION,"Login Successful!").show();
                lodeUI(UIPanes.DOCTOR_DASHBOARD_FORM);
        }else {
            new Alert(Alert.AlertType.ERROR,"DoctorDTO Loading Error!").show();
        }
    }

    private void checkPatient() {
        // TODO: 3/19/2023
    }
}
