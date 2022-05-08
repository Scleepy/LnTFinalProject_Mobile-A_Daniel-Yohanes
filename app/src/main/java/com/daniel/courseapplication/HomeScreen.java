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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.courseapplication.fragments.FragmentAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

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

    }

    public void openProfile(View view){
        Intent intent = new Intent(view.getContext(), UserProfile.class);
        startActivity(intent);
    }


}