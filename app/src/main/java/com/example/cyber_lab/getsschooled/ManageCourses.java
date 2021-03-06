package com.example.cyber_lab.getsschooled;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


import adapters.CourseAdapter;
import objects.Course;
import objects.Tutor;

public class ManageCourses extends AppCompatActivity {

    final int LIST_RESULT = 100;

    ArrayList<String> list;
    static String[] courses;
    Tutor teacher;
    RecyclerView recyclerView;
    CourseAdapter courseAdapter;
    LinearLayoutManager llm;
    Button submitButton;
    DatabaseReference mDatabaseTeachers;
    DatabaseReference mDatabaseCourses;
    ValueEventListener mDatabaseValueEventListener;
    ValueEventListener mDatabaseCoursesListener;
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
        mDatabaseCourses = FirebaseDatabase.getInstance().getReference("Courses");
        list = getIntent().getStringArrayListExtra("courseToStringArray");
        cameFromTutor = getIntent().getBooleanExtra("cameFromTutor",false);
        /**
         * disallow data change from student flow
         */
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
                teacher = dataSnapshot.getValue(Tutor.class);
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
        mDatabaseCoursesListener = mDatabaseCourses
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int index = 0;
                        courses = new String[(int)dataSnapshot.getChildrenCount()];
                        boolean correctData = true;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Course course = snapshot.getValue(Course.class);
                            courses[index++] = course.getName();
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        /**
         * updates database on submission
         */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                list = courseAdapter.getStepList();
                teacher.setCourseArrayListFromStringArrayList(list);
                mDatabaseTeachers.setValue(teacher);
                i.putStringArrayListExtra("courseToStringArray", list);
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
        if(mDatabaseCourses != null)
            mDatabaseCourses.removeEventListener(mDatabaseCoursesListener);
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        mDatabaseTeachers.addValueEventListener(mDatabaseValueEventListener);
        auth.addAuthStateListener(authStateListener);
        mDatabaseCourses.addValueEventListener(mDatabaseCoursesListener);
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
    public static String[] getCourses(){
        if(courses == null){
            courses = new String[1];
            courses[0] = "Computer Science-null";
        }
        return  courses;
    }
}