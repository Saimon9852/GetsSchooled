package com.example.cyber_lab.getsschooled;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapters.ProfileAdapter;
import adapters.TeachersAdapter;
import objects.Review;
import objects.Teacher;

public class AdminReviewsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //class teacher list
    private List<Teacher> teacherList = new ArrayList<>();
    private List<Review> reviewsList = new ArrayList<>();
    //teachers for database listener on change
    private List<Teacher>  teachers;
    DatabaseReference mDatabaseTeachers = FirebaseDatabase.getInstance().getReference("Teachers");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reviews);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_Allreview_profile);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDatabaseTeachers
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        teachers= new ArrayList<>();
                        boolean correctData = true;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Teacher teacher = snapshot.getValue(Teacher.class);
                            try {
                                teacher.getCourseArrayList().size();
                                teachers.add(teacher);
                            }
                            catch (Exception e){
                                correctData = false;
                                continue;
                            }
                        }
                        if(correctData == true) {
                            teacherList.clear();
                            teacherList.addAll(teachers);
                            getAllReviews(teachers);
                            mAdapter = new ProfileAdapter(reviewsList);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    //j1508
    //get list of teacher and merge all teacher reviews
    private void getAllReviews(List<Teacher> teachers) {
        List<Review> reviews = new ArrayList<>();
        for(Teacher teacher: teachers){
            reviews.addAll(teacher.getReviews());
        }
        reviews.clear();
        reviewsList.addAll(reviews);
    }
}
