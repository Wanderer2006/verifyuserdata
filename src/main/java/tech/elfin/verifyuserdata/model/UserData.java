package tech.elfin.verifyuserdata.model;

import java.io.Serializable;

/**
 * POJO-класс для десириализации JSON-запроса из Camunda BPM
 */
public class UserData implements Serializable {
    private String fio;

    private String passSeries;

    private String passNumber;

    private String email;

    private String comment;

    public UserData() {
    }

    public UserData(String fio, String passSeries, String passNumber, String email, String comment) {
        this.fio = fio;
        this.passSeries = passSeries;
        this.passNumber = passNumber;
        this.email = email;
        this.comment = comment;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPassSeries() {
        return passSeries;
    }

    public void setPassSeries(String passSeries) {
        this.passSeries = passSeries;
    }

    public String getPassNumber() {
        return passNumber;
    }

    public void setPassNumber(String passNumber) {
        this.passNumber = passNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "fio='" + fio + '\'' +
                ", passSeries='" + passSeries + '\'' +
                ", passNumber='" + passNumber + '\'' +
                ", email='" + email + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}

