package com.cottonsoft.mediclinic.controller;

import com.cottonsoft.mediclinic.db.Database;
import com.cottonsoft.mediclinic.dto.UserDTO;
import com.cottonsoft.mediclinic.enums.AccountType;
import com.cottonsoft.mediclinic.enums.UIPanes;
import com.cottonsoft.mediclinic.utility.common.MediClinicConstant;
import com.cottonsoft.mediclinic.utility.common.Session;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

public class SignupFormController {
    public JFXRadioButton rBtnDoctor;
    public JFXRadioButton rBtnPatient;
    public ImageView imgDoctors;
    public ImageView imgPatients;
    public ImageView imgEmailValid;
    public ImageView imgNICValid;
    public ImageView imgEmailInvalid;
    public ImageView imgPwdTypAgainInvalid;
    public ImageView imgPwdTypAgainValid;
    public ImageView imgNICInvalid;
    public JFXButton btnSignUp;

    private boolean emailValid = false;
    private boolean nicValid = false;
    private boolean passwordValid = false;
    public AnchorPane signupContext;
    public JFXPasswordField txtPwd1;
    public JFXPasswordField txtPwd11;
    public JFXTextField txtNICNo;
    public JFXTextField txtEmail;
    public Label lblEmailInValidReason;
    public Label lblNICInValidReason;
    public Label lblPwdInValidReason;
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;

    public void initialize(){
        Session.setVisiblePane(signupContext);
        initiateEmailValidation();
        initiateNICValidation();
        initiatePasswordValidation();
    }

    private void initiatePasswordValidation() {
        txtPwd1.textProperty().addListener((observable, oldValue, newValue) -> {passwordValid = validatePassword();});
        txtPwd11.textProperty().addListener((observable, oldValue, newValue) -> {passwordValid = validatePassword();});
    }

    private void initiateNICValidation() {
        txtNICNo.textProperty().addListener((observable, oldValue, newValue) -> {

            String nic = newValue.trim().toLowerCase();
            if (!MediClinicConstant.EMPTY_STRING.equals(nic) && nic.length() == MediClinicConstant.NUMBER_10) {
                if (String.valueOf(nic.charAt(nic.length() - MediClinicConstant.NUMBER_1)).equals(MediClinicConstant.LAST_NIC_CHARACTER)) {
                    Optional<UserDTO> userSelected = Database.userTable.stream().filter(userDTO -> userDTO.getNic().equals(nic)).findFirst();
                    if (userSelected.isPresent()) {
                        nicValid = showInvalid(imgNICValid,imgNICInvalid);
                        lblNICInValidReason.setText("NIC Already Registered!");
                    } else {
                        nicValid = showValid(imgNICValid,imgNICInvalid);
                        lblNICInValidReason.setText(MediClinicConstant.EMPTY_STRING);
                    }
                } else {
                    nicValid = showInvalid(imgNICValid,imgNICInvalid);
                    lblNICInValidReason.setText("Invalid NIC Sequence!");
                }
            } else if (nic.length() != MediClinicConstant.NUMBER_10 && nic.length()>MediClinicConstant.NUMBER_0 ) {
                nicValid = showInvalid(imgNICValid,imgNICInvalid);
                lblNICInValidReason.setText("Invalid NIC Sequence!");
            } else {
                nicValid = showNotVisible(imgNICValid,imgNICInvalid);
                lblNICInValidReason.setText(MediClinicConstant.EMPTY_STRING);
            }
        });
    }

    private void initiateEmailValidation() {

        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> {

            if(!MediClinicConstant.EMPTY_STRING.equals(newValue)){
                String regex = MediClinicConstant.EMAIL_VALIDATOR_REGEX; //For validating email sequences
                Pattern pattern = Pattern.compile(regex);
                if (pattern.matcher(newValue).matches()) {
                    Optional<UserDTO> userSelected = Database.userTable.stream().filter(userDTO -> userDTO.getEmail().equals(newValue)).findFirst();
                    if (userSelected.isPresent()) {
                        emailValid=showInvalid(imgEmailValid,imgEmailInvalid);
                        lblEmailInValidReason.setText("Email Already Registered!");
                    } else {
                        emailValid=showValid(imgEmailValid,imgEmailInvalid);
                        lblEmailInValidReason.setText(MediClinicConstant.EMPTY_STRING);
                    }
                } else {
                    emailValid=showInvalid(imgEmailValid,imgEmailInvalid);
                    lblEmailInValidReason.setText("Invalid Email Sequence!");
                }
            }else {
                emailValid= showNotVisible(imgEmailValid,imgEmailInvalid);
                lblEmailInValidReason.setText(MediClinicConstant.EMPTY_STRING);
            }
        });
    }
    private boolean showValid(ImageView imageViewValid,ImageView imageViewInvalid){
        imageViewValid.setVisible(true);
        imageViewInvalid.setVisible(false);
        return true;
    }
    private boolean showInvalid(ImageView imageViewValid,ImageView imageViewInvalid){
        imageViewValid.setVisible(false);
        imageViewInvalid.setVisible(true);
        return false;
    }
    private boolean showNotVisible(ImageView imageViewValid, ImageView imageViewInvalid){
        imageViewValid.setVisible(false);
        imageViewInvalid.setVisible(false);
        return false;
    }

    // TODO: 3/24/2023 change to a faded animation like do in doctor dashboard 
    public void doctorRBtnOnAction(ActionEvent actionEvent) {
        if(rBtnDoctor.isSelected()&&imgPatients.isVisible()){
            imgPatients.setVisible(false);
            imgDoctors.setVisible(true);
        }

    }

    public void patientRBtnOnAction(ActionEvent actionEvent) {
        if(rBtnPatient.isSelected() && imgDoctors.isVisible()){
            imgDoctors.setVisible(false);
            imgPatients.setVisible(true);
        }
    }

    public void alreadyHaveAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        backTOLoginUI();
    }
    public boolean validatePassword(){
        if(!MediClinicConstant.EMPTY_STRING.equals(txtPwd1.getText().trim()) && !MediClinicConstant.EMPTY_STRING.equals(txtPwd11.getText().trim())){
            if(txtPwd1.getText().trim().equals(txtPwd11.getText().trim())) {
                lblPwdInValidReason.setText(MediClinicConstant.EMPTY_STRING);
                return showValid(imgPwdTypAgainValid,imgPwdTypAgainInvalid);

            }else {
                lblPwdInValidReason.setText("Password Not Matched!");
                return showInvalid(imgPwdTypAgainValid,imgPwdTypAgainInvalid);
            }
        }else {
            lblPwdInValidReason.setText(MediClinicConstant.EMPTY_STRING);
            return showNotVisible(imgPwdTypAgainValid,imgPwdTypAgainInvalid);
        }
    }

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {

        if(nicValid && emailValid && passwordValid){
            if(MediClinicConstant.EMPTY_STRING.equals(txtFirstName.getText().trim())
                    ||MediClinicConstant.EMPTY_STRING.equals(txtLastName.getText().trim())){
                new Alert(Alert.AlertType.WARNING,"Please Add Your Name Correctly!").show();
            }else { //add user to DB
                Database.userTable.add(
                        new UserDTO(txtNICNo.getText().trim().toLowerCase(),
                                txtFirstName.getText().trim(),
                                txtLastName.getText().trim(),
                                txtEmail.getText().trim().toLowerCase(),
                                txtPwd1.getText(),
                                rBtnDoctor.isSelected() ? AccountType.DOCTOR : AccountType.PATIENT));
                new Alert(Alert.AlertType.CONFIRMATION, "User Successfully Registered!").show();
                backTOLoginUI();
            }
        } else if (!nicValid && MediClinicConstant.EMPTY_STRING.equals(txtNICNo.getText().trim())) {
            new Alert(Alert.AlertType.WARNING,"Please Add Your NIC No!").show();
        } else if (!emailValid && MediClinicConstant.EMPTY_STRING.equals(txtEmail.getText().trim()) ) {
            new Alert(Alert.AlertType.WARNING,"Please Add Your Email!").show();
        } else if (!passwordValid && (MediClinicConstant.EMPTY_STRING.equals(txtPwd1.getText().trim()) || MediClinicConstant.EMPTY_STRING.equals(txtPwd11.getText().trim()))) {
            new Alert(Alert.AlertType.WARNING,"Please Add Your Password Correct!").show();
        } else {
            new Alert(Alert.AlertType.WARNING,"SignIn Failed Due to- "+lblNICInValidReason.getText()+","
                                                                                +lblEmailInValidReason.getText()+","
                                                                                +lblPwdInValidReason.getText()).show();
        }
    }

    // TODO: Change to LoadUI
    private void backTOLoginUI() throws IOException {
       // Stage stage= (Stage) signupContext.getScene().getWindow();
        Stage stage= (Stage) Session.getVisiblePane().getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(String.format(".%s", UIPanes.LOGIN_FORM.getUiPath())))));
        stage.centerOnScreen();
    }
}

