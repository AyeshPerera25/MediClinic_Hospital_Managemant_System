package com.cottonsoft.mediclinic.db;

import com.cottonsoft.mediclinic.dto.DoctorDTO;
import com.cottonsoft.mediclinic.dto.PatientDTO;
import com.cottonsoft.mediclinic.dto.UserDTO;
import com.cottonsoft.mediclinic.enums.AccountType;
import com.cottonsoft.mediclinic.enums.Gender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Database {
    public static ArrayList<UserDTO> userTable = new ArrayList();
    public static ArrayList<DoctorDTO> doctorTable = new ArrayList<>();
    public static ArrayList<PatientDTO> patientTable = new ArrayList<>();

    static {
        userTable.add(new UserDTO("953602113v","Ayesh","Perera","givanthaperera25@gmail.com","20742", AccountType.PATIENT));
        userTable.add(new UserDTO("963602113v","Samuduni","Wijerathna","samuduniwijerathna25@gmail.com","1234", AccountType.DOCTOR));
        userTable.add(new UserDTO("111111111v","Kasun","Perera","k@k.kk","1234",AccountType.DOCTOR));
        userTable.add(new UserDTO("222222222v","Deepika","Kumari","d@d.dd","1234",AccountType.DOCTOR));

        doctorTable.add(new DoctorDTO("Kasun","Perera","111111111v","1234567890","k@k.kk","","", Gender.MALE));
        doctorTable.add(new DoctorDTO("Deepika","Kumari","222222222v","1234567890","d@d.dd","","", Gender.FEMALE));

        try {
            patientTable.add((new PatientDTO( "333333333v","Kusal","Mendis",new SimpleDateFormat("yyyy-MM-dd").parse("1995-12-25"),
                    Gender.MALE,"Moratuwa,Sri Lanka","kusal@gmail.com","123456789")));
            patientTable.add((new PatientDTO( "444444444v","Angelo","Mathews",new SimpleDateFormat("yyyy-MM-dd").parse("1996-01-25"),
                    Gender.MALE,"Colombo,Sri Lanka","angelo@gmail.com","987654321")));
            patientTable.add((new PatientDTO( "55555555v","Dasun","Shanaka",new SimpleDateFormat("yyyy-MM-dd").parse("2000-11-25"),
                    Gender.MALE,"Kurunagala,Sri Lanka","dasun@gmail.com","1231231234")));
            patientTable.add((new PatientDTO( "666666666v","Pathum","Nishshanka",new SimpleDateFormat("yyyy-MM-dd").parse("2001-02-25"),
                    Gender.MALE,"Kaluthara,Sri Lanka","pathum@gmail.com","77878878778")));
            patientTable.add((new PatientDTO( "777777777v","Dushmantha","Chameera",new SimpleDateFormat("yyyy-MM-dd").parse("1998-12-01"),
                    Gender.MALE,"Avissawella,Sri Lanka","dushmantha@gmail.com","34786578346")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }


}
