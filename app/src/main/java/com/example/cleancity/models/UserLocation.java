package com.example.cleancity.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class UserLocation implements Parcelable {

    private User user;
    private double lat;
    private double lon;
    private Date timestamp;


    public UserLocation(User user, double lat, double lon) {
        this.user = user;
        this.lat = lat;
        this.lon = lon;
    }

    public UserLocation() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    protected UserLocation(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        lat = in.readDouble();
        lon = in.readDouble();
    }

    public static final Creator<UserLocation> CREATOR = new Creator<UserLocation>() {
        @Override
        public UserLocation createFromParcel(Parcel in) {
            return new UserLocation(in);
        }

        @Override
        public UserLocation[] newArray(int size) {
            return new UserLocation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
    }
}
