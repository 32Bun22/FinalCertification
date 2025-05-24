package com.example.attestations;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class AddPassActivity extends AppCompatActivity {
    private EditText serviceEditText, loginEditText, passwordEditText, noteEditText;
    private ImageView togglePasswordVisibility;
    private Button saveButton;
    private boolean isPasswordVisible = false;
    private SQLHelper dbHelper;
    private int userId; // получаем из Intent
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        serviceEditText = findViewById(R.id.edit_service);
        loginEditText = findViewById(R.id.edit_login);
        passwordEditText = findViewById(R.id.edit_password);
        noteEditText = findViewById(R.id.edit_note);
        togglePasswordVisibility = findViewById(R.id.toggle_password_visibility);
        saveButton = findViewById(R.id.btn_save);

        dbHelper = new SQLHelper(this);
        userId = getIntent().getIntExtra("UserId", 0);

        togglePasswordVisibility.setOnClickListener(v -> {
            if (isPasswordVisible) {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                togglePasswordVisibility.setImageResource(R.drawable.baseline_visibility_off_24);
            } else {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                togglePasswordVisibility.setImageResource(R.drawable.baseline_visibility_24);
            }
            passwordEditText.setSelection(passwordEditText.getText().length());
            isPasswordVisible = !isPasswordVisible;
        });

        saveButton.setOnClickListener(v -> savePasswordRecord());
    }
    private void savePasswordRecord() {
        String service = serviceEditText.getText().toString().trim();
        String login = loginEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String note = noteEditText.getText().toString().trim();

        if (service.isEmpty() || login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните обязательные поля", Toast.LENGTH_SHORT).show();
            return;
        }
        List<PasswordRecord> prevCount = dbHelper.getPasswordRecordsList(userId);
        dbHelper.insertPasswordRecord(userId, service, login, password, note);
        List<PasswordRecord> nowCount = dbHelper.getPasswordRecordsList(userId);
        if (nowCount.size() > prevCount.size()) {
            Toast.makeText(this, "Пароль сохранён", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, PasswordManagerActivity.class);
            intent.putExtra("UserId", userId);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Ошибка при сохранении", Toast.LENGTH_SHORT).show();
        }
    }
}