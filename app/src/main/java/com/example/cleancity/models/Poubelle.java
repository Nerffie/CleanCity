package com.example.cleancity.models;


import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Poubelle implements Parcelable {

    int id;
    int temp;
    int rempli; // Pourcentage
    String avatar;
    String etat;

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    protected Poubelle(Parcel in) {
        id = in.readInt();
        temp = in.readInt();
        rempli = in.readInt();
        avatar = in.readString();
    }

    public static final Creator<Poubelle> CREATOR = new Creator<Poubelle>() {
        @Override
        public Poubelle createFromParcel(Parcel in) {
            return new Poubelle(in);
        }

        @Override
        public Poubelle[] newArray(int size) {
            return new Poubelle[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(temp);
        dest.writeInt(rempli);
        dest.writeString(avatar);
    }
}
