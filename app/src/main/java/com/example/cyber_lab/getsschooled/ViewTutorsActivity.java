package com.example.cyber_lab.getsschooled;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import adapters.LineAdapter;
import objects.Teacher;

public class ViewTutorsActivity extends AppCompatActivity {
    private ArrayList<Teacher> teachersMail;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Teachers");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tutors);
        mRecyclerView= (RecyclerView) findViewById(R.id.recycle_view_tutors);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mDatabase
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int size = (int)dataSnapshot.getChildrenCount();
                        teachersMail = new ArrayList<Teacher>();
                        int indedx = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Teacher teacher = snapshot.getValue(Teacher.class);
                            if(snapshot.getKey()!= FirebaseAuth.getInstance().getUid())
                                teachersMail.add(teacher);
                        }
                        mAdapter = new LineAdapter(teachersMail);
                        mRecyclerView.setAdapter(mAdapter);


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }


}
