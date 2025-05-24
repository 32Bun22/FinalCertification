package com.example.attestations;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class RegistrationActivity extends AppCompatActivity {
    EditText ETLogin;
    EditText ETPassword;
    EditText ETConfirmPassword;
    Button BRegistration;
    SQLHelper sqlHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ETLogin = findViewById(R.id.editTextLog);
        ETPassword = findViewById(R.id.editTextPass);
        ETConfirmPassword = findViewById(R.id.editTextConfirmPass);
        BRegistration = findViewById(R.id.button3);
        sqlHelper = new SQLHelper(getApplicationContext());
        BRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = ETLogin.getText().toString();
                String pass = ETPassword.getText().toString();
                String confPass = ETConfirmPassword.getText().toString();
                if(login.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Введите логин",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pass.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Введите пароль",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(confPass.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Введите подтверждение пароля",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!confPass.equals(pass)){
                    Toast.makeText(getApplicationContext(), "Пароли не совпадают",Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = sqlHelper.getUser(login);
                if(user.getId() == -1){
                    Log.w("REEEEEEEEEEEEEEEEEEEEEEEEEEEEE","dfsdfdf");
                    sqlHelper.insertUser(login,pass);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Логин занят",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}