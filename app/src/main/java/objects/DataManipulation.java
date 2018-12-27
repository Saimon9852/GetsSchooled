package objects;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import objects.Course;
import objects.Teacher;

//Data manipulation on courses and teachers
public class DataManipulation {
    private List<Teacher> mList = new ArrayList<>();
    private List<Course> courseList = new ArrayList<>();


    public DataManipulation(List<Teacher> mList, List<Course> cList) {
        this.mList = mList;
        this.courseList = cList;
    }

    public DataManipulation(List<Teacher> mList) {
        this.mList = mList;

    }

    public DataManipulation(){
    }

    public List<Teacher> getAllTeachers() {
        return mList;
    }

    public List<Course> getAllCourses() {return courseList;}

    public void setCourses(List<Course> courseList){
        this.courseList = courseList;
    }

    public void setmList(List<Teacher> mList) {
        this.mList = mList;
    }


    //Function get string department, list of teacherts and list of selected course filters in department
    //Filtering from mList all teachers which teach some course in courses_in_department, without repeat
    public List<Teacher> getFilteredTeachers(String department, List<String> courses_in_department, List<Teacher> mList) {
        List<Teacher> tempList = new ArrayList<>();
        for (Teacher teacher : mList) {
            for (String g : courses_in_department) {
                 for(Course c: teacher.getCourseArrayList()){
                     String[] split = c.getName().split("-");
                     if(split[0].equals(department) && split[1].equals(g)){
                         if(!tempList.contains(teacher))
                             tempList.add(teacher);
                     }
                 }
            }

        }
        return tempList;
    }


    //Setting up for each key(department) his courses without repeat
    //Get string department, and for each course in courseList
    // check if the course belong to this department and add it to new String list contaions courses name
    public List<String> getUniqueKeys(String department) {
        List<String> ratings = new ArrayList<>();
        for (Course course : courseList) {
            String[] split = course.getName().split("-");
            if(split[0].equals(department)){
                ratings.add(split[1]);
            }
        }
        Collections.sort(ratings);
        return ratings;
    }


}
