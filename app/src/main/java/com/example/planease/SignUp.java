package com.example.planease;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    Button callLogin, signup_btn;
    TextView title1;
    TextInputLayout fullname, username, email, password, confirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fullname = findViewById(R.id.name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpass = findViewById(R.id.confirmpass);
        signup_btn = findViewById(R.id.signup_btn);
        callLogin = findViewById(R.id.login_screen);

        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);

                android.util.Pair[] pairs = new android.util.Pair[8];

                pairs[0] = new android.util.Pair<View, String>(title1, "logo_image");
                pairs[1] = new android.util.Pair<View, String>(fullname, "logo_text");
                pairs[2] = new android.util.Pair<View, String>(username, "username_trans");
                pairs[3] = new android.util.Pair<View, String>(email, "fogetpass_trans");
                pairs[4] = new android.util.Pair<View, String>(password, "fogetpass_trans");
                pairs[5] = new android.util.Pair<View, String>(confirmpass, "password_trans");
                pairs[6] = new android.util.Pair<View, String>(signup_btn, "loginbtn_trans");
                pairs[7] = new android.util.Pair<View, String>(callLogin, "signup_page");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();
                String confirmPass = confirmpass.getEditText().getText().toString();
                String mail = email.getEditText().getText().toString();

                if (user.isEmpty() || pass.isEmpty() || confirmPass.isEmpty() || mail.isEmpty()) {
                    Toast.makeText(SignUp.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass.equals(confirmPass)) {
                    Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save user data to SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Username", user);
                editor.putString("Password", pass);
                editor.putString("Email", mail);
                editor.apply();

                Toast.makeText(SignUp.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

}