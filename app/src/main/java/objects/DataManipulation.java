package objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Data manipulation on courses and teachers
public class DataManipulation {
    private List<Tutor> mList = new ArrayList<>();
    private List<Course> courseList = new ArrayList<>();


    public DataManipulation(List<Tutor> mList, List<Course> cList) {
        this.mList = mList;
        this.courseList = cList;
    }

    public DataManipulation(List<Tutor> mList) {
        this.mList = mList;

    }

    public DataManipulation(){
    }

    public List<Tutor> getAllTeachers() {
        return mList;
    }

    public List<Course> getAllCourses() {return courseList;}

    public void setCourses(List<Course> courseList){
        this.courseList = courseList;
    }

    public void setmList(List<Tutor> mList) {
        this.mList = mList;
    }


    //Function get string department, list of teacherts and list of selected course filters in department
    //Filtering from mList all teachers which teach some course in courses_in_department, without repeat
    public List<Tutor> getFilteredTeachers(String department, List<String> courses_in_department, List<Tutor> mList) {
        List<Tutor> tempList = new ArrayList<>();
        for (Tutor teacher : mList) {
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
