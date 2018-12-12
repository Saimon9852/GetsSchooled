package adapters;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.cyber_lab.getsschooled.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import adapters.LineHolder;
import objects.Teacher;

public class LineAdapter extends RecyclerView.Adapter<LineHolder> {

    private final List<Teacher> mTeachers;

    public LineAdapter(ArrayList teachers) {
        mTeachers = teachers;
    }

    @Override
    public LineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LineHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_line_view, parent, false));
    }

    @Override
    public void onBindViewHolder(LineHolder holder, int position) {
        holder.title.setText(String.format(Locale.getDefault(), "%s, %s , %s",
                mTeachers.get(position).getName(),
                mTeachers.get(position).getEmail(),
                mTeachers.get(position).getMobilePhoneNumber()
        ));

    }

    @Override
    public int getItemCount() {
        return mTeachers != null ? mTeachers.size() : 0;
    }

}
