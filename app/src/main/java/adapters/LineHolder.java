package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.cyber_lab.getsschooled.R;

public class LineHolder extends RecyclerView.ViewHolder {

    public TextView title;

    public LineHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.main_line_title);
    }
}
