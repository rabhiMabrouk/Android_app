package com.example.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText inputN,inputP,inputP2;
    Button btnR;
    TextView txtS;
    SQLDatabase MYdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MYdb = new SQLDatabase(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtS = findViewById(R.id.txtSign);
        inputN = findViewById(R.id.inputNameS);
        inputP = findViewById(R.id.inputPasswordS);
        inputP2 = findViewById(R.id.inputPassword2S);
        btnR = findViewById(R.id.btnRegist);
        txtS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,loginActivity.class);
                RegisterActivity.this.startActivity(intent);
                finish();
            }
        });
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputN.getText().toString();
                String pass = inputP.getText().toString();
                String pass2 = inputP2.getText().toString();
                CheckInformationR(name,pass,pass2);
            }
        });
    }

    private void CheckInformationR(String name , String pass, String pass2) {
        if (name.isEmpty() || name.length() < 2 || name.length() > 50 || !name.matches("[a-zA-Zأ-ي ]+")) {
            errorMesseg(inputN,"only alphabetic between 2 and 50");
        }else if (!(pass.length()>7)) {
            errorMesseg(inputP,"At least 8 characters");
        }else if(!pass.equals(pass2))
            errorMesseg(inputP2,"password does not mach");
        else {
            Toast.makeText(this, "Call Register Methods", Toast.LENGTH_SHORT).show();
            registerUser(name,pass);
        }
    }

    private void registerUser(String username,String password) {
        boolean checkuser= MYdb.checkUsername(username);
        if(checkuser==true)
            errorMesseg(inputN,"user All reday exist");
        else{
            boolean res = MYdb.insertData(username,password);
            if(res==true){
                Intent intent = new Intent(RegisterActivity.this,loginActivity.class);
                RegisterActivity.this.startActivity(intent);
                Toast.makeText(this, "Register successfully", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(this, "Register filed", Toast.LENGTH_SHORT).show();

        }

    }

    private void errorMesseg(EditText input,String s) {
        input.setError(s);
        input.requestFocus();
    }
}