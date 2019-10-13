package com.example.cleancity.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Signal implements Parcelable {
    int id;
    double lat;
    double lon;
    String photo;
    String type;
    String desc;
    String date;

    protected Signal(Parcel in) {
        id = in.readInt();
        lat = in.readDouble();
        lon = in.readDouble();
        photo = in.readString();
        type = in.readString();
        desc = in.readString();
        date = in.readString();
    }

    public static final Creator<Signal> CREATOR = new Creator<Signal>() {
        @Override
        public Signal createFromParcel(Parcel in) {
            return new Signal(in);
        }

        @Override
        public Signal[] newArray(int size) {
            return new Signal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeString(photo);
        dest.writeString(type);
        dest.writeString(desc);
        dest.writeString(date);
    }
//    int idUser;
}
