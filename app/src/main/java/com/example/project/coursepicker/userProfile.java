package com.example.project.coursepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by christian on 2018-03-12.
 */

public class userProfile extends AppCompatActivity {

    DatabaseReference db_root, database_1, database_2, database_3;
    private TextView user_ID;
    private TextView user_nameView;
    private TextView user_ChangePW;
    private EditText user_Email;
    private EditText user_Phone;
    private Button user_Update;
    private Session session1;   //-------------------------------is this correct way to call session ???

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        db_root = FirebaseDatabase.getInstance().getReference();
        database_1 = db_root.child("Users").child(session1.getID());

        user_ID = (TextView) findViewById(R.id.userID);
        user_nameView = (TextView) findViewById(R.id.userNameViewer);
        user_ChangePW = (TextView) findViewById(R.id.changePWBtn);
        user_Email = (EditText) findViewById(R.id.userEmail);
        user_Phone = (EditText) findViewById(R.id.userPhone);
        user_Update = (Button) findViewById(R.id.updateProfileBtn);


        database_1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot_1) {
                String u_id = dataSnapshot_1.getValue().toString();
                user_ID.setText(u_id);        //gets userID <------ need to add reference to "session user"

                database_2 = db_root.child("Users").child(session1.getID()).child("Name");
                user_nameView = (TextView) findViewById(R.id.userNameViewer);


                database_2.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot_2) {
                        String u_name = dataSnapshot_2.getValue().toString();   //gets userName <------ need to add reference to "session user"
                        user_ID.setText(u_name);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


}
