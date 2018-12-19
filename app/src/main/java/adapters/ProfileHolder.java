package adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cyber_lab.getsschooled.R;
import objects.Review;
import objects.Teacher;

public class ProfileHolder extends RecyclerView.ViewHolder {

    public TextView textViewName;
    public TextView textViewReview;

    Review review;

    public void setReview(Review review) {
        this.review = review;
    }
    public void setTextName(String name){
        textViewName.setText(name);
    }
    public void setTextReview(String txtReview){
        textViewReview.setText(txtReview);
    }

    public ProfileHolder(View itemView) {
        super(itemView);
        textViewName = (TextView) itemView.findViewById(R.id.ReviewByName);
        textViewReview = (TextView) itemView.findViewById(R.id.ReviewMessage);
    }

}
