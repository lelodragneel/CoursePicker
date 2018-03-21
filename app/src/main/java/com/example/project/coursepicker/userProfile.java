package com.example.project.coursepicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by christian on 2018-03-12.
 */

public class userProfile extends AppCompatActivity {

    DatabaseReference db_root, database_u_id;
    private TextView user_ID;
    private TextView user_nameView;
    private TextView user_ChangePW;
    private EditText user_Email;
    private EditText user_Phone;
    private Button user_Update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        db_root = FirebaseDatabase.getInstance().getReference();
        database_u_id = db_root.child("Users").child("Ab123456");    //------------------need reference to session user

        user_ID = (TextView) findViewById(R.id.userID);
        user_nameView = (TextView) findViewById(R.id.userNameViewer);
        user_ChangePW = (TextView) findViewById(R.id.changePWBtn);
        user_Email = (EditText) findViewById(R.id.userEmail);
        user_Phone = (EditText) findViewById(R.id.userPhone);
        user_Update = (Button) findViewById(R.id.updateProfileBtn);


        user_ID.setText(database_u_id.toString());               //---retrieve current User details & set in profile
        user_nameView.setText(database_u_id.child("Name").toString());
        user_Email.setText(database_u_id.child("Email").toString());
        user_Phone.setText(database_u_id.child("Phone").toString());

        user_Update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (user_ID.getText().toString().trim().length() != 0 && user_Phone.getText().toString().trim().length() != 0){
                    updateProfileDetails(user_Email.toString(), user_Phone.toString());
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
