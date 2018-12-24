package objects;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import objects.Course;
import objects.Teacher;

/**
 * Created by krupenghetiya on 28/06/17.
 */

public class TeacherData {
    private List<Teacher> mList = new ArrayList<>();
    private List<Course> courseList = new ArrayList<>();


    public TeacherData(List<Teacher> mList, List<Course> cList) {
        this.mList = mList;
        this.courseList = cList;
    }

    public TeacherData(List<Teacher> mList) {
        this.mList = mList;

    }

    public TeacherData(){
        courseList = new ArrayList<>();
        courseList.add(new Course("test1-test2"));

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

    public List<Teacher> getFilteredTeachers(String department, List<String> genre, List<Teacher> mList) {
        List<Teacher> tempList = new ArrayList<>();
        for (Teacher teacher : mList) {
            for (String g : genre) {
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



    public List<String> getUniqueKeys(String department) {
        List<String> ratings = new ArrayList<>();
        for (Course course : courseList) {
            String[] split = course.getName().split("-");
            Log.d("justcheck",split[1]);
            if(split[0].equals(department)){
                ratings.add(split[1]);
            }
        }
        Collections.sort(ratings);
        return ratings;
    }


}
