package com.example.project.coursepicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by christian on 2018-03-12.
 */

public class userProfile extends AppCompatActivity {

    private DatabaseReference db_root, database_u_id;
    private ImageView picture;
    private TextView user_ID;
    private TextView user_nameView;
    private TextView user_ChangePW;
    private EditText user_Email;
    private EditText user_Phone;
    private Button user_Update;
    private String uid = "Ab123456"; // ------ need reference to session class??
    private String url = "https://firebasestorage.googleapis.com/v0/b/courseselection-2ce4a.appspot.com/o/Profile%20Picture%2Fprofile_picture.jpg?alt=media&token=9e0f361d-399e-4b09-bc78-201cb8c59683";
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        picture = findViewById(R.id.profilePicture);
        user_ID = findViewById(R.id.userID);
        user_nameView = findViewById(R.id.userNameViewer);
        user_ChangePW = findViewById(R.id.changePWBtn);
        user_Email = findViewById(R.id.userEmail);
        user_Phone = findViewById(R.id.userPhone);
        user_Update = findViewById(R.id.updateProfileBtn);

        session = new Session(getApplicationContext());

        Picasso.with(getApplicationContext()).load(url).into(picture);

        db_root = FirebaseDatabase.getInstance().getReference();
        database_u_id = db_root.child("Users");    //-----need reference to session class??

        database_u_id.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){

                for (DataSnapshot pSnapshot : dataSnapshot.getChildren()) {
                    if (uid.equals(pSnapshot.getKey())) {
                        user_ID.setText(/*session.getID()*/pSnapshot.getKey());
                        user_nameView.setText(pSnapshot.child("Name").getValue(String.class));
                        user_Email.setText(pSnapshot.child("Email").getValue(String.class));
                        user_Phone.setText(pSnapshot.child("Phone").getValue(String.class));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError){ }
        });

        user_Update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (user_ID.getText().toString().trim().length() != 0 && user_Phone.getText().toString().trim().length() != 0){
                    updateProfileDetails(user_Email.getText().toString(), user_Phone.getText().toString());
                }
                else {
                    Toast.makeText(getApplicationContext(),"Empty Field(s)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        user_ChangePW.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startChangePassword();
            }

        });

    }

    private void updateProfileDetails(String email, String phone){
        database_u_id.child("Email").setValue(email);
        database_u_id.child("Phone").setValue(phone);
        Toast.makeText(this,"Update Successful", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void startChangePassword(){
        Intent intent = new Intent(this, changePassword.class);
        startActivity(intent);
    }

}
