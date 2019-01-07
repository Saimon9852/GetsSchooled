package com.example.cyber_lab.getsschooled;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import adapters.TeachersAdapter;
import objects.Course;
import objects.DataManipulation;
import objects.MyFabFragment;
import objects.Tutor;


public class DashBoardActivity extends AppCompatActivity implements AAH_FabulousFragment.Callbacks, AAH_FabulousFragment.AnimationListener {
    final int PERMISSION_ACCESS_FINE_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    private List<Tutor> teachers;
    private List<Course> courses;
    public static List<String> staticCourses;
    private ImageView btnLogOut;
    private ValueEventListener mDatabaseTeachersListener;
    private ValueEventListener mDatabaseCoursesListener;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FloatingActionButton fab2;
    RecyclerView recyclerView;
    DataManipulation mData;
    TeachersAdapter mAdapter;
    LinearLayout ll;
    List<Tutor> mList = new ArrayList<>();
    List<Course>  cList = new ArrayList<>();
    private ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();
    MyFabFragment dialogFrag;
    DatabaseReference mDatabaseTeachers = FirebaseDatabase.getInstance().getReference("Teachers");
    DatabaseReference mDatabaseCourses = FirebaseDatabase.getInstance().getReference("Courses");
    public static Location studentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //filter libary stuff
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        btnLogOut = (ImageView) findViewById(R.id.viewTutorSignOut);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ll = (LinearLayout) findViewById(R.id.ll);
        //recycled view stuff
        mData = new DataManipulation();
        cList.addAll(mData.getAllCourses());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mData.setmList(teachers);
        mAdapter = new TeachersAdapter(mList,  DashBoardActivity.this,getApplicationContext());
        recyclerView.setAdapter(mAdapter);



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();

        auth = FirebaseAuth.getInstance();
        authListener =  new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (auth.getCurrentUser()  == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(DashBoardActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent intent = new Intent(DashBoardActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        mDatabaseTeachersListener = mDatabaseTeachers
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        teachers= new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Tutor teacher = snapshot.getValue(Tutor.class);
                            if(teacher.getCourseArrayList()!= null && teacher.hasCourses() ){
                                teachers.add(teacher);
                            }

                        }
                        Collections.sort(teachers);
                        for(Tutor t : teachers){
                             Log.d("saimon",t.getName());
                        }
                        mData.setmList(teachers);
                        mList.clear();
                        mList.addAll(teachers);
                        Log.d("teachers",Integer.toString(mList.size()));
                        mAdapter.notifyDataSetChanged();

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



        mDatabaseCoursesListener = mDatabaseCourses
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        courses = new ArrayList<>();
                        staticCourses = new ArrayList<>();
                        boolean correctData = true;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Course course = snapshot.getValue(Course.class);
                                courses.add(course);
                                staticCourses.add(course.toString());
                        }
                            mData.setCourses(courses);
                            cList.addAll(mData.getAllCourses());
                            dialogFrag = MyFabFragment.newInstance();
                            dialogFrag.setDepartments(getAllDepartments(cList));
                            dialogFrag.setParentFab(fab2);
                            fab2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag());
                            }});
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


    }

    //Function is activated once the approve button is pressed in the filter dialog fragment
    //function takes all applied filters by the user and create new teacher list based on these filters
    @Override
    public void onResult(Object result) {
        Log.d("k9res", "onResult: " + result.toString());
        List<Tutor> teacherList = new ArrayList<>();
        if (result.toString().equalsIgnoreCase("swiped_down")) {
            //do something or nothing
        } else {
            if (result != null) {
                ArrayMap<String, List<String>> applied_filters = (ArrayMap<String, List<String>>) result;
                if (applied_filters.size() != 0) {
                    List<Tutor> filteredList = mData.getAllTeachers();
                    //iterate over arraymap
                    for (Map.Entry<String, List<String>> entry : applied_filters.entrySet()) {
                        mergeFilter(teacherList,mData.getFilteredTeachers(entry.getKey(),entry.getValue(), filteredList));
                    }
                    mList.clear();
                    Collections.sort(teacherList);
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

    @Override
    public void onStart() {
        super.onStart();
        mDatabaseCourses.addValueEventListener(mDatabaseCoursesListener);
        mDatabaseTeachers.addValueEventListener(mDatabaseTeachersListener);
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mDatabaseTeachers != null)
            mDatabaseTeachers.removeEventListener(mDatabaseTeachersListener);
        if(mDatabaseCourses != null)
            mDatabaseCourses.removeEventListener(mDatabaseCoursesListener);
        if(auth != null)
            auth.removeAuthStateListener(authListener);

    }
    public ArrayMap<String, List<String>> getApplied_filters() {
        return applied_filters;
    }

    public DataManipulation getmData() {
        return mData;
    }

    public void signOut() {
        auth.signOut();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!

                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
    //Get List of courses and take all existing departments without repeat
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

    //merge two different list of teachers without repeat
    private void mergeFilter(List<Tutor> mainList, List<Tutor> filterList){
        for (Tutor teacher: filterList){
            if(!mainList.contains(teacher))
                mainList.add(teacher);
        }
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_ACCESS_FINE_LOCATION);
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                studentLocation = location;
                            } else {
                                Log.d("EHUD", "failed");
                            }
                        }
                    });
        }
    }


}




