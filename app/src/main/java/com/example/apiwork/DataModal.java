package com.example.apiwork;

public class DataModal {

    // string variables for our name and job
    private String user;
    private String konfiguracia;
    private int zena;
    String Img="";

    public DataModal(String user, String konfiguracia, int zena ,String Image) {
        this.user = user;
        this.konfiguracia = konfiguracia;
        this.zena = zena;
        this.Img=Image;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public String getImg() {
        return Img;
    }

    public void setImg(String IMG) {
        this.Img = IMG;
    }

    public String getKonfiguracia() {
        return konfiguracia;
    }

    public void setKonfiguracia(String konfiguracia) {
        this.konfiguracia = konfiguracia;
    }
    public int getZena() {
        return zena;
    }

    public void setZena(int zena) {
        this.zena = zena;
    }

}
