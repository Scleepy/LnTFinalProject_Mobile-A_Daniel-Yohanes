package com.daniel.courseapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.courseapplication.fragments.FragmentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeScreen extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    PagerAdapter pagerAdapter;
    FragmentManager fragmentManager;
    private FragmentAdapter adapter;

    BottomNavigationView navBar;
    Toolbar appbar;

    SharedPreferences userInfoSharedPreferences;

    TextView usernameTextView, courseIDTextView;

    public static Activity homeScreenActivity;
    private Handler handler = new Handler();

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        homeScreenActivity = this;

        super.onCreate(savedInstanceState);

        Window window = getWindow();

        View view = window.getDecorView();

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= 27) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        userInfoSharedPreferences = getSharedPreferences("USERINFO", MODE_PRIVATE);

        setContentView(R.layout.activity_home_screen);

        String name = userInfoSharedPreferences.getString("NAME", "");
        String courseID = userInfoSharedPreferences.getString("COURSEID", "");

        usernameTextView = findViewById(R.id.usernameText);
        courseIDTextView = findViewById(R.id.userClassID);

        usernameTextView.setText(name);
        courseIDTextView.setText(courseID);

        viewPager2 = findViewById(R.id.viewPager2);

        viewPager2.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getLifecycle()));

        navBar = findViewById(R.id.navigationBarBottom);

        navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){
                    case(R.id.counterfragment):
                        viewPager2.setCurrentItem(0);
                        break;
                    case(R.id.areafragment):
                        viewPager2.setCurrentItem(1);
                        break;
                    default:
                        viewPager2.setCurrentItem(2);
                        break;
                }

                return true;
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navBar.getMenu().findItem(R.id.counterfragment).setChecked(true);
                        break;
                    case 1:
                        navBar.getMenu().findItem(R.id.areafragment).setChecked(true);
                        break;
                    default:
                        navBar.getMenu().findItem(R.id.volumefragment).setChecked(true);
                        break;
                }
            }
        });



        appbar = findViewById(R.id.appbar);
        setSupportActionBar(appbar);
        setTitle("");
        appbar.setOutlineProvider(null);

        mAuth = FirebaseAuth.getInstance();


        String dateTracker = userInfoSharedPreferences.getString("DATETRACKER", "");

        Utility utility = new Utility();

        String dateToday = utility.generateDate();

        if(!dateTracker.equals(dateToday)){
            resetTimeToday();
            Log.d("TAG", "TIME TODAY RESET");
        }

        Log.d("TAG", "ONCREATE");

    }

    public void openProfile(View view){
        Intent intent = new Intent(view.getContext(), UserProfile.class);
        startActivity(intent);
    }

    public void openCreator(View view){
        Intent intent = new Intent(view.getContext(), CreatorProfile.class);
        startActivity(intent);
    }

    public void resetTimeToday(){

        userInfoSharedPreferences = getSharedPreferences("USERINFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfoSharedPreferences.edit();

        String UUID = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(UUID);

        mDatabase.child("progressToday").setValue(0)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("TAG", "SAVED COUNTER DATA");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "COUNTER DATA FAILED");
                    }
                });

        Utility utility = new Utility();

        String dateToday = utility.generateDate();

        mDatabase.child("dateTracker").setValue(dateToday)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("TAG", "SAVED COUNTER DATA");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "COUNTER DATA FAILED");
                    }
                });

        editor.putString("PROGRESSTODAY", "0");
        editor.putString("DATETRACKER", dateToday);
        editor.apply();

    }

    public void updateTime(){

        userInfoSharedPreferences = getSharedPreferences("USERINFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfoSharedPreferences.edit();

        String timeToday = userInfoSharedPreferences.getString("PROGRESSTODAY", "");
        String timeTotal = userInfoSharedPreferences.getString("PROGRESSTOTAL", "");

        Integer timeTodayInt = Integer.parseInt(timeToday);
        Integer timeTotalInt = Integer.parseInt(timeTotal);

        timeTodayInt++;
        timeTotalInt++;

        editor.putString("PROGRESSTODAY", Integer.toString(timeTodayInt));
        editor.putString("PROGRESSTOTAL", Integer.toString(timeTotalInt));
        editor.apply();

        String UUID = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(UUID);

        mDatabase.child("progressToday").setValue(timeTodayInt)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("TAG", "SAVED PROGRESS TODAY DATA");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "COUNTER DATA FAILED");
                    }
                });

        mDatabase.child("progressTotal").setValue(timeTotalInt)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("TAG", "SAVED TOTAL PROGRESS DATA");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "COUNTER DATA FAILED");
                    }
                });
    }

    public void startTimer(){

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }, 60000);


    }

    public void stopTimer(){
        handler.removeCallbacks(runnable);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateTime();
            handler.postDelayed(runnable, 60000);
        }
    };

    @Override
    protected void onStart(){
        super.onStart();

        Log.d("TAG", "STARTED");

        startTimer();

    }

    @Override
    protected void onStop(){
        super.onStop();

        Log.d("TAG", "STOPPED");

        stopTimer();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        Log.d("TAG", "DESTROYED");

        stopTimer();

    }





}