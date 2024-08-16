package com.example.firebaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {
    EditText inputN,inputPass;
    Button btnLog;
    TextView txtLog;
    SQLDatabase Mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtLog = findViewById(R.id.txtLog);
        btnLog = findViewById(R.id.btnLog);
        inputN = findViewById(R.id.inputEmailLog);
        inputPass = findViewById(R.id.inputPasswordLog);
        Mydb = new SQLDatabase(this);


        btnLog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = inputN.getText().toString();
                String password = inputPass.getText().toString();
                CheckInformationLogin(email,password);
            }
        });
        txtLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this,RegisterActivity.class);
                loginActivity.this.startActivity(intent);
                finish();
            }
        });
    }

    private void CheckInformationLogin(String name,String password) {
        if (name.isEmpty() || name.length() < 2 || name.length() > 50 || !name.matches("[a-zA-Zأ-ي ]+")) {
            errorMsg(inputN, "only alphabetic between 2 and 50");
        }else if((password.length()<7)) {
            errorMsg(inputPass,"At least 8 characters");
        }else {
            Toast.makeText(this, "Call Method login", Toast.LENGTH_SHORT).show();
            checkInfoLogin(name,password);
        }
    }

    private void checkInfoLogin(String name,String password) {
        boolean res= Mydb.checkUsername(name);
        if(res==false)
            errorMsg(inputN,"user name not exist");
        else {
            boolean r = Mydb.checkuserPassword(name,password);
            if(r==true){
                Intent intent = new Intent(loginActivity.this,HomeActivity.class);
                loginActivity.this.startActivity(intent);
                Toast.makeText(this, "login successfully", Toast.LENGTH_SHORT).show();
            }else errorMsg(inputPass,"password not correct");
        }
    }

    private void errorMsg(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}