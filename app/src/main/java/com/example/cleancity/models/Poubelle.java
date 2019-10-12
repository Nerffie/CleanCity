package com.example.cleancity.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Poubelle {

    int id;
    int lat;
    int lon;
    int temp;
    int rempli; // Pourcentage

}
