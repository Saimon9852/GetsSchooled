package objects;

import java.util.ArrayList;

public class Teacher extends Person {
    private ArrayList<Course> courseArrayList;
    public Teacher(String email, String name, String mobilePhoneNumber, ArrayList<Course> courseArrayList) {
        super(email, name, mobilePhoneNumber);
        this.courseArrayList = courseArrayList;
    }
    public Teacher(){

    }

    public ArrayList<Course> getCourseArrayList() {
        return courseArrayList;
    }

    public void setCourseArrayList(ArrayList<Course> courseArrayList) {
        this.courseArrayList = courseArrayList;
    }
}
