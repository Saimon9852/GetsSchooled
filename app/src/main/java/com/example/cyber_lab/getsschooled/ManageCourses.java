package com.example.cyber_lab.getsschooled;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


import adapters.CourseAdapter;
import adapters.TeachersAdapter;
import objects.Course;
import objects.Review;
import objects.Teacher;

public class ManageCourses extends AppCompatActivity {

    final int LIST_RESULT = 100;

    ArrayList<String> list;
    Teacher teacher;
    RecyclerView recyclerView;
    CourseAdapter courseAdapter;
    LinearLayoutManager llm;
    Button submitButton;
    DatabaseReference mDatabaseTeachers;
    ValueEventListener mDatabaseValueEventListener;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    private boolean cameFromTutor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_courses);

        submitButton = (Button) findViewById(R.id.submit_button);
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        auth = FirebaseAuth.getInstance();
        mDatabaseTeachers = FirebaseDatabase.getInstance().getReference("Teachers").child(auth.getUid());
        list = getIntent().getStringArrayListExtra("list");
        cameFromTutor = getIntent().getBooleanExtra("cameFromTutor",false);

        if(!cameFromTutor) {
            submitButton.setEnabled(false);
            submitButton.setVisibility(View.GONE);
        }

        //To show at least one row
        if(list ==null || list.size() == 0)
        {
            list = new ArrayList<String>();
            list.add("");
        }


        courseAdapter = new CourseAdapter(list, this,cameFromTutor);
        llm = new LinearLayoutManager(this);
        //Setting the adapter
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(llm);
        mDatabaseValueEventListener = mDatabaseTeachers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teacher = dataSnapshot.getValue(Teacher.class);
                list = teacher.courseToStringArray();
                courseAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        authStateListener  =  new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (auth.getCurrentUser()  == null) {
//                     user auth state is changed - user is null
//                     launch login activity
                    startActivity(new Intent(ManageCourses.this, LoginActivity.class));
                    finish();
                }
            }
        };
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                list = courseAdapter.getStepList();
                teacher.setCourseArrayListFromStringArrayList(list);
                mDatabaseTeachers.setValue(teacher);
                i.putStringArrayListExtra("list", list);
                setResult(LIST_RESULT, i);
                finish();
            }
        });
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(mDatabaseTeachers != null)
            mDatabaseTeachers.removeEventListener(mDatabaseValueEventListener);
        if(auth != null)
            auth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(mDatabaseTeachers != null)
            mDatabaseTeachers.removeEventListener(mDatabaseValueEventListener);
        if(auth != null)
            auth.removeAuthStateListener(authStateListener);
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        mDatabaseTeachers.addValueEventListener(mDatabaseValueEventListener);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}