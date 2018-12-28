package objects;

import java.io.Serializable;

public class Course implements Serializable {
    private String name;

    public Course(){
        this.name = "";
    }


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
