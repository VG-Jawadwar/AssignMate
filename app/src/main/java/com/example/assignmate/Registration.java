package com.example.assignmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.assignmate.databinding.ActivityRegistrationBinding;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    ActivityRegistrationBinding binding;
    DatabaseHelper databaseHelper;
    ArrayList<String> messageParts;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[@#$%^&+=])(?=\\S+$).{4,}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConfirm.getText().toString();
                String phonenum = binding.signupPhone.getText().toString();

                if (email.equals("") || password.equals("") || confirmPassword.equals("") || phonenum.equals(""))
                    Toast.makeText(getApplicationContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else {
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && Patterns.PHONE.matcher(phonenum).matches() && PASSWORD_PATTERN.matcher(password).matches()) {
                        if (password.equals(confirmPassword)) {
                            Boolean checkUserEmail = databaseHelper.checkEmail(email);
                            Boolean checkUserPhone = databaseHelper.checkPhone(phonenum);

                            if (checkUserEmail == false || checkUserPhone == false) {
                                Boolean insert = databaseHelper.insertData(email, password, phonenum);

                                if (insert == true) {
                                    Toast.makeText(getApplicationContext(), "Signup Successfully!", Toast.LENGTH_SHORT).show();

                                    SmsManager smsManager = SmsManager.getDefault();
                                    String message = "Welcome to AssignMate! \n\n" + "Your account has been created successfully.\n\n" + "Username: " + email + "\n" + "Password: " + password + "\n\n" + "Login now to generate assignments easily!\n\n" + "Keep your credentials safe.\nHappy Learning!\n\n" + "- AssignMate Team | Vaibhav Jawadwar";
                                    phonenum = "+91" + phonenum;
                                    messageParts = smsManager.divideMessage(message);
                                    smsManager.sendMultipartTextMessage(phonenum, null, messageParts, null, null);

                                    Toast.makeText(getApplicationContext(), "Account details sent via SMS!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Signup Failed!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "User already exists! Please login", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid Password!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Credentials Entered!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

    }

}