package objects;

import android.graphics.Bitmap;
import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;

import javax.crypto.Cipher;

public class Teacher extends Person implements Serializable {

    private ArrayList<Course> courseArrayList;
    private String price;
    private String description;
    private Bitmap img;
    private String stars;
    private ArrayList<Review> reviews;
    private String photo = "https://yts.ag/assets/images/movies/smurfs_the_lost_village_2017/medium-cover.jpg";
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedium_cover_image(){
         return  this.photo;
    }


    public void setPrice(String price) {
        this.price = price;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    //Added to merge with search tutor activity,Need to be fixed
    public Teacher(String email, String name, String mobilePhoneNumber, ArrayList<Course> courseArrayList, String price,String photo) {
        super(email, name, mobilePhoneNumber);
        this.courseArrayList = courseArrayList;
        this.price = price;
        this.photo = photo;
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
    public ArrayList<String> courseToStringArray(){
        ArrayList<String> courses = new ArrayList<String>();
        for (Course course : courseArrayList){
            courses.add(course.getName());
        }
        return  courses;
    }
    public void setCourseArrayListFromStringArrayList(ArrayList<String> courseArrayList) {
        ArrayList<Course> courses = new ArrayList<Course>();
        for (String course : courseArrayList) {
            courses.add(new Course(course));
        }
        this.courseArrayList = courses;
    }

}
