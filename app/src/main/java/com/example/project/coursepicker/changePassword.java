package com.example.project.coursepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by christian on 2018-03-21.
 */

public class changePassword extends AppCompatActivity {

    DatabaseReference db_root, database_u_id;
    private EditText user_newPW;
    private EditText user_retypePW;
    private Button resetPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        db_root = FirebaseDatabase.getInstance().getReference();
        database_u_id = db_root.child("Users").child("Ab123456");

        user_newPW = (EditText) findViewById(R.id.newPassword);
        user_retypePW = (EditText) findViewById(R.id.retypePassword);
        resetPW = (Button) findViewById(R.id.newPasswordBtn);

        resetPW.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (user_newPW.getText().toString().trim().length() != 0 && user_retypePW.getText().toString().trim().length() != 0){
                    if (user_newPW.equals(user_retypePW)){
                        updatePassword(user_newPW.toString());
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Passwords Don't Match", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Empty Field(s)", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePassword(String password){
        database_u_id.child("Password").setValue(password);
        Toast.makeText(getApplicationContext(),"Update Successful", Toast.LENGTH_SHORT).show();
        finish();
    }

}
