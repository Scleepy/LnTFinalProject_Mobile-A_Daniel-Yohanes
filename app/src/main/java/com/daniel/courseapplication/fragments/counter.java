package com.daniel.courseapplication.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.courseapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class counter extends Fragment {

    TextView counterText, counterTextName;

    Button btnAdd, btnMin, btnReset;

    SharedPreferences sPUser;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

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

        SharedPreferences.Editor editor = sPUser.edit();

        String words[] = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty", "Twenty-one", "Twenty-two", "Twenty-three", "Twenty-four", "Twenty-five", "Twenty-six", "Twenty-seven", "Twenty-eight", "Twenty-nine", "Thirty", "Thirty-one", "Thirty-two", "Thirty-three", "Thirty-four", "Thirty-five", "Thirty-six", "Thirty-seven", "Thirty-eight", "Thirty-nine", "Forty", "Forty-one", "Forty-two", "Forty-three", "Forty-four", "Forty-five", "Forty-six", "Forty-seven", "Forty-eight", "Forty-nine", "Fifty", "Fifty-one", "Fifty-two", "Fifty-three", "Fifty-four", "Fifty-five", "Fifty-six", "Fifty-seven", "Fifty-eight", "Fifty-nine", "Sixty", "Sixty-one", "Sixty-two", "Sixty-three", "Sixty-four", "Sixty-five", "Sixty-six", "Sixty-seven", "Sixty-eight", "Sixty-nine", "Seventy", "Seventy-one", "Seventy-two", "Seventy-three", "Seventy-four", "Seventy-five", "Seventy-six", "Seventy-seven", "Seventy-eight", "Seventy-nine", "Eighty", "Eighty-one", "Eighty-two", "Eighty-three", "Eighty-four", "Eighty-five", "Eighty-six", "Eighty-seven", "Eighty-eight", "Eighty-nine", "Ninety", "Ninety-one", "Ninety-two", "Ninety-three", "Ninety-four", "Ninety-five", "Ninety-six", "Ninety-seven", "Ninety-eight", "Ninety-nine", "One hundred"};

        counterText.setText(progressCounter);

        counterTextName.setText(words[Integer.parseInt(counterText.getText().toString())]);

        btnAdd = view.findViewById(R.id.btnAddition);
        btnMin = view.findViewById(R.id.btnSubstraction);
        btnReset = view.findViewById(R.id.btnReset);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int counterIndex = Integer.parseInt(counterText.getText().toString());
                counterIndex++;

                counterText.setText(Integer.toString(counterIndex));

                if(counterIndex > 100){
                    counterTextName.setText("Keep it going!");
                } else {
                    counterTextName.setText(words[counterIndex]);
                }

                addExp();
                updateCounter(counterIndex);
                editor.putString("PROGRESSCOUNTER", Integer.toString(counterIndex));
                editor.apply();
            }
        });

        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int counterIndex = Integer.parseInt(counterText.getText().toString());

                if(counterIndex > 0){
                    counterIndex--;
                    counterText.setText(Integer.toString(counterIndex));
                    addExp();
                }

                if(counterIndex > 100){
                    counterTextName.setText("Keep it going!");
                } else {
                    counterTextName.setText(words[counterIndex]);
                }

                updateCounter(counterIndex);
                editor.putString("PROGRESSCOUNTER", Integer.toString(counterIndex));
                editor.apply();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(counterText.getText() != "0") {
                    counterText.setText("0");
                    counterTextName.setText("Zero");

                    addExp();
                    updateCounter(0);
                    editor.putString("PROGRESSCOUNTER", "0");
                    editor.apply();
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();

        return view;
    }

    public void updateCounter(Integer counterIndex){

        String UUID = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(UUID);

        mDatabase.child("progressCounter").setValue(counterIndex)
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
    }

    public void addExp(){
        String totalExp = sPUser.getString("TOTALEXP", "");

        int expValue = 10;

        int newValue = Integer.parseInt(totalExp) + expValue;

        SharedPreferences.Editor editor = sPUser.edit();

        editor.putString("TOTALEXP", Integer.toString(newValue));
        editor.apply();

        String UUID = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(UUID);

        mDatabase.child("totalExp").setValue(newValue)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("TAG", "SAVED EXP DATA");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "EXP DATA FAILED");
                    }
                });
    }

}