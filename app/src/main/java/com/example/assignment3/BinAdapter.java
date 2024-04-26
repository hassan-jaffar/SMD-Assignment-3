package com.example.assignment3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class BinAdapter extends RecyclerView.Adapter<BinAdapter.ViewHolder>{
    ArrayList<Password> passwords;
    Context context;
    public BinAdapter(Context c, ArrayList<Password> list){
        passwords = list;
        context = c;

    }
    @NonNull
    @Override
    public BinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.single_bin_item, parent, false);
        return new BinAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BinAdapter.ViewHolder holder, int position) {
        holder.tvUsername.setText(passwords.get(position).getUsername());
        holder.tvPassword.setText(passwords.get(position).getPassword());
        holder.tvUrl.setText(passwords.get(position).getUrl());

        holder.btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper myDatabaseHelper = new DatabaseHelper(context);
                myDatabaseHelper.open();
                myDatabaseHelper.restorePassword(passwords.get(holder.getAdapterPosition()).getId());
                myDatabaseHelper.close();

                passwords.remove(holder.getAdapterPosition());
                notifyDataSetChanged();


            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                deleteDialog.setTitle("Confirmation");
                deleteDialog.setMessage("The item will permanently be deleted!");
                deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper database = new DatabaseHelper(context);
                        database.open();
                        database.permanentDelete(passwords.get(holder.getAdapterPosition()).getId());
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
    }


    @Override
    public int getItemCount() {
        return passwords.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvUsername, tvPassword, tvUrl;
        Button btnRestore, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvPassword = itemView.findViewById(R.id.tvPassword);
            tvUrl = itemView.findViewById(R.id.tvUrl);
            btnRestore = itemView.findViewById(R.id.btnRestore);
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
