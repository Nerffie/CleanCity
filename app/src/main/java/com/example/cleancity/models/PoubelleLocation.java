package com.example.cleancity.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class PoubelleLocation implements Parcelable{

    private Poubelle poubelle;
    private double lat;
    private double lon;
    private Date timestamp;


    public PoubelleLocation(Poubelle poubelle, double lat, double lon) {
        this.poubelle = poubelle;
        this.lat = lat;
        this.lon = lon;
    }

    public PoubelleLocation() {

    }

    public Poubelle getPoubelle() {
        return poubelle;
    }

    public void setPoubelle(Poubelle poubelle) {
        this.poubelle = poubelle;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    protected PoubelleLocation(Parcel in) {
        poubelle = in.readParcelable(Poubelle.class.getClassLoader());
    }

    public static final Creator<PoubelleLocation> CREATOR = new Creator<PoubelleLocation>() {
        @Override
        public PoubelleLocation createFromParcel(Parcel in) {
            return new PoubelleLocation(in);
        }

        @Override
        public PoubelleLocation[] newArray(int size) {
            return new PoubelleLocation[size];
        }
    };

    public Poubelle getpoubelle() {
        return poubelle;
    }

    public void setpoubelle(Poubelle poubelle) {
        this.poubelle = poubelle;
    }



    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(poubelle, flags);
    }
}
