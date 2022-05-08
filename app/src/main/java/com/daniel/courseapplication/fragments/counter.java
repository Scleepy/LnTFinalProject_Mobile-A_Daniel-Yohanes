package com.daniel.courseapplication.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.courseapplication.R;

public class counter extends Fragment {

    TextView counterText, counterTextName;

    Button btnAdd, btnMin, btnReset;

    SharedPreferences sPUser;

    public counter() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_counter, container, false);

        counterText = view.findViewById(R.id.counterNumber);
        counterTextName = view.findViewById(R.id.textNumberCounter);

        sPUser = this.getActivity().getSharedPreferences("USERINFO", Context.MODE_PRIVATE);

        String progressCounter = sPUser.getString("PROGRESSCOUNTER", "");

        counterText.setText(progressCounter);

        btnAdd = view.findViewById(R.id.btnAddition);
        btnMin = view.findViewById(R.id.btnSubstraction);
        btnReset = view.findViewById(R.id.btnReset);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int counterIndex = Integer.parseInt(counterText.getText().toString());
                counterIndex++;
                counterText.setText(Integer.toString(counterIndex));
            }
        });

        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int counterIndex = Integer.parseInt(counterText.getText().toString());

                if(counterIndex > 0){
                    counterIndex--;
                    counterText.setText(Integer.toString(counterIndex));
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterText.setText("0");
            }
        });


        return view;
    }
}