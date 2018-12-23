package com.example.cyber_lab.getsschooled;

import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.squareup.picasso.Picasso;
import com.allattentionhere.fabulousfilter.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import Database.Util;
import objects.Course;
import objects.TeacherData;
import adapters.TeachersAdapter;
import objects.MyFabFragment;
import objects.Teacher;


public class ViewTutorsActivity extends AppCompatActivity implements AAH_FabulousFragment.Callbacks, AAH_FabulousFragment.AnimationListener {
    private List<Teacher> teachers;
    FloatingActionButton fab2;
    RecyclerView recyclerView;
    TeacherData mData;
    TeachersAdapter mAdapter;
    Picasso p;
    LinearLayout ll;
    List<Teacher> mList = new ArrayList<>();
    List<Course>  cList = new ArrayList<>();
    private ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();
    MyFabFragment dialogFrag;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Teachers");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tutors);

        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ll = (LinearLayout) findViewById(R.id.ll);

        mData = Util.getTeacherData();
        p = Picasso.with(this);
        mList.addAll(mData.getAllTeachers());
        cList.addAll(mData.getAllCourses());
        mAdapter = new TeachersAdapter(mList, p, ViewTutorsActivity.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        mDatabase
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        teachers= new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Teacher teacher = snapshot.getValue(Teacher.class);
                                teachers.add(teacher);
                        }
                        mData.setmList(teachers);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



        dialogFrag = MyFabFragment.newInstance();
        dialogFrag.setDepartments(getAllDepartments(cList));
        dialogFrag.setParentFab(fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag());
            }
        });


    }

    @Override
    public void onResult(Object result) {
        Log.d("k9res", "onResult: " + result.toString());
        List<Teacher> teacherList = new ArrayList<>();
        if (result.toString().equalsIgnoreCase("swiped_down")) {
            //do something or nothing
        } else {
            if (result != null) {
                ArrayMap<String, List<String>> applied_filters = (ArrayMap<String, List<String>>) result;
                if (applied_filters.size() != 0) {
                    List<Teacher> filteredList = mData.getAllTeachers();
                    //iterate over arraymap
                    for (Map.Entry<String, List<String>> entry : applied_filters.entrySet()) {
                        Log.d("k9res", "entry.key: " + entry.getKey());
                        mergeFilter(teacherList,mData.getFilteredTeachers(entry.getKey(),entry.getValue(), filteredList));
                    }
                    Log.d("k9res", "new size: " + filteredList.size());
                    mList.clear();
                    mList.addAll(teacherList);
                    mAdapter.notifyDataSetChanged();


                } else {
                    mList.clear();
                    mList.addAll(mData.getAllTeachers());
                    mAdapter.notifyDataSetChanged();
                }
            }
            //handle result
        }
    }

    public ArrayMap<String, List<String>> getApplied_filters() {
        return applied_filters;
    }

    public TeacherData getmData() {
        return mData;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (dialogFrag.isAdded()) {
            dialogFrag.dismiss();
            dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag());
        }

    }

    @Override
    public void onOpenAnimationStart() {
        Log.d("aah_animation", "onOpenAnimationStart: ");
    }

    @Override
    public void onOpenAnimationEnd() {
        Log.d("aah_animation", "onOpenAnimationEnd: ");

    }

    @Override
    public void onCloseAnimationStart() {
        Log.d("aah_animation", "onCloseAnimationStart: ");

    }

    @Override
    public void onCloseAnimationEnd() {
        Log.d("aah_animation", "onCloseAnimationEnd: ");

    }

    private List<String> getAllDepartments(List<Course> cList) {
        List<String> depars = new ArrayList<>();
        String[] split;
        for(Course c: cList){
            split = c.getName().split("-");
            if(!depars.contains(split[0]))
                depars.add(split[0]);
        }
        return depars;
    }

    private void mergeFilter(List<Teacher> mainList,List<Teacher> filterList){
        for (Teacher teacher: filterList){
            if(!mainList.contains(teacher))
                mainList.add(teacher);
        }
    }


}




