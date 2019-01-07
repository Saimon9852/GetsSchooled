package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import objects.Review;
import objects.Tutor;
import com.example.cyber_lab.getsschooled.R;
public class ProfileAdapter  extends RecyclerView.Adapter<ProfileHolder> {


    private final List<Review> mReviews;

    public ProfileAdapter(List<Review> reviews) {
        mReviews = reviews;
    }
    public ProfileAdapter(Tutor teacher) {
        mReviews = teacher.getReviewArrayList();
    }

    @Override
    public ProfileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProfileHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_recycle_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ProfileHolder holder, int position) {
        holder.setReview(mReviews.get(position));
        holder.setTextName(mReviews.get(position).getName());
        holder.setTextReview(mReviews.get(position).getMessage());
    }


    @Override
    public int getItemCount() {
        return mReviews != null ? mReviews.size() : 0;
    }

}
