package com.daniel.courseapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    TextView existingAccount;

    EditText courseEditText, nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;

    Button backButtonSignup;

    Dialog loadDialog;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        View view = window.getDecorView();

        if (Build.VERSION.SDK_INT >= 27) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        setContentView(R.layout.activity_signup);

        String str = "Have an account? Login";

        existingAccount = findViewById(R.id.textNoAccount);

        SpannableString subString = new SpannableString(str);

        ForegroundColorSpan subStringRed = new ForegroundColorSpan(Color.RED);

        subString.setSpan(subStringRed, 17, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        existingAccount.setText(subString);

        existingAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });


        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void attemptSignup(View view){

        courseEditText = findViewById(R.id.editTextCourseID);
        nameEditText = findViewById(R.id.editTextName);
        emailEditText = findViewById(R.id.editTextEmailAddressSignup);
        passwordEditText = findViewById(R.id.editTextPasswordSignup);
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPasswordSignup);

        String courseID = courseEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        Utility utility = new Utility();

        if(!utility.validCourseID(courseID)){
            Toast.makeText(view.getContext(), "Invalid course ID", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!utility.validName(name)){
            Toast.makeText(view.getContext(), "Name has to be more than 5 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!utility.validEmail(email)){
            Toast.makeText(view.getContext(), "Email has to contain @ and ends with .com", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!utility.validPassword(password)){
            Toast.makeText(view.getContext(), "Password has to be more than 5 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!utility.validConfirm(password, confirmPassword)) {
            Toast.makeText(view.getContext(), "Password and confirm password doesn't match", Toast.LENGTH_SHORT).show();
            return;
        }

        loadDialog = new Dialog(this);
        loadDialog.setContentView(R.layout.dialog_loading);
        loadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadDialog.setCancelable(false);

        loadDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            User newUser = new User(name, email, password, courseID);

                            String UUID = mAuth.getCurrentUser().getUid();

                            mDatabase.child("Users").child(UUID).setValue(newUser);

                            Toast.makeText(view.getContext(), "Account registered!", Toast.LENGTH_SHORT).show();
                            loadDialog.cancel();

                            Intent intent = new Intent(view.getContext(), Login.class);
                            startActivity(intent);
                            finish();

                        } else {
                            loadDialog.cancel();
                            Toast.makeText(view.getContext(), "Registration failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void backButtonSignup(View view){
        super.onBackPressed();
    }


}