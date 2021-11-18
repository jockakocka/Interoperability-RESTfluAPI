package com.example.rest.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class ComplexRepairCompany implements Serializable {

    private Integer companyId;

    private String name;

    private String fieldOfWork;

    private Date foundationDate;

    private String streetName;

    private Integer number;

    private String city;

    private String postalCode;

    private String country;

    public ComplexRepairCompany(Integer companyId, String name, String fieldOfWork, Date foundationDate, String streetName,
                                Integer number, String city, String postalCode, String country){
        this.companyId = companyId;
        this.name = name;
        this.fieldOfWork = fieldOfWork;
        this.foundationDate = foundationDate;
        this.streetName = streetName;
        this.number = number;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

}
