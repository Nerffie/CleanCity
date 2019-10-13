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
public class Signal implements Serializable, Parcelable {
    int id;
    double lat;
    double lon;
    String photo;
    String type;
    String desc;
    String date;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.write
    }
//    int idUser;
}
