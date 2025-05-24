package com.example.attestations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PasswordRecordAdapter extends RecyclerView.Adapter<PasswordRecordAdapter.ViewHolder> {

    private ArrayList<PasswordRecord> records;
    private Set<Integer> expandedItems = new HashSet<>();

    public PasswordRecordAdapter(ArrayList<PasswordRecord> records) {
        this.records = records;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_password_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PasswordRecord record = records.get(position);

        holder.serviceText.setText(record.getService());
        holder.loginText.setText(record.getLogin());

        boolean expanded = expandedItems.contains(position);
        holder.passwordText.setText(expanded ? record.getPassword() : "••••••••");

        if (expanded && record.getNote() != null && !record.getNote().isEmpty()) {
            holder.noteText.setText(record.getNote());
            holder.noteText.setVisibility(View.VISIBLE);
        } else {
            holder.noteText.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (expanded) {
                expandedItems.remove(position);
            } else {
                expandedItems.add(position);
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView serviceText, loginText, passwordText, noteText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceText = itemView.findViewById(R.id.serviceText);
            loginText = itemView.findViewById(R.id.loginText);
            passwordText = itemView.findViewById(R.id.passwordText);
            noteText = itemView.findViewById(R.id.noteText);
        }
    }
}

