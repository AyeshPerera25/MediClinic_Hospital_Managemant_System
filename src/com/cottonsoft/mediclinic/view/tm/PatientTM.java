package com.cottonsoft.mediclinic.view.tm;

import com.cottonsoft.mediclinic.enums.Gender;

import java.util.Date;

public class PatientTM {
    private String nic;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Gender genderType;
    private String address;
    private String email;
    private String contact;
    private int age;

    public PatientTM() {
    }

    public PatientTM(String nic, String firstName, String lastName, String dateOfBirth, Gender genderType, String address, String email, String contact, int age) {
        this.nic = nic;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.genderType = genderType;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.age = age;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
