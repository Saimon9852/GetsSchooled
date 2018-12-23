package adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.example.cyber_lab.getsschooled.R;

import objects.Teacher;

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

    @Override
    public void onBindViewHolder(TeacherViewHolder holder, final int position) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (position == 0){
            layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin));
        }else {
            layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin),0,(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin));
        }
        holder.card_view.setLayoutParams(layoutParams);

        picasso.load(mList.get(position).getMedium_cover_image()).placeholder(android.R.color.darker_gray).config(Bitmap.Config.RGB_565).into(holder.iv_cover);
        holder.tv_title.setText(mList.get(position).getName());
        holder.tv_genre.setText("Genre: " +mList.get(position).getEmail());
        holder.tv_rating.setText("Rating: " + mList.get(position).getPrice());
        holder.tv_year.setText("Year: " + mList.get(position).getDescription());
        holder.tv_quality.setText("Quality: " +mList.get(position).getMobilePhoneNumber());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),TeacherProfileActivity.class);
                Log.d("dudeD", mList.get(position).getName());
                intent.putExtra("Teacher",mList.get(position));
                Log.d("dudeD","mytest");
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class TeacherViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_cover;
        private TextView tv_title;
        private TextView tv_genre;
        private TextView tv_rating;
        private TextView tv_year;
        private TextView tv_quality;
        private CardView card_view;

        public TeacherViewHolder(View x) {
            super(x);
            iv_cover = (ImageView) x.findViewById(R.id.iv_cover);
            tv_title = (TextView) x.findViewById(R.id.tv_title);
            tv_genre = (TextView) x.findViewById(R.id.tv_genre);
            tv_rating = (TextView) x.findViewById(R.id.tv_rating);
            tv_year = (TextView) x.findViewById(R.id.tv_year);
            tv_quality = (TextView) x.findViewById(R.id.tv_quality);
            card_view = (CardView) x.findViewById(R.id.card_view);
        }

    }



}
