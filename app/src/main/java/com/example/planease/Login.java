package com.example.planease;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    Button callSignUp, login_btn, forgetpass;
    TextView title1, title2;
    TextInputLayout email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        title1= findViewById(R.id.loginLogo1);
        title2 = findViewById(R.id.loginLogo2);
        email = findViewById(R.id.edittextemail);
        password= findViewById(R.id.password);
        forgetpass = findViewById(R.id.forgetpass);
        login_btn =findViewById(R.id.login_btn);
        callSignUp = findViewById(R.id.signup_screen);

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Login.this, SignUp.class);

                android.util.Pair[] pairs = new android.util.Pair[7];

                pairs[0] = new android.util.Pair<View, String>(title1, "logo_image");
                pairs[1] = new android.util.Pair<View, String>(title2, "logo_text");
                pairs[2] = new android.util.Pair<View, String>(email, "fogetpass_trans");
                pairs[3] = new android.util.Pair<View, String>(forgetpass, "fogetpass_trans");
                pairs[4] = new android.util.Pair<View, String>(password, "password_trans");
                pairs[5] = new android.util.Pair<View, String>(login_btn, "loginbtn_trans");
                pairs[6] = new android.util.Pair<View, String>(callSignUp, "signup_page");



                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getEditText().getText().toString();
                String passText = password.getEditText().getText().toString();

                if (emailText.isEmpty() || passText.isEmpty()) {
                    Toast.makeText(Login.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                String registeredEmail = sharedPreferences.getString("Email", "");
                String registeredPass = sharedPreferences.getString("Password", "");
                String registerUsername = sharedPreferences.getString("Username", "");

                if (emailText.equals(registeredEmail) && passText.equals(registeredPass)) {
                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("LoggedIn", true);
                    editor.apply();

                    Intent intent = new Intent(Login.this, TaskHome.class);
                    intent.putExtra("Email", emailText);
                    intent.putExtra("Username", registerUsername);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}