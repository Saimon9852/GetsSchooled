package com.example.cyber_lab.getsschooled;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import objects.Course;

public class MainActivity extends AppCompatActivity {
    private Button btnLogOut,btnProfile,btnViewTutors,btnManageCourses;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    final int LIST_REQUEST = 1;
    final int LIST_RESULT = 100;
    Button button;
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

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        btnLogOut = (Button) findViewById(R.id.btn_logout);
        btnProfile = (Button) findViewById(R.id.btn_profile);
        btnViewTutors = (Button) findViewById(R.id.btn_view_tutors);
        btnManageCourses = (Button) findViewById(R.id.button_manage_courses);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent intent = new Intent(btnLogOut.getContext(),LoginActivity.class);
                btnLogOut.getContext().startActivity(intent);
            }
        });
        btnViewTutors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ViewTutorsActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ProfileActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        btnManageCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ManageCourses.class);
                if(list == null) {
                    list = new ArrayList<>();
                }
                i.putStringArrayListExtra("list", list);
                startActivityForResult(i, LIST_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LIST_REQUEST && resultCode == LIST_RESULT)
            list = data.getStringArrayListExtra("list");
    }
}


