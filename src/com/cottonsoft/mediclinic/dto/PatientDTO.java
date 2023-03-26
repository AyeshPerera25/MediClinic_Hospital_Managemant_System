package com.cottonsoft.mediclinic.dto;

import com.cottonsoft.mediclinic.db.Database;
import com.cottonsoft.mediclinic.enums.Gender;

import java.util.Date;

public class PatientDTO {

    private String nic;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Gender  genderType;
    private String address;
    private String email;
    private String contact;

    public PatientDTO() {
    }
               
    public PatientDTO(String nic, String firstName, String lastName, Date dateOfBirth, Gender genderType, String address, String email, String contact) {
        this.nic = nic;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.genderType = genderType;
        this.address = address;
        this.email = email;
        this.contact = contact;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGenderType() {
        return genderType;
    }

    public void setGenderType(Gender genderType) {
        this.genderType = genderType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
