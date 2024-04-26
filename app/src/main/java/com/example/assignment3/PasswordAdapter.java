package com.example.assignment3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.ViewHolder>{
    ArrayList<Password> passwords;
    Context context;
    public PasswordAdapter(Context c, ArrayList<Password> list){
        passwords = list;
        context = c;
    }
    @NonNull
    @Override
    public PasswordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.single_password_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordAdapter.ViewHolder holder, int position) {
        holder.tvUsername.setText(passwords.get(position).getUsername());
        holder.tvPassword.setText(passwords.get(position).getPassword());
        holder.tvUrl.setText(passwords.get(position).getUrl());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                deleteDialog.setTitle("Confirmation");
                deleteDialog.setMessage("Do you really want to delete it?");
                deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper database = new DatabaseHelper(context);
                        database.open();
                        database.deletePassword(passwords.get(holder.getAdapterPosition()).getId());
                        database.close();

                        passwords.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                });
                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                deleteDialog.show();

            }
        });

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog editDialog = new AlertDialog.Builder(context).create();
                View view = LayoutInflater.from(context).inflate(R.layout.edit_password_layout, null, false);
                editDialog.setView(view);

                EditText etName = view.findViewById(R.id.etName);
                EditText etPassword = view.findViewById(R.id.etPassword);
                Button btnUpdate = view.findViewById(R.id.btnUpdate);
                Button btnCancel = view.findViewById(R.id.btnCancel);

                etName.setText(passwords.get(holder.getAdapterPosition()).getUsername());
                etPassword.setText(passwords.get(holder.getAdapterPosition()).getPassword());

                editDialog.show();

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editDialog.dismiss();
                    }
                });

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = etName.getText().toString().trim();
                        String password = etPassword.getText().toString();
                        DatabaseHelper myDatabaseHelper = new DatabaseHelper(context);
                        myDatabaseHelper.open();
                        myDatabaseHelper.updatePassword(passwords.get(holder.getAdapterPosition()).getId(),
                                name, password);
                        myDatabaseHelper.close();

                        editDialog.dismiss();

                        passwords.get(holder.getAdapterPosition()).setUsername(name);
                        passwords.get(holder.getAdapterPosition()).setPassword(password);
                        notifyDataSetChanged();

                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return passwords.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvUsername, tvPassword, tvUrl;
        Button btnUpdate, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvPassword = itemView.findViewById(R.id.tvPassword);
            tvUrl = itemView.findViewById(R.id.tvUrl);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(itemView.getContext(), tvUsername.getText().toString(), Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}
