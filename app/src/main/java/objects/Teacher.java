package objects;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class Teacher extends Person implements Serializable {

    private ArrayList<Course> courseArrayList;
    private String price;
    private String description;
    private Bitmap img;
    private String stars;
    //private ArrayList<Reviews> reviewArrayList;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private ArrayList<Review> reviews;

    public void setPrice(String price) {
        this.price = price;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public Teacher(String email, String name, String mobilePhoneNumber, ArrayList<Course> courseArrayList, String price, ArrayList<Review> reviews) {
        super(email, name, mobilePhoneNumber);
        this.courseArrayList = courseArrayList;
        this.price = price;
        this.reviews = reviews;
    }
    public Teacher(String email, String name, String mobilePhoneNumber) {
        super(email, name, mobilePhoneNumber);
    }
    public String getPrice() {
        return price;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public Teacher(){

    }

    public ArrayList<Course> getCourseArrayList() {
        return courseArrayList;
    }

    public void setCourseArrayList(ArrayList<Course> courseArrayList) {
        this.courseArrayList = courseArrayList;
    }


    public static class ChildClass implements Serializable {

        public ChildClass() {}
    }
}
