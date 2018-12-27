package com.example.cyber_lab.getsschooled.objects;



public class Admin extends Person {
    private static Admin admin;
    private String adminMail = "saimo3@walla.com";
    private String phone  = "0544886192";
    private Admin(){
        super(admin.adminMail,"ADMIN",admin.phone);
    }
    public Admin getInstance(){
        if(admin == null)
            admin = new Admin();
        return  admin;
    }
}
