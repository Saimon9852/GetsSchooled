package adapters;

import android.content.Context;
import android.media.Image;
import android.media.Rating;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cyber_lab.getsschooled.R;

import java.util.ArrayList;
import java.util.Iterator;

import objects.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    Context context;
    ArrayList<Review> reviews;
    boolean reviewed;
    String uid;
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView addComment;
        EditText reviewReview,reviewName;
        RatingBar reviewRatingBar;
        public ViewHolder(View itemView) {
            super(itemView);
            reviewReview = (EditText) itemView.findViewById(R.id.review_Review);
            reviewName = (EditText) itemView.findViewById(R.id.review_name);
            reviewRatingBar = (RatingBar) itemView.findViewById(R.id.review_rating_bar);
//            addComment = (ImageView) itemView.findViewById(R.id.review_list_add_comment);

//            addComment.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    Iterator<Review> iterator = reviews.iterator();
//                    if(position == reviews.size()-1 && !(reviews.get(position).isNull()))
//                        try {
//                            reviews.add(position + 1, new Review());
//                            notifyItemInserted(position + 1);
//                        }catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
//                }
//            });

            reviewReview.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    reviews.get(getAdapterPosition()).setMessage(s.toString());
                    reviews.get(getAdapterPosition()).setReviewerUID(uid);
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
                    reviews.get(getAdapterPosition()).setReviewerUID(uid);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            reviewRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    reviews.get(getAdapterPosition()).setStars((int)rating);
                    reviews.get(getAdapterPosition()).setReviewerUID(uid);
                }
            });
        }
    }

    public ReviewAdapter(ArrayList<Review> reviews, Context context, boolean reviewed, String uid){
        this.reviews = reviews;
        this.context = context;
        this.reviewed = reviewed;
        this.uid = uid;
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

    /**
     * Binds data to holder, Only last empty review have the add button.
     * only last empty review can be edited.
     * @param holder
     * @param i
     */
    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, final int i) {
        int x = holder.getLayoutPosition();
        if(reviews.get(x).getMessage().length() > 0 || reviewed) {
            holder.reviewReview.setText(reviews.get(x).getMessage());
            holder.reviewName.setText(reviews.get(x).getName());
            holder.reviewRatingBar.setRating(reviews.get(x).getStars());

            holder.reviewRatingBar.setIsIndicator(true);
            holder.reviewReview.setEnabled(false);
            holder.reviewName.setEnabled(false);
//            holder.addComment.setVisibility(View.INVISIBLE);
//            holder.addComment.setEnabled(false);
        }
        else{

            holder.reviewName.setHint("Name");
            holder.reviewReview.setHint("Review");
            holder.reviewReview.requestFocus();
            holder.reviewRatingBar.setIsIndicator(false);
            holder.reviewName.setEnabled(true);
            holder.reviewReview.setEnabled(true);
//            holder.addComment.setVisibility(View.VISIBLE);
//            holder.addComment.setEnabled(true);

        }
    }

    public ArrayList<Review> getStepList(){
        return reviews;
    }

    /**
     * checks if the user already reviewed the teacher.
     * @param UID
     * @return
     */
    public boolean isUIDin(String UID){
        for (Review review: reviews){
            if (review.getReviewerUID().equals(UID))
                return true;
        }
        return false;
    }
}