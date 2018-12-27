package com.example.cyber_lab.getsschooled.objects;

import java.io.Serializable;

public class Course implements Serializable {
    private String name;

    public Course(){}


    public Course(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
