package com.example.attestations;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PasswordManagerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView emptyMessage;
    private Button addButton;
    private PasswordRecordAdapter adapter;
    private SQLHelper dbHelper;
    private int currentUserId; // Предположим, пользователь авторизован
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_password_manager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        currentUserId = getIntent().getIntExtra("UserId",0);
        Log.w("PasswordManagerActivity","UserId: "+currentUserId);
        recyclerView = findViewById(R.id.recyclerView);
        emptyMessage = findViewById(R.id.emptyText);
        addButton = findViewById(R.id.button4);
        dbHelper = new SQLHelper(this);
        ArrayList<PasswordRecord> records = dbHelper.getPasswordRecordsList(currentUserId);

        if (records.isEmpty()) {
            emptyMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyMessage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new PasswordRecordAdapter(records);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPassActivity.class);
            intent.putExtra("UserId", currentUserId);
            startActivity(intent);
        });
    }
}