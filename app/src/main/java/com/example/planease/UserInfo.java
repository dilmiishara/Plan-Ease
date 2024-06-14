package com.example.planease;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.planease.databinding.ActivityUserInfoBinding;

public class UserInfo extends AppCompatActivity {

    ActivityUserInfoBinding binding;
    String email;
    String registerUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email= getIntent().getStringExtra("Email");
        registerUsername = getIntent().getStringExtra("Username");

        binding.txtEmailup.setText(registerUsername);
        binding.txtUsername.setText(registerUsername);
        binding.txtEmail.setText(email);


        binding.btnlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });

        binding.btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditInfoDialog();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(UserInfo.this, TaskHome.class);
        intent.putExtra("Email", email);
        intent.putExtra("Username", registerUsername);
        startActivity(intent);
        finish();

    }

    private void showLogoutDialog() {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);

        // Set the title
        builder.setTitle("Signout");

        builder.setMessage("Really want to signout ?");

        // Add action buttons
        builder.setPositiveButton("Yes", (dialog, which) -> {

            Intent intent=new Intent(UserInfo.this,Login.class);
            startActivity(intent);
            finish();

        });

        builder.setNegativeButton("No", (dialog, which) -> {
            // Handle the "Cancel" button click
            dialog.cancel();
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.dialogTitleColor));
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setBackground(getResources().getDrawable(R.drawable.rounded_rectangle));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.dialogTitleColor));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setBackground(getResources().getDrawable(R.drawable.rounded_rectangle));
    }

    private void showEditInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.update_user_info, null);
        builder.setView(dialogView);

        final EditText editTextNewUsername = dialogView.findViewById(R.id.editTextNewUsername);
        final EditText editTextNewEmail = dialogView.findViewById(R.id.editTextNewEmail);

        editTextNewUsername.setText(registerUsername);
        editTextNewEmail.setText(email);

        builder.setTitle("Edit Info")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String newUsername = editTextNewUsername.getText().toString().trim();
                        String newEmail = editTextNewEmail.getText().toString().trim();

                        if (!newUsername.isEmpty() && !newEmail.isEmpty()) {
                            registerUsername = newUsername;
                            email = newEmail;

                            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Username", newUsername);
                            editor.putString("Email", newEmail);
                            editor.apply();

                            binding.txtUsername.setText(newUsername);
                            binding.txtEmail.setText(newEmail);
                            binding.txtEmailup.setText(newUsername);

                            Toast.makeText(UserInfo.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserInfo.this, "Both fields are required", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.dialogTitleColor));
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setBackground(getResources().getDrawable(R.drawable.rounded_rectangle));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.dialogTitleColor));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setBackground(getResources().getDrawable(R.drawable.rounded_rectangle));
    }

}