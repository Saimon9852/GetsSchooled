package com.example.cyber_lab.getsschooled;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


import adapters.ProfileAdapter;
import adapters.ReviewAdapter;
import objects.Course;
import objects.Review;
import objects.Teacher;

public class TeacherProfileActivity extends AppCompatActivity {
    private boolean changedProfileImage = false;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView textViewName, textDescription,textViewPrice,textViewCourses,textViewRating;
    private ImageView imgCamera,imgWhatsapp,imgProfile,SaveChanges;
    private ArrayList<Review> list;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private ReviewAdapter reviewAdapter;
    private boolean reviewed;
    private DatabaseReference mDatabaseTeachers;
    private Teacher teacher;
    private String UID;
    private FirebaseAuth.AuthStateListener authListener;
    private ValueEventListener mDatabaseTeachersListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_profile);
        textDescription = (TextView)findViewById(R.id.txtViewTeacherDescp);
        textViewName = (TextView)findViewById(R.id.txtViewTeacherName);
        imgProfile = (ImageView)findViewById(R.id.profile_image);
        SaveChanges = (ImageView)findViewById(R.id.profile_save_changes);
        imgWhatsapp = (ImageView)findViewById(R.id.image_view_whatsapp);
        textViewPrice = (TextView)findViewById(R.id.textViewPrice);
        textViewCourses = (TextView)findViewById(R.id.textViewCourses);
        textViewRating = (TextView)findViewById(R.id.textViewRating);
        imgCamera = (ImageView)findViewById(R.id.image_view_camera);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        teacher = (Teacher)getIntent().getSerializableExtra("Teacher");
        auth = FirebaseAuth.getInstance();
        UID = auth.getUid();
        mDatabaseTeachers = FirebaseDatabase.getInstance().getReference("Teachers").child(UID);

        authListener =  new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                UID = firebaseAuth.getUid();
                if (UID == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(TeacherProfileActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        mDatabaseTeachersListener = mDatabaseTeachers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UID = auth.getUid();
                teacher = dataSnapshot.getValue(Teacher.class);
                teacher.updateRating();
                pullPhoto();
                updatedView();
                isReviwed();
                HideCameraOnNotSameUser();
                mAdapter = new ProfileAdapter(teacher.getReviewArrayList());
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(mLayoutManager);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teacher.getUID().equals(UID))
                    dispatchTakePictureIntent();
            }
        });

        imgWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.whatsapp.com/send?phone="+teacher.getMobilePhoneNumber();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        list = (ArrayList<Review>)getIntent().getSerializableExtra("list");
        //To show at least one row
        if(list == null || list.size() == 0){
            list = new ArrayList<>();
            list.add(new Review());
        }


        /**
         * on change will add empty comment if necessary,
         * will write changed teacher to data base,
         * and will update the teacher's rating.
         */
        SaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                list = reviewAdapter.getStepList();
                teacher.setReviewArrayList(list);
                addComment(list);
                mDatabaseTeachers.setValue(teacher);
                i.putExtra("list", list);
                setResult(100, i);
                if (changedProfileImage) {
                    uploadImage();
                }
                finish();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
        mDatabaseTeachers.addValueEventListener(mDatabaseTeachersListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (auth != null) {
            auth.removeAuthStateListener(authListener);
            mDatabaseTeachers.removeEventListener(mDatabaseTeachersListener);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgProfile = (ImageView) findViewById(R.id.profile_image);
            imgProfile.setImageBitmap(imageBitmap);

            changedProfileImage = true;
        }
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void uploadImage() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        StorageReference ref = storageReference.child("images/" +teacher.getEmail()+"/profile");
        imgProfile.setDrawingCacheEnabled(true);
        imgProfile.buildDrawingCache();
        Bitmap bitmap = imgProfile.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        ref.putBytes(data);
        Log.e("trying to upload image", "success");
        updateUserPictureUri();

    }
    public  void updateUserPictureUri() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        StorageReference ref = storageReference.child("images/" + teacher.getEmail() + "/profile");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Teachers");
                final Uri Furi = uri;
                teacher.setPhoto(Furi.toString());
                reference.child(teacher.getUID()).setValue(teacher);
                Log.e("WTF", "5");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception exception) {
                Log.e("WTF", "failed");
            }
        });
    }
    public void pullPhoto(){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        StorageReference load = storageReference.child("images/" + teacher.getEmail() + "/profile");

        load.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                // Pass it to Picasso to download, show in ImageView and caching
                Glide.with(imgProfile).load(uri).into(imgProfile);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }
    public void addComment(ArrayList<Review> list){
        int size = list.size();
        if(size > 0){
            if(list.get(size-1).isNull()){

            }else{
                list.add(new Review());
            }
        }
    }
    public void isReviwed() {
        if (teacher.isReviewedBy(UID) || teacher.getUID().equals(UID)) {
            reviewed = true;
        } else {
            reviewed = false;
        }

    }
    public void updatedView(){
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
//        textViewEmail.setText(teacher.getEmail());
        textViewName.setText(teacher.getName());
        textViewPrice.setText(teacher.getPrice());
        textViewCourses.setText(Integer.toString(teacher.getCourseArrayList().size()));
        textViewRating.setText(Float.toString(teacher.getRating()));
        textDescription.setText(getBeutifulCoursesString());
    }
    public String getBeutifulCoursesString(){
        String beautiful = "";
        String currCourseDepartment = "";
        ArrayList<Course> courseArrayList = teacher.getCourseArrayList();
        if( courseArrayList.size() >0){
            HashMap<String, ArrayList> dictMap = new HashMap<String, ArrayList>();
            for(Course course : courseArrayList){
                if( course.getName().length() > 0 ){
                    currCourseDepartment = course.getName().split("-")[0];
                    if(dictMap.get(currCourseDepartment) == null){
                        ArrayList<String> courses = new ArrayList<>();
                        courses.add(course.getName().split("-")[1]);
                        dictMap.put(currCourseDepartment,courses);
                    }else{
                        ArrayList<String> tempArrayList = dictMap.get(currCourseDepartment);
                        tempArrayList.add(course.getName().split("-")[1]);
                        dictMap.put(currCourseDepartment,tempArrayList);
                    }
                }
            }
            Set<String> keyset = dictMap.keySet();
            for(String key :keyset){
                beautiful += key +":\n" + dictMap.get(key).toString().replace(",","\n");
            }
        }

        return  beautiful;
    }
    public void HideCameraOnNotSameUser(){
        if(! (teacher.getUID().equals(UID)) ){
            imgCamera.setVisibility(View.INVISIBLE);
        }
    }
}
