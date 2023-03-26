package com.cottonsoft.mediclinic.enums;

public enum UIPanes {
    LOGIN_FORM("./view/LoginForm.fxml"),
    SIGNUP_FORM("./view/SignupForm.fxml"),
    DOCTOR_DASHBOARD_FORM("./view/DoctorDashboardForm.fxml"),
    DOCTOR_REGISTRATION_FORM("./view/DoctorRegistrationForm.fxml"),
    PATIENT_MANAGEMENT_FORM("./view/PatientManagementForm.fxml");

    private final String uiPath;

    UIPanes(String uiPath) {
        this.uiPath = uiPath;
    }
    public String getUiPath(){
        return uiPath;
    }
}
