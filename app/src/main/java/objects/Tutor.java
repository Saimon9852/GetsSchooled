package objects;

import android.util.Log;

import com.example.cyber_lab.getsschooled.ManageCourses;

import java.io.Serializable;
import java.util.ArrayList;

public class Tutor extends User implements Serializable,Comparable<Tutor> {
    private ArrayList<Course> courseArrayList;
    private String UID;
    private String price;
    private String description;
    private ArrayList<Review> reviewArrayList;
    private String photo;
    private float rating;
    double  lat;
    double lon;
    public Tutor(){
        this.reviewArrayList = new ArrayList<>();
        this.courseArrayList = new ArrayList<>();
        reviewArrayList.add(new Review());
        courseArrayList.add(new Course());
        // by default we have Ariel as tutor location.
        lon = 35.2099067;
        lat = 32.1031880;
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
    public double getLat(){
        if(lat == 0){
            lat = 32.1031880;
        }
        return  lat;
    }
    public void setLat(double lat){
        this.lat = lat;
    }
    public double getLon(){
        if(lon == 0){
            lon = 35.2099067;
        }
        return  lon;
    }
    public void setLon(double lon){
        this.lon = lon;
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

    /**
     * Course arraylist to String arraylist
     * @return A String arraylist representing Course arraylist
     */
    public ArrayList<String> courseToStringArray(){
        ArrayList<String> courses = new ArrayList<>();
        for (Course course : courseArrayList){
            courses.add(course.getName());
        }
        return  courses;
    }

    /**
     * sets our course arraylist from string arraylist
     * @param courseArrayList
     */
    public void setCourseArrayListFromStringArrayList(ArrayList<String> courseArrayList) {
        ArrayList<Course> courses = new ArrayList<Course>();
        for (String course : courseArrayList) {
            if(!course.isEmpty() && isLegalCourse(course))
                courses.add(new Course(course));
        }
        this.courseArrayList = courses;
    }

    /**
     * updates rating , uses simple average. null messages dont count.
     */
    public void updateRating(){
        float rate = 0;
        int realSize = 0;
        for(Review review : reviewArrayList){
            if( ! review.isNull()){
                realSize++;
                rate += review.getStars();
            }
        }
        if(realSize != 0)
            this.rating = rate/ realSize;
        else
            this.rating = 0;
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
        public boolean isLegalCourse(String course){
            for(String rCourse : ManageCourses.getCourses()){
                if(rCourse.equals(course))
                    return  true;
            }
            return false;
        }
    public boolean hasCourses() {
        for (Course course : courseArrayList) {
            if (!course.getName().isEmpty())
                return true;
        }
        return false;

    }

    @Override
    /**
     * comparator for sorting tutors by rating
     */
    public int compareTo(Tutor teacher) {
        if (this.getRating() > teacher.getRating()) {
            return -1;
        }
        else if (this.getRating() <  teacher.getRating()) {

            return 1;
        }
        else {
            return 0;
        }

    }


}
