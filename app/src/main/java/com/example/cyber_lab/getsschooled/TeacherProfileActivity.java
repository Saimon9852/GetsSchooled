package com.example.cyber_lab.getsschooled;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;

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
    private Button btnSaveChanges;
    private boolean changedProfileImage = false;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    //need to add statistics
    private Teacher teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        teacher = (Teacher)getIntent().getSerializableExtra("Teacher");
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_profile);
        textViewEmail = (TextView)findViewById(R.id.txtViewTeacherMail);
        textViewName = (TextView)findViewById(R.id.txtViewTeacherName);
        imgProfile = (ImageView)findViewById(R.id.profile_image);
        btnSaveChanges = (Button)findViewById(R.id.buttonSaveChanges);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
       textViewEmail.setText(teacher.getEmail());
        textViewName.setText(teacher.getName());

        imgProfile.setImageBitmap(imageBitmap);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProfileAdapter(teacher.getReviews());
        mRecyclerView.setAdapter(mAdapter);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();

            }
        });
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                reference.child("vEVqLmnDm5TIaqVNt6rsxQSP2LI3").setValue(Furi.toString());
                Log.e("update pictrue Uri", "5");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception exception) {
                Log.e("downloadImage", "failed");
            }
        });
    }
}
