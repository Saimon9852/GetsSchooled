package com.example.cyber_lab.getsschooled;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageView imageLogout,imageProfile,imageCourses,imageSetting;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    final int LIST_REQUEST = 1;
    final int LIST_RESULT = 100;
    ArrayList<String> list;
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        authListener =  new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(auth.getCurrentUser() == null){
                    Intent intent = new Intent(imageLogout.getContext(),LoginActivity.class);
                    imageLogout.getContext().startActivity(intent);
                    finish();
                }

            }
        };

        imageLogout = (ImageView) findViewById(R.id.main_image_logout);
        imageProfile = (ImageView) findViewById(R.id.main_image_profile);
        imageCourses = (ImageView) findViewById(R.id.main_image_courses);
        imageSetting = (ImageView) findViewById(R.id.main_image_setting);
        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent intent = new Intent(imageLogout.getContext(),LoginActivity.class);
                imageLogout.getContext().startActivity(intent);
            }
        });
        imageSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),SettingActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),TeacherProfileActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        imageCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ManageCourses.class);
                if(list == null) {
                    list = new ArrayList<>();
                }
                i.putStringArrayListExtra("list", list);
                i.putExtra("cameFromTutor", true);
                startActivityForResult(i, LIST_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LIST_REQUEST && resultCode == LIST_RESULT)
            list = data.getStringArrayListExtra("list");
    }
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (auth != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}


