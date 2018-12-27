package com.example.cyber_lab.getsschooled.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;

import com.example.cyber_lab.getsschooled.R;

import java.util.ArrayList;

import com.example.cyber_lab.getsschooled.objects.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    Context context;
    ArrayList<Review> reviews;
    String hint;

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton plus;
        EditText reviewReview;
        EditText reviewName;
        RatingBar reviewRatingBar;
        public ViewHolder(View itemView) {
            super(itemView);
            plus = (ImageButton) itemView.findViewById(R.id.review_plus);
            reviewReview = (EditText) itemView.findViewById(R.id.review_Review);
            reviewName = (EditText) itemView.findViewById(R.id.review_name);
            reviewRatingBar = (RatingBar) itemView.findViewById(R.id.reviewRatingBar);
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    try {
                        reviews.add(position + 1, new Review());
                        notifyItemInserted(position + 1);
                    }catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
                }
            });

            reviewReview.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    reviews.get(getAdapterPosition()).setMessage(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            reviewName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    reviews.get(getAdapterPosition()).setName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            reviewRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    reviews.get(getAdapterPosition()).setStars((int)rating);
                }
            });
        }
    }

    public ReviewAdapter(ArrayList<Review> reviews, Context context, String hint){
        this.reviews = reviews;
        this.hint = "";
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, final int i) {
        int x = holder.getLayoutPosition();

        if(reviews.get(x).getMessage().length() > 0) {
            holder.reviewReview.setText(reviews.get(x).getMessage());
            holder.reviewName.setText(reviews.get(x).getName());
            holder.reviewRatingBar.setRating(reviews.get(x).getStars());
        }
        else{
            holder.reviewReview.setText("");
            holder.reviewReview.setHint("Review");
            holder.reviewReview.requestFocus();
        }
    }

    public ArrayList<Review> getStepList(){
        return reviews;
    }

}