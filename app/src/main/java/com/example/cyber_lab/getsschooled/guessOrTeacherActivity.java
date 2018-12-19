package com.example.cyber_lab.getsschooled;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class guessOrTeacherActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private Button btnTeacher;
    private Button btnGuest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_or_teacher);

        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(guessOrTeacherActivity.this, ViewTutorsActivity.class));
                    finish();
                }
            }
        };

        btnTeacher = (Button) findViewById(R.id.btnLogTeacher);
        this.btnGuest = (Button) findViewById(R.id.btnGuest);
        this.btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ViewTutorsActivity.class);
                v.getContext().startActivity(intent);
            }
        });

    }


}
