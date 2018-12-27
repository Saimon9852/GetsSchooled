package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;

import com.example.cyber_lab.getsschooled.ManageCourses;
import com.example.cyber_lab.getsschooled.R;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    Context context;
    ArrayList<String> steps;
    String hint;

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton plus, minus;
        AutoCompleteTextView step;
        String[] courses={"Computer Science-Infi 1" ,
                "Computer Science-Introduction To Programming" ,
                "Computer Science-Logic And Group Theory" ,
                "Computer Science-Algorthmic Number Theory" ,
                "Computer Science-Numerical Systems" ,
                "Computer Science-Discrete Mathemathics" ,
                "Computer Science-Linear Algebra 1" ,
                "Computer Science-Infi 2"};
        public ViewHolder(View itemView) {
            super(itemView);
            plus = (ImageButton) itemView.findViewById(R.id.plus);
            minus = (ImageButton) itemView.findViewById(R.id.minus);
            step = (AutoCompleteTextView) itemView.findViewById(R.id.step);
            ArrayAdapter adapter = new
                    ArrayAdapter(itemView.getContext(),android.R.layout.simple_list_item_1,courses);

            step.setAdapter(adapter);
            step.setThreshold(1);

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    try {
                        steps.remove(position);
                        notifyItemRemoved(position);
                    }catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
                }
            });

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    try {
                        steps.add(position + 1, "");
                        notifyItemInserted(position + 1);
                    }catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
                }
            });

            step.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    steps.set(getAdapterPosition(), s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    public CourseAdapter(ArrayList<String> steps, Context context, String hint){
        this.steps = steps;
        this.hint = "Next " + hint;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CourseAdapter.ViewHolder holder, final int i) {
        int x = holder.getLayoutPosition();

        if(steps.get(x).length() > 0) {
            holder.step.setText(steps.get(x));
        }
        else{
            holder.step.setText(null);
            holder.step.setHint(hint);
            holder.step.requestFocus();
        }
    }

    public ArrayList<String> getStepList(){
        return steps;
    }

}