package adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyber_lab.getsschooled.R;
import com.example.cyber_lab.getsschooled.TeacherProfileActivity;

import objects.Teacher;

public class TutorHolder extends RecyclerView.ViewHolder   {

    public TextView textViewName;
    public TextView textViewEmail;
    public TextView textViewDescription;
    public TextView TextViewPrice;
    public ImageView imageViewWhatsapp;
    public Button btnToProfile;
    public TextView textViewstars;
    Teacher teacher;

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public void setTextName(String name){
        textViewName.setText(name);
    }
    public void setTextEmail(String email){
        textViewEmail.setText(email);
    }
    public TutorHolder(View itemView) {
        super(itemView);
        textViewName = (TextView) itemView.findViewById(R.id.name);
        btnToProfile = (Button) itemView.findViewById(R.id.btnProfile);
//        textViewEmail = (TextView) itemView.findViewById(R.id.email);
//        textViewDescription = (TextView) itemView.findViewById(R.id.description);
//        TextViewPrice = (TextView) itemView.findViewById(R.id.price);
//        textViewstars = (TextView) itemView.findViewById(R.id.review);
        imageViewWhatsapp = (ImageView) itemView.findViewById(R.id.image_view_whatsapp);
        imageViewWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://wa.me/" + teacher.getMobilePhoneNumber();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                v.getContext().startActivity(i);
            }
        });

        btnToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),TeacherProfileActivity.class);
                //put extra for teacher
                v.getContext().startActivity(intent);

            }
        });



    }

}
