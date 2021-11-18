package com.example.rest.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ComplexObjectList implements Serializable {

    public  String firstName;

    public Integer companyId;

    public ComplexObjectList(String firstName, Integer companyId){
        this.firstName = firstName;
        this.companyId = companyId;
    }
}
