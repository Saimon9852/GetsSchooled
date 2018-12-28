package objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Teacher extends Person implements Serializable {

    private ArrayList<Course> courseArrayList;
    private String UID;
    private String price;
    private String description;
    private ArrayList<Review> reviewArrayList;
    private String photo;
    private float rating;
    public Teacher(){
        this.reviewArrayList = new ArrayList<>();
        this.courseArrayList = new ArrayList<>();
        reviewArrayList.add(new Review());
        courseArrayList.add(new Course());
    }
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

    public void setReviewArrayList(ArrayList<Review> reviewArrayList) {
        this.reviewArrayList = reviewArrayList;
    }


    public String getPrice() {
        return price;
    }

    public ArrayList<Review> getReviewArrayList() {
        return reviewArrayList;
    }

    public ArrayList<Course> getCourseArrayList() {
        if(courseArrayList !=null)
            return courseArrayList;
        courseArrayList = new ArrayList<>();
        return  courseArrayList;
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

    public void updateRating(){
        float rate = 0;
        for(Review review : reviewArrayList){
            rate += review.getStars();
        }
        this.rating = rate/ reviewArrayList.size();
    }
    public int getCourseArrayListSize(){
        return courseArrayList.size();
    }
    public boolean isReviewedBy(String uid){
        for(Review review : reviewArrayList){
            if(!review.isNull() && review.getReviewerUID().equals(uid))
                return true;
        }
        return false;
    }

}
