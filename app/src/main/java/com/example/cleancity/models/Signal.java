package com.example.cleancity.models;


import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Signal implements Serializable {
    int id;
    double lat;
    double lon;
    String photo;
    String type;
    String desc;
    String date;
//    int idUser;
}
