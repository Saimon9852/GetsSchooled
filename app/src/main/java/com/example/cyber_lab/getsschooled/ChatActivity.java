package com.example.cyber_lab.getsschooled;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

import objects.ChatMessage;


public class ChatActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseListAdapter<ChatMessage> adapter;
    String toEmail;
    String fromEmail;
    private void displayChatMessage(){
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference("Messages")) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                Log.d("check messages","to email " + auth.getCurrentUser().getEmail()  + "from email to "+ model.getmessageTo() + "from email from " + model.getmessageFrom()  );
                if(model.getmessageTo().equals(auth.getCurrentUser().getEmail()) || model.getmessageFrom().equals(auth.getCurrentUser().getEmail()) ) {
                    TextView messageText = (TextView)v.findViewById(R.id.message_text);
                    TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                    TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                    // Set their text
                    messageText.setText(model.getMessageText());
                    messageUser.setText(model.getmessageFrom());

                    // Format the date before showing it
                    long time = model.getMessageTime();
                    Date date = new Date(time);
                    messageTime.setText(DateFormat.getDateInstance().format(date));
                }
            }
        };

        listOfMessages.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        auth = FirebaseAuth.getInstance();
        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            toEmail = extras.getString("mail");
            //The key argument here must match that used in the other activity
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference("Messages")
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getEmail(),
                                        toEmail
                                )
                        );

                // Clear the input
                input.setText("");
            }
        });
        displayChatMessage();

    }
}
