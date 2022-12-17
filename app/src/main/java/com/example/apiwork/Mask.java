package com.example.apiwork;

import android.os.Parcel;
import android.os.Parcelable;

public class Mask implements Parcelable {
    private int id_zakaza;
    private String User;
    private String Konfiguracia;
    private int Zena;
    private String Img;

    public Mask(int Id_zakaza, String user, String konfiguracia, int zena, String Image) {
        this.id_zakaza = Id_zakaza;
        User = user;
        Konfiguracia = konfiguracia;
        Zena = zena;
        Img=Image;
    }

    protected Mask(Parcel in) {
        id_zakaza = in.readInt();
        User = in.readString();
        Konfiguracia = in.readString();
        Zena = in.readInt();
        Img=in.readString();
    }

    public static final Creator<Mask> CREATOR = new Creator<Mask>() {
        @Override
        public Mask createFromParcel(Parcel in) {
            return new Mask(in);
        }

        @Override
        public Mask[] newArray(int size) {
            return new Mask[size];
        }
    };


    public void setId_zakaza(int Id_zakaza) {
        this.id_zakaza = Id_zakaza;
    }

    public void setUser(String user) {
        User = user;
    }

    public void setKonfiguracia(String konfiguracia) {
        Konfiguracia = konfiguracia;
    }

    public void setZena(int zena) {
        Zena = zena;
    }

    public void setImg(String Image) {
        Img = Image;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id_zakaza);
        parcel.writeString(User);
        parcel.writeString(Konfiguracia);
        parcel.writeInt(Zena);
        parcel.writeString(Img);
    }

    public String getUser() {
        return User;
    }

    public String getKonfiguracia() {
        return Konfiguracia;
    }

    public int getZena() {
        return Zena;
    }

    public int getId_zakaza() {
        return id_zakaza;
    }
    public String getImg() {
        return Img;
    }
}
