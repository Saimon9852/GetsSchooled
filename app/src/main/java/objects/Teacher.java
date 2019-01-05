package objects;

import android.location.Location;
import android.util.Log;

import com.example.cyber_lab.getsschooled.ManageCourses;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

public class Teacher extends Person implements Serializable,Comparable<Teacher> {
    private ArrayList<Course> courseArrayList;
    private String UID;
    private String price;
    private String description;
    private ArrayList<Review> reviewArrayList;
    private String photo;
    private float rating;
    double  lat;
    double lon;
    public Teacher(){
        this.reviewArrayList = new ArrayList<>();
        this.courseArrayList = new ArrayList<>();
        reviewArrayList.add(new Review());
        courseArrayList.add(new Course());
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
    public ArrayList<String> courseToStringArray(){
        ArrayList<String> courses = new ArrayList<>();
        for (Course course : courseArrayList){
            courses.add(course.getName());
        }
        return  courses;
    }
    public void setCourseArrayListFromStringArrayList(ArrayList<String> courseArrayList) {
        ArrayList<Course> courses = new ArrayList<Course>();
        for (String course : courseArrayList) {
            if(!course.isEmpty() && isLegalCourse(course))
                courses.add(new Course(course));
        }
        this.courseArrayList = courses;
    }

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
    public int compareTo(Teacher teacher) {
        Log.d("EHUD",this.getName() + " " + teacher.getName());
        if (this.getRating() > teacher.getRating()) {
            Log.d("EHUD",this.getName() + " is bigger then" + teacher.getName());
            return -1;
        }
        else if (this.getRating() <  teacher.getRating()) {
            Log.d("EHUD",this.getName() + " is smaller then" + teacher.getName());

            return 1;
        }
        else {
            Log.d("EHUD",this.getName() + " is equal then" + teacher.getName());

            return 0;
        }

    }


}
