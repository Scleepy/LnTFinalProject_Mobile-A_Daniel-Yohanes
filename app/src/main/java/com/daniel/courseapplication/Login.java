package com.daniel.courseapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    EditText editTextEmailLogin, editTextPasswordLogin;

    TextView noAccount;

    SharedPreferences sharedPreferences, userInfoSharedPreferences;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    Dialog loadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        View view = window.getDecorView();

        if (Build.VERSION.SDK_INT >= 27) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        setContentView(R.layout.activity_login);

        String str = "No account? Create account";

        noAccount = findViewById(R.id.textNoAccount);

        SpannableString subString = new SpannableString(str);

        ForegroundColorSpan subStringRed = new ForegroundColorSpan(Color.RED);

        subString.setSpan(subStringRed, 11, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        noAccount.setText(subString);

        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Signup.class);
                startActivity(intent);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();

    }

    public void attemptLogin(View view){

        editTextEmailLogin = findViewById(R.id.editTextLengthSquare);
        editTextPasswordLogin = findViewById(R.id.editTextPassword);

        String email = editTextEmailLogin.getText().toString();
        String password = editTextPasswordLogin.getText().toString();

        Utility utility = new Utility();

        if(!utility.validEmail(email)){
            Toast.makeText(view.getContext(), "Email has to contain @ and ends with .com", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!utility.validPassword(password)){
            Toast.makeText(view.getContext(), "Password has to be more than 5 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        loadDialog = new Dialog(this);
        loadDialog.setContentView(R.layout.dialog_loading);
        loadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadDialog.setCancelable(false);

        loadDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){


                            sharedPreferences = getSharedPreferences("LOGININFO", MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("EMAIL", email);
                            editor.putString("PASSWORD", password);
                            editor.apply();

                            String UUID = mAuth.getCurrentUser().getUid();

                            userInfoSharedPreferences = getSharedPreferences("USERINFO", MODE_PRIVATE);
                            SharedPreferences.Editor editorUser = userInfoSharedPreferences.edit();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(UUID);

                            mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {

                                    if(!task.isSuccessful()){
                                       Toast.makeText(view.getContext(), "FAILED", Toast.LENGTH_SHORT).show();
                                    } else {

                                        String name = String.valueOf(task.getResult().child("name").getValue());
                                        String courseID = String.valueOf(task.getResult().child("courseID").getValue());
                                        String email = String.valueOf(task.getResult().child("email").getValue());
                                        String progressCounter = String.valueOf(task.getResult().child("progressCounter").getValue());
                                        String progressToday = String.valueOf(task.getResult().child("progressToday").getValue());
                                        String progressTotal = String.valueOf(task.getResult().child("progressTotal").getValue());
                                        String totalExp = String.valueOf(task.getResult().child("totalExp").getValue());
                                        String date = String.valueOf(task.getResult().child("date").getValue());

                                        editorUser.putString("NAME", name);
                                        editorUser.putString("COURSEID", courseID);
                                        editorUser.putString("EMAIL", email);
                                        editorUser.putString("PROGRESSCOUNTER", progressCounter);
                                        editorUser.putString("PROGRESSTODAY", progressToday);
                                        editorUser.putString("PROGRESSTOTAL", progressTotal);
                                        editorUser.putString("TOTALEXP", totalExp);
                                        editorUser.putString("DATE", date);
                                        editorUser.apply();

                                    }
                                    Toast.makeText(view.getContext(), "Login successful!", Toast.LENGTH_SHORT).show();

                                    MainActivity.mainActivity.finish();

                                    Intent intent = new Intent(view.getContext(), HomeScreen.class);
                                    startActivity(intent);
                                    finish();

                                }

                            });

                        } else {
                            loadDialog.cancel();
                            Toast.makeText(view.getContext(), "Login failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void backButtonLogin(View view){
        super.onBackPressed();
    }


}