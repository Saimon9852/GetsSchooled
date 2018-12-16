package adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cyber_lab.getsschooled.ChatActivity;
import com.example.cyber_lab.getsschooled.R;

public class LineHolder extends RecyclerView.ViewHolder   {

    public TextView title;
    public Button chatButton;
    public Button reviewButton;

    public LineHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.main_line_title);
        chatButton = (Button)itemView.findViewById(R.id.main_line_button_chat);
        reviewButton = (Button)itemView.findViewById(R.id.main_line_button_chat);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ChatActivity.class);
                intent.putExtra("mail",title.getText());
                v.getContext().startActivity(intent);
            }
        });
    }
}
