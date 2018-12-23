package Database;

import android.app.Activity;
import android.content.ReceiverCallNotAllowedException;
import android.support.v7.widget.RecyclerView;

import com.example.cyber_lab.getsschooled.ViewTutorsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.PipedInputStream;
import java.util.ArrayList;
import java.util.List;

import objects.Course;

import objects.Teacher;
import objects.TeacherData;

/**
 * Created by krupenghetiya on 27/06/17.
 */

public class Util {


    public static TeacherData getTeacherData(){

        List<Teacher>  mList = new ArrayList<>();
        ArrayList<Course>  co = new ArrayList<>();
        Course d = new Course("Computer science-CPP");
        Course d2 = new Course("Computer science-Java");
        Course d3 = new Course("Computer science-Linear Algebra");
        Course d4 = new Course("Software engineering-Software Structure");
        Course d5 = new Course("Computer engineering-Chip design");
        co.add(d);
        co.add(d2);
        co.add(d3);
        co.add(d4);
        co.add(d5);
        ArrayList<Course>  co2 = new ArrayList<>();
        Course t = new Course("Computer science-CPP");
        Course t2 = new Course("Computer science-Java");
        co2.add(t);
        co2.add(t2);

        ArrayList<Course>  co3 = new ArrayList<>();
        Course t4 = new Course("BIO engineering-Biology");
        Course t5 = new Course("Computer engineering-CPP");
        co3.add(t4);
        co3.add(t5);
        Teacher t1 = new Teacher("saimo3@gmail.com","saimon lankri","22222222",co,"54","https://yts.ag/assets/images/movies/smurfs_the_lost_village_2017/medium-cover.jpg");
        Teacher t6 = new Teacher("ehud@gmail.com","ehud plaksin","11111",co2,"54","https://yts.ag/assets/images/movies/smurfs_the_lost_village_2017/medium-cover.jpg");
        Teacher t3 = new Teacher("adiel@gmail.com","adiel izhak","24444444",co3,"54","https://yts.ag/assets/images/movies/smurfs_the_lost_village_2017/medium-cover.jpg");
        mList.add(t1);
        mList.add(t6);
        mList.add(t3);



        List<Course> cl = new ArrayList<>();
        Course c1 = new Course("Computer science-CPP");
        Course c2 = new Course("Computer science-Java");
        Course c3 = new Course("Computer science-Linear Algebra");
        Course c4 = new Course("Computer engineering-Chip design");
        Course c5 = new Course("Computer science-Infy 1");
        Course c6 = new Course("Computer engineering-Infy 2");
        Course c7 = new Course("Software engineering-OOP");
        Course c8 = new Course("Software engineering-Software Structure");
        Course c9 = new Course("BIO engineering-Chemistry");
        Course c10 = new Course("BIO engineering-Biology");
        Course c11 = new Course("Computer engineering-Infy 1");
        Course c12 = new Course("Computer science- OS");
        cl.add(c1);
        cl.add(c2);
        cl.add(c3);
        cl.add(c4);
        cl.add(c5);
        cl.add(c6);
        cl.add(c7);
        cl.add(c8);
        cl.add(c9);
        cl.add(c10);
        cl.add(c11);
        cl.add(c12);

        return new TeacherData(mList,cl);

    }
}
