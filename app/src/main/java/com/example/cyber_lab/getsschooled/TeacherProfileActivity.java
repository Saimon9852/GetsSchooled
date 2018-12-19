package com.example.cyber_lab.getsschooled;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import adapters.ProfileAdapter;
import objects.Teacher;

public class TeacherProfileActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Intent intent;
    private TextView textViewName;
    private TextView textViewEmail;
    private ImageView imgProfile;
    private TextView txtDecription;
    //need to add statistics

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        intent = getIntent();
        Bundle bd = intent.getExtras();
        Teacher teacher = (Teacher)bd.get("Teacher");
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_profile);
        textViewEmail = (TextView)findViewById(R.id.txtViewTeacherMail);
        textViewName = (TextView)findViewById(R.id.txtViewTeacherName);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        textViewEmail.setText(teacher.getEmail());
        textViewName.setText(teacher.getName());
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProfileAdapter(teacher);
        mRecyclerView.setAdapter(mAdapter);

    }
}
