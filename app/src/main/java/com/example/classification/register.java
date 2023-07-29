package com.example.classification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class register extends AppCompatActivity {
    TextInputLayout username, password, repassword;
    Button signup;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.pass);
        repassword = (TextInputLayout) findViewById(R.id.repass);
        signup = (Button) findViewById(R.id.registerbtn);
        DB = new DBHelper(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();
                String repass = repassword.getEditText().getText().toString();

                if(user.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(register.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if(checkuser==false){
                            Boolean insert = DB.insertdoctorData(user, pass);
                            if(insert==true){
                                Toast.makeText(register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(register.this,MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(register.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(register.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                } }
        });




    }


}