package objects;

import java.util.ArrayList;

public class CourseList {
    ArrayList<Course> courses_List ;
    private static CourseList courseList = null;
    private CourseList(){
        courses_List = new ArrayList<Course>();
        Course c1 = new Course("Linear Algebra 1" , new Department("Computer Science"));
        Course c2 = new Course("Linear Algebra 1" , new Department("Electrical Engineering"));
    }
    public CourseList getInstance(){
        if(courseList == null)
            courseList = new CourseList();
        return  courseList;
    }
}
