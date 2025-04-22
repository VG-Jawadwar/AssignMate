package com.example.assignmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignmate.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    TextView register;
    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setContentView(R.layout.activity_login);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }


        databaseHelper = new DatabaseHelper(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        register = findViewById(R.id.signupText);

        binding.signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
                finish();
            }
        });

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            finish();
        }

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputUsername = binding.username.getText().toString().trim();
                String inputPassword = binding.password.getText().toString().trim();

                if (inputUsername.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (inputPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {


                    Boolean checkCredentials = databaseHelper.checkEmailPassword(inputUsername, inputPassword);

                    if (checkCredentials) {
                        Toast.makeText(getApplicationContext(), "Login Successfully!", Toast.LENGTH_SHORT).show();

                        editor = sharedPreferences.edit();
                        editor.putString("username", inputUsername);
                        editor.putBoolean("isLoggedIn", true);

                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


        binding.ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot, null);
                EditText phone = dialogView.findViewById(R.id.phoneBox);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userPhone = phone.getText().toString();
                        if (TextUtils.isEmpty(userPhone) && !Patterns.PHONE.matcher(userPhone).matches()) {
                            Toast.makeText(Login.this, "Enter your registered Mobile Number!", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Boolean SendCredentials = databaseHelper.getPassword(userPhone);
                            if (SendCredentials == true) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Account details sent via SMS!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Phone Number Not Registered!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
                dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS permission denied! Please Allow from System Settings!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void forgetpass(View view) {

    }
}