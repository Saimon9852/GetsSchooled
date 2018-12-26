package adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cyber_lab.getsschooled.TeacherProfileActivity;
import com.example.cyber_lab.getsschooled.ViewTutorsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.example.cyber_lab.getsschooled.R;

import objects.Teacher;


////Class adapter to single teacher in recycleView

public class TeachersAdapter extends RecyclerView.Adapter<TeachersAdapter.TeacherViewHolder> {

    List<Teacher> mList = new ArrayList<>();
    Picasso picasso;
    Activity _activity;

    public TeachersAdapter(List<Teacher> list_urls, Picasso p,Activity a) {
        this.mList = list_urls;
        this.picasso = p;
        this._activity = a;
    }


    @Override
    public TeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_teacher, parent, false);
        return new TeacherViewHolder(itemView);
    }


    //setting single_teacher.xml values from each teacher for mList, position present each one in list
    @Override
    public void onBindViewHolder(final TeacherViewHolder holder, final int position) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (position == 0){
            layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin));
        }else {
            layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin),0,(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin));
        }
        holder.card_view.setLayoutParams(layoutParams);


        if(mList.get(position).getPhoto()!= null){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            StorageReference load = storageReference.child("images/" + mList.get(position).getEmail() + "/profile");
            load.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    // Pass it to Picasso to download, show in ImageView and caching
                    Picasso.with(holder.card_view.getContext()).load(uri.toString()).into(holder.teacherPicture);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
//        holder.tv_title.setText(mList.get(position).getName());
        holder.teacherName.setText("Name: " +mList.get(position).getName());
//        holder.tv_rating.setText("Rating: " + mList.get(position).getPrice());
//        holder.tv_year.setText("Courses\n: " + mList.get(position).getCourseArrayList);
//        holder.tv_quality.setText("Quality: " +mList.get(position).getMobilePhoneNumber());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),TeacherProfileActivity.class);
                intent.putExtra("Teacher",mList.get(position));
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }


    //creating connection with single_teacher.xml (setting Id's to class)
    public class TeacherViewHolder extends RecyclerView.ViewHolder {
        private ImageView teacherPicture;
        private TextView teacherName;
        private TextView teacherRating;
        private TextView teacherCourses;
        private TextView teacherPrice;
        private TextView teacherTest;
        private CardView card_view;

        public TeacherViewHolder(View x) {
            super(x);
            teacherPicture = (ImageView) x.findViewById(R.id.teacher_picture);
            teacherName = (TextView) x.findViewById(R.id.teacher_name);
            teacherRating = (TextView) x.findViewById(R.id.teacher_rating);
            teacherCourses = (TextView) x.findViewById(R.id.teacher_courses);
            teacherPrice = (TextView) x.findViewById(R.id.teacher_price);
            teacherTest = (TextView) x.findViewById(R.id.teacher_test);
            card_view = (CardView) x.findViewById(R.id.card_view);
        }

    }



}
