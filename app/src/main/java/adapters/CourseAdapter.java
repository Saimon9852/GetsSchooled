package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.MultiAutoCompleteTextView;

import com.example.cyber_lab.getsschooled.ManageCourses;
import com.example.cyber_lab.getsschooled.R;
import com.example.cyber_lab.getsschooled.ViewTutorsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    Context context;
    ArrayList<String> steps;
    Boolean cameFromTutor;
    ArrayList<String> stringArrayList;

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView plus, minus;
        AutoCompleteTextView step;
        /**
         * courses for auto complete, will add more before production!
         */
        public ViewHolder(View itemView) {
            super(itemView);
            plus = (ImageView) itemView.findViewById(R.id.course_list_add);
            minus = (ImageView) itemView.findViewById(R.id.course_list_remove);
            step = (AutoCompleteTextView) itemView.findViewById(R.id.step);
            if(!cameFromTutor){
                plus.setEnabled(false);
                plus.setVisibility(View.GONE);
                minus.setEnabled(false);
                minus.setVisibility(View.GONE);
                step.setEnabled(false);
            }else{
                ArrayAdapter adapter = new
                        ArrayAdapter(itemView.getContext(),android.R.layout.simple_list_item_1,
                        ManageCourses.getCourses());
                step.setAdapter(adapter);
                //for auto complete
                step.setThreshold(1);
            }

            /**
             * delete course if minus pressed.
             */
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
            /**
             * add item for holding course when plus clicked
             */
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
            //loads text to the list.
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

    public CourseAdapter(ArrayList<String> steps, Context context, boolean cameFromTutor){
        this.steps = steps;
        this.cameFromTutor = cameFromTutor;
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
        //if Course is non empty.
        if(steps.get(x).length() > 0) {
            holder.step.setText(steps.get(x));
        }
        else{
            holder.step.setText(null);
            holder.step.setHint("");
            holder.step.requestFocus();
        }
    }

    public ArrayList<String> getStepList(){
        return steps;
    }


}