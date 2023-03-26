package com.cottonsoft.mediclinic.controller;

import com.cottonsoft.mediclinic.enums.UIPanes;
import com.cottonsoft.mediclinic.utility.common.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientManagementFormController {
    public AnchorPane patientManagementContext;
    public TableView tblPatientTable;
    public TableColumn colNIC;
    public TableColumn colFirstName;
    public TableColumn colLastName;
    public TableColumn colDOB;
    public TableColumn colAge;
    public TableColumn colContact;
    public TableColumn colEmail;
    public TableColumn colAddress;


    public void initialize(){
        Session.setVisiblePane(patientManagementContext);
        loadAllData("");
       
    }

    private void loadAllData(String s) {
    }


    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        lodeUI(UIPanes.DOCTOR_DASHBOARD_FORM);
    }
    private void lodeUI(UIPanes uiPane) throws IOException {
        Stage stage= (Stage) Session.getVisiblePane().getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(String.format(".%s",uiPane.getUiPath())))));
        stage.centerOnScreen();
    }

    public void searchButtonOnAction(ActionEvent actionEvent) {
    }
}
