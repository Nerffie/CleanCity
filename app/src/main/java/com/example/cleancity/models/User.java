package com.example.cleancity.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class User {
    int id;
    String name;
    String password;
}
