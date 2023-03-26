package com.cottonsoft.mediclinic.utility.common;

import com.cottonsoft.mediclinic.dto.DoctorDTO;
import com.cottonsoft.mediclinic.dto.UserDTO;
import javafx.scene.layout.AnchorPane;

public class Session {
    private static UserDTO selectedUser;
    private static DoctorDTO selectedDoctor;
    private static AnchorPane visiblePane ;

    public static UserDTO getSelectedUser() {
        return selectedUser;
    }

    public static void setSelectedUser(UserDTO selectedUser) {
        Session.selectedUser = selectedUser;
    }

    public static DoctorDTO getSelectedDoctor() {
        return selectedDoctor;
    }

    public static void setSelectedDoctor(DoctorDTO selectedDoctor) {
        Session.selectedDoctor = selectedDoctor;
    }

    public static AnchorPane getVisiblePane() {
        return visiblePane;
    }

    public static void setVisiblePane(AnchorPane visiblePane) {
        Session.visiblePane = visiblePane;
    }
}
