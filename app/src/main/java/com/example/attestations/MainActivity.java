package com.example.attestations;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText ETLogin;
    EditText ETPassword;
    Button BLogin;
    Button BRegistration;
    SQLHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ETLogin = findViewById(R.id.editTextLogin);
        ETPassword = findViewById(R.id.editTextPassword);
        BLogin = findViewById(R.id.button);
        BRegistration = findViewById(R.id.button2);
        sqlHelper = new SQLHelper(getApplicationContext());
        BLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = ETLogin.getText().toString();
                String pass = ETPassword.getText().toString();
                if(login.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Введите логин",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pass.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Введите пароль",Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = sqlHelper.getUser(login);
                if(user != null){
                    if(pass.equals(user.getPassword())){
                        Intent intent = new Intent(getApplicationContext(),PasswordManagerActivity.class);
                        intent.putExtra("UserId",user.getId());
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Не верный пароль",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Не верный логин",Toast.LENGTH_SHORT).show();
                }
            }
        });
        BRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}