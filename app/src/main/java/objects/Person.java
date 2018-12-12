package objects;

import java.util.ArrayList;

public class Person {
    private String email;
    private String name;
    private String mobilePhoneNumber;
    public Person(String email, String name, String mobilePhoneNumber){
        this.email = email;
        this.name = name;
        this.mobilePhoneNumber = mobilePhoneNumber;
    }
    public Person(){

    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }
}
