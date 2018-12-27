package com.example.cyber_lab.getsschooled;

import android.content.Intent;
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

import com.example.cyber_lab.getsschooled.objects.Teacher;

public class ManageCourses extends AppCompatActivity {

    final int LIST_RESULT = 100;

    ArrayList<String> list;
    Teacher teacher;
    RecyclerView recyclerView;
    CourseAdapter courseAdapter;
    LinearLayoutManager llm;
    Button submitButton;
    DatabaseReference mDatabaseTeachers;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_courses);
        auth = FirebaseAuth.getInstance();
        mDatabaseTeachers = FirebaseDatabase.getInstance().getReference("Teachers").child(auth.getUid());
        list = getIntent().getStringArrayListExtra("list");
        //To show at least one row
        if(list ==null || list.size() == 0)
            list = new ArrayList<String>();
        list.add("");


        submitButton = (Button) findViewById(R.id.submit_button);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        courseAdapter = new CourseAdapter(list, this,"");
        llm = new LinearLayoutManager(this);

        //Setting the adapter
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(llm);
        mDatabaseTeachers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teacher = dataSnapshot.getValue(Teacher.class);
                    if(teacher.getCourseArrayList()!= null)
                        list = teacher.courseToStringArray();
                        courseAdapter = new CourseAdapter(list, recyclerView.getContext(),"");
                        recyclerView.setAdapter(courseAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


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
    protected void onDestroy() {
        super.onDestroy();
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