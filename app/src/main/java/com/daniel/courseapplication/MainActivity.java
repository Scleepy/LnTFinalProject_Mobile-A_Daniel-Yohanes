package com.daniel.courseapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private boolean enableExit;

    Button loginButton, signupButton;
    Dialog helpDialog;
    TextView helpText;

    SharedPreferences sharedPreferences;

    private FirebaseAuth mAuth;

    public static Activity mainActivity;

    private DatabaseReference mDatabase;

    Dialog loadDialog;

    SharedPreferences sPUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mainActivity = this;

        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        View view = window.getDecorView();

        if (Build.VERSION.SDK_INT >= 27) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.logInButton);
        signupButton = findViewById(R.id.signUpButton);

        helpDialog = new Dialog(this);
        helpDialog.setContentView(R.layout.help);
        helpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Login.class);
                startActivity(intent);

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Signup.class);
                startActivity(intent);
            }
        });

        helpText = findViewById(R.id.helpText);
        helpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helpDialog.show();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences("LOGININFO", MODE_PRIVATE);

        String email = sharedPreferences.getString("EMAIL", "");
        String password = sharedPreferences.getString("PASSWORD", "");

        if(!email.equals("") && !password.equals("")){

            syncData();


        }
    }

    public void syncData(){

        loadDialog = new Dialog(this);
        loadDialog.setContentView(R.layout.dialog_loading);
        loadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadDialog.setCancelable(false);

        loadDialog.show();

        String UUID = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(UUID);

        sPUser = getSharedPreferences("USERINFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPUser.edit();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(UUID);

        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(!task.isSuccessful()){
                    Log.e("TAG", "ERROR RETRIEVING DATE");
                } else {

                    String dateTracker = String.valueOf(task.getResult().child("dateTracker").getValue());
                    String progressCounter = String.valueOf(task.getResult().child("progressCounter").getValue());
                    String progressToday = String.valueOf(task.getResult().child("progressToday").getValue());
                    String progressTotal = String.valueOf(task.getResult().child("progressTotal").getValue());

                    editor.putString("PROGRESSCOUNTER", progressCounter);
                    editor.putString("PROGRESSTODAY", progressToday);
                    editor.putString("PROGRESSTOTAL", progressTotal);
                    editor.putString("DATETRACKER", dateTracker);
                    editor.apply();

                    Log.d("TAG", "SYNCINGDATA");
                }

                loadDialog.cancel();

                Intent intent = new Intent(MainActivity.mainActivity.getApplicationContext(), HomeScreen.class);
                startActivity(intent);
                finish();



            }

        });

        Log.d("TAG", "DATA RETRIEVED");

    }


}