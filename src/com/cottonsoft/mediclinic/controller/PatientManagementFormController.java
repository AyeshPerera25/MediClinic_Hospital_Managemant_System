package com.cottonsoft.mediclinic.controller;

import com.cottonsoft.mediclinic.db.Database;
import com.cottonsoft.mediclinic.dto.PatientDTO;
import com.cottonsoft.mediclinic.enums.UIPanes;
import com.cottonsoft.mediclinic.utility.common.Session;
import com.cottonsoft.mediclinic.view.tm.PatientTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.util.resources.LocaleData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.util.Date;

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
    public TableColumn colGender;
    public TextField txtSearch;


    public void initialize(){
        Session.setVisiblePane(patientManagementContext);
        loadAllData("");
        viewDataOnTable();
        initiateProperties();
       
    }

    private void initiateProperties() {
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {loadAllData(newValue);});
    }

    private void viewDataOnTable() {
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("genderType"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private void loadAllData(String s) {
        s=s.toLowerCase();
        ObservableList<PatientTM> tmList = FXCollections.observableArrayList();
        // TODO: 3/26/2023  Update to stream -->filter
        for (PatientDTO dto: Database.patientTable){
            // TODO: 3/26/2023 Optimise this search also
            if(dto.getFirstName().toLowerCase().contains(s)
                    ||dto.getLastName().toLowerCase().contains(s)
                    ||dto.getEmail().toLowerCase().contains(s)
                    || dto.getNic().toLowerCase().contains(s)){
                tmList.add(
                        new PatientTM(
                                dto.getNic(),dto.getFirstName(),dto.getLastName(),new SimpleDateFormat("yyyy-MM-dd").format(dto.getDateOfBirth()),dto.getGenderType(), dto.getAddress(),
                                dto.getEmail(), dto.getContact(), Period.between(dto.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears()));
            }

        }
        tblPatientTable.setItems(tmList);
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
