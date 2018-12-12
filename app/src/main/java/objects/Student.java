package objects;

import java.util.ArrayList;

public class Student extends  Person {
    private ArrayList<Department> departmentArrayList;
    public Student(){

    }
    public Student(String email, String name, String mobilePhoneNumber, String password, ArrayList<Department> departmentArrayList) {
        super(email, name, mobilePhoneNumber);
        this.departmentArrayList = departmentArrayList;
    }
    public String getEmail(){
        return  super.getEmail();
    }
    public void setEmail(String mail){
        super.setEmail(mail);
    }
}
