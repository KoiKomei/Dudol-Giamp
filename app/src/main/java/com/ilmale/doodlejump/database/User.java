package com.ilmale.doodlejump.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "User")
public class User {

    @ColumnInfo
    private int id;

    @ColumnInfo(name="Nome_Utente")
    private String username;

    @ColumnInfo(name="Password")
    private String password;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="Email")
    private String email;

    @ColumnInfo(name="Money")
    private int money;

    @ColumnInfo(name="Punteggio")
    private int punteggio;

    @ColumnInfo(name="Latitudine")
    private double lat;

    @ColumnInfo(name="Longitudine")
    private double longi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMoney(){return money;}

    public void setMoney(int money){this.money=money;}

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }
}
