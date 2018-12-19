package adapters;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.cyber_lab.getsschooled.R;

import java.util.ArrayList;
import java.util.List;

import objects.Teacher;

public class TutorAdapter extends RecyclerView.Adapter<TutorHolder> {

    private final List<Teacher> mTeachers;

    public TutorAdapter(ArrayList<Teacher> teachers) {
        mTeachers = teachers;
    }

    @Override
    public TutorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TutorHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.short_profile_view, parent, false));
    }

    @Override
    public void onBindViewHolder(TutorHolder holder, int position) {
        holder.setTeacher(mTeachers.get(position));
//        holder.setTextEmail(mTeachers.get(position).getEmail());
        holder.setTextName(mTeachers.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return mTeachers != null ? mTeachers.size() : 0;
    }

}