package com.daniel.courseapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.daniel.courseapplication.MainActivity;
import com.daniel.courseapplication.R;

public class userProfile2 extends Fragment {

    Button btnLogout;
    SharedPreferences sharedPreferences, sharedPreferencesUser;

    public userProfile2(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        btnLogout = view.findViewById(R.id.btnLogout);

        sharedPreferences = view.getContext().getSharedPreferences("LOGININFO", Context.MODE_PRIVATE);
        sharedPreferencesUser = view.getContext().getSharedPreferences("USERINFO", Context.MODE_PRIVATE);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                SharedPreferences.Editor editorUser = sharedPreferencesUser.edit();
                editorUser.clear();
                editorUser.apply();

                Toast.makeText(view.getContext(), "Logout successful", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }




}