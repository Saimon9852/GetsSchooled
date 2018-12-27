package com.example.cyber_lab.getsschooled.objects;

import java.util.ArrayList;

public class CourseList {
    ArrayList<Course> courses_List ;
    private static CourseList courseList = null;
    private CourseList(){
        courses_List = new ArrayList<Course>();
        Course c1 = new Course("Linear Algebra 1");
        Course c2 = new Course("Linear Algebra 1");
    }
    public CourseList getInstance(){
        if(courseList == null)
            courseList = new CourseList();
        return  courseList;
    }
}
