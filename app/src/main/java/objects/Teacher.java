package objects;

import java.util.ArrayList;

public class Teacher extends Person {

    private ArrayList<Course> courseArrayList;
    private String price;
    //private IMAGE img;
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



}
