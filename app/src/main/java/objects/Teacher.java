package objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Teacher extends Person implements Serializable,Comparable<Teacher> {
    final static String[] staticCourses={"Computer Science-Infi 1" ,
                "Computer Science-Introduction To Programming" ,
                        "Computer Science-Logic And Group Theory" ,
                        "Computer Science-Algorthmic Number Theory" ,
                        "Computer Science-Numerical Systems" ,
                        "Computer Science-Discrete Mathemathics" ,
                        "Computer Science-Linear Algebra 1" ,
                        "Computer Science-Infi 2"};
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
            for(String rCourse : staticCourses){
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

        if (this.rating > teacher.getRating()) {
            return 1;
        }
        else if (this.rating <  teacher.getRating()) {
            return -1;
        }
        else {
            return 0;
        }

    }


}
