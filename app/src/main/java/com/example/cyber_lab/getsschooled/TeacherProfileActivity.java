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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import android.widget.Toast;

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
    private Intent intent;
    private TextView textViewName,textViewEmail,txtDecription,textViewPrice,textViewCourses,textViewRating;
    private Button btnSaveChanges;
    private ImageView imgCamera,imgEdit,getImgCamera,imgWhatsapp,imgProfile;
    private ArrayList<Review> list;
    private DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private ReviewAdapter reviewAdapter;
    DatabaseReference mDatabaseTeachers;

    //need to add statistics
    private Teacher teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        teacher = (Teacher)getIntent().getSerializableExtra("Teacher");
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_profile);
        txtDecription = (TextView)findViewById(R.id.txtViewTeacherDescp);
        textViewName = (TextView)findViewById(R.id.txtViewTeacherName);
        imgProfile = (ImageView)findViewById(R.id.profile_image);
        btnSaveChanges = (Button)findViewById(R.id.buttonSaveChanges);
        imgWhatsapp = (ImageView)findViewById(R.id.image_view_whatsapp);
        imgEdit = (ImageView)findViewById(R.id.image_view_edit);
        imgCamera = (ImageView)findViewById(R.id.image_view_camera);
        textViewPrice = (TextView)findViewById(R.id.textViewPrice);
        textViewCourses = (TextView)findViewById(R.id.textViewCourses);
        textViewRating = (TextView)findViewById(R.id.textViewRating);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
//        textViewEmail.setText(teacher.getEmail());
        textViewName.setText(teacher.getName());
        textViewPrice.setText("100");
        textViewCourses.setText("0");
        textViewRating.setText("5");
        txtDecription.setText(teacher.getBeutifulCoursesString());
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProfileAdapter(teacher.getReviews());
        mRecyclerView.setAdapter(mAdapter);

        pullPhoto();

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();

            }
        });

        list = (ArrayList<Review>)getIntent().getSerializableExtra("list");
        //To show at least one row
        if(list ==null || list.size() == 0)
            list = new ArrayList<Review>();
        list.add(new Review());


        reviewAdapter = new ReviewAdapter(list, this,"");
        //Setting the adapter
        mRecyclerView.setAdapter(reviewAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //for now this works , will have to push uid to user as well..
        mDatabaseTeachers = FirebaseDatabase.getInstance().getReference("Teachers").child(teacher.getUID());
        mDatabaseTeachers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teacher = dataSnapshot.getValue(Teacher.class);
                if(teacher.getReviews()!= null)
                    list = teacher.getReviews();
                reviewAdapter = new ReviewAdapter(list, mRecyclerView.getContext(),"");
                mRecyclerView.setAdapter(reviewAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                list = reviewAdapter.getStepList();
                teacher.setReviews(list);
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
        auth = FirebaseAuth.getInstance();
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

}
