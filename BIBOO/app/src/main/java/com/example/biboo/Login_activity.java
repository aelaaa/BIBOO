package com.example.biboo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login_activity extends AppCompatActivity {

    EditText username, password;
    TextView forgot, privacy, terms;
    Button login, signup;

    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.login_username_edittext);
        password = findViewById(R.id.login_password_edittext);
        forgot = findViewById(R.id.forgotpass_textview);
        privacy = findViewById(R.id.privacy_textview);
        terms = findViewById(R.id.terms_textview);
        login = findViewById(R.id.login_btn);
        signup = findViewById(R.id.signup_btn);
        login.setOnClickListener(v-> loginaccount());
        signup.setOnClickListener(v-> signupaccount());

        //setting up the database
        db = openOrCreateDatabase("BIBOO", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS BIBOO" + "(Username TEXT PRIMARY KEY, Password TEXT);");
        username.requestFocus();


    }
    //for signup
    private void signupaccount() {
        Intent signup = new Intent();
        startActivity(signup);
        finish();
    }

    // to Login btn and validation
    void loginaccount(){
        String user = username.getText().toString();
        String pass = password.getText().toString();

        if (user.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Please enter all the missing values", Toast.LENGTH_SHORT).show();
            clear();

        }else {
            cursor = db.rawQuery("SELECT * FROM BIBOO WHERE Username = '" + user + "'", null);
            if (cursor.getCount() == 0){
                Toast.makeText(this, "Incorrect Username and/or Password!", Toast.LENGTH_SHORT).show();
                clear();
            } else {
                cursor.moveToFirst();
                if (cursor.getString(1).equals(pass)){
                    // intent to homepage
                } else {
                    Toast.makeText(this, "Incorrect Username and/or Password!", Toast.LENGTH_SHORT).show();
                    clear();
                }
            }
        }
    }


    //clearing all inputs in edittext fields
    public void clear(){
        username.setText("");
        password.setText("");
        username.requestFocus();
    }
}