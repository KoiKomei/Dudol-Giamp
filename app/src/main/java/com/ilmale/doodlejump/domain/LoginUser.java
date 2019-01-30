package com.ilmale.doodlejump.domain;

public class LoginUser {

    private static final LoginUser ourInstance = new LoginUser();

    public static LoginUser getInstance() {
        return ourInstance;
    }

    private LoginUser() {}

    private String username = null;
    private String password = null;
    private String email = null;
    private int money = 0;
    private int punteggio = 0;
    private double lat = 0.0;
    private double longi = 0.0;

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
