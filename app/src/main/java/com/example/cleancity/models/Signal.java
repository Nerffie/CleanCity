package com.example.cleancity.models;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Signal {
    int id;
    int lat;
    int lon;
    String type;
    String desc;
    Date date;
    int idUser;
}
