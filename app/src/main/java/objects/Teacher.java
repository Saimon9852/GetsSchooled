package objects;

import android.graphics.Bitmap;
import android.se.omapi.SEService;
import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.crypto.Cipher;

public class Teacher extends Person implements Serializable {

    private ArrayList<Course> courseArrayList;
    private String UID;
    private String price;
    private String description;
    private ArrayList<Review> reviews;
    private String photo;
    private float rating;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto(){
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
    public String getBeutifulCoursesString(){
        String beutiful ="";
        HashMap<String, ArrayList> dictMap = new HashMap<String, ArrayList>();
        for(Course course : courseArrayList){
            String currCourseDepartment = course.getName().split("-")[0];
            if(dictMap.get(currCourseDepartment) == null){
                ArrayList<String> courses = new ArrayList<String>();
                courses.add(course.getName().split("-")[1]);
                dictMap.put(currCourseDepartment,courses);
            }else{
                ArrayList<String> tempArrayList = dictMap.get(currCourseDepartment);
                tempArrayList.add(course.getName().split("-")[1]);
                dictMap.put(currCourseDepartment,tempArrayList);
            }
        }
        Set <String> keyset = dictMap.keySet();
        for(String key :keyset){
            beutiful += key +":\n" + dictMap.get(key).toString().replace(",","\n");
        }
        return  beutiful;
    }
    public void updateRating(){
        float rate = 0;
        for(Review review : reviews){
            rate += review.getStars();
        }
        this.rating = rate/reviews.size();
    }
    public int getCourseArrayListSize(){
        if(courseArrayList == null){
            return  0;
        }
        return courseArrayList.size();
    }
    public boolean isReviewedBy(String uid){
        for(Review review : reviews){
            if(review.getReviewerUID().equals(uid))
                return true;
        }
        return false;
    }

}
