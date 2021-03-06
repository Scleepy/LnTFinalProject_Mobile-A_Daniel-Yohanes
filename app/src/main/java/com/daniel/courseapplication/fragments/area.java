package com.daniel.courseapplication.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.daniel.courseapplication.R;

public class area extends Fragment {

    EditText editTextLengthSquare, editTextResultSquare;
    TextView textViewProcessSquare;

    EditText editTextBaseTriangle, editTextHeightTriangle, editTextResultTriangle;
    TextView textViewProcessTriangle;

    EditText editTextRadiusCircle, editTextResultCircle;
    TextView textViewProcessCircle;

    public area(){

    }

    TextWatcher squareTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!editTextLengthSquare.getText().toString().isEmpty() && editTextLengthSquare.getText().toString().length() <= 20){

                String length = editTextLengthSquare.getText().toString();

                Double result = squareArea(Double.parseDouble(length));

                editTextResultSquare.setText(String.format("%,.2f", result));
                textViewProcessSquare.setText("Area = " + length + "²");
            } else if (editTextLengthSquare.getText().toString().isEmpty()) {
                editTextResultSquare.setText("Input length");
                textViewProcessSquare.setText("Area = ?²"); //max 15
            } else if (editTextLengthSquare.getText().toString().length() > 20){
                editTextResultSquare.setText("Max 20 digits");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    TextWatcher triangleTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!editTextBaseTriangle.getText().toString().isEmpty() && !editTextHeightTriangle.getText().toString().isEmpty() && (editTextBaseTriangle.getText().toString().length() + editTextHeightTriangle.getText().toString().length() <= 20)){

                String base = editTextBaseTriangle.getText().toString();
                String height = editTextHeightTriangle.getText().toString();

                Double result = triangleArea(Double.parseDouble(base), Double.parseDouble(height));

                editTextResultTriangle.setText(String.format("%,.2f", result));
                textViewProcessTriangle.setText("Area =     x " + base + " x " + height);

            } else if (editTextBaseTriangle.getText().toString().isEmpty() && editTextHeightTriangle.getText().toString().isEmpty()){
                editTextResultTriangle.setText("Input values");
                textViewProcessTriangle.setText("Area =     x ? x ?");

            } else if (editTextBaseTriangle.getText().toString().length() + editTextHeightTriangle.getText().toString().length() > 20){
                editTextResultTriangle.setText("Both max 20 digits");

            } else if (editTextBaseTriangle.getText().toString().isEmpty()){
                editTextResultTriangle.setText("Input base");

                String height = editTextHeightTriangle.getText().toString();

                textViewProcessTriangle.setText("Area =     x ? x " + height);

            } else if (editTextHeightTriangle.getText().toString().isEmpty()){
                editTextResultTriangle.setText("Input height");

                String base = editTextBaseTriangle.getText().toString();

                textViewProcessTriangle.setText("Area =     x " + base + " x ?");

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    TextWatcher circleTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!editTextRadiusCircle.getText().toString().isEmpty() && (editTextRadiusCircle.getText().toString().length() <= 30)){

                String radius = editTextRadiusCircle.getText().toString();

                Double result = circleArea(Double.parseDouble(radius));

                editTextResultCircle.setText(String.format("%,.2f", result));
                textViewProcessCircle.setText("Area = π x " + radius + "²");


            } else if (editTextRadiusCircle.getText().toString().isEmpty()) {
                editTextResultCircle.setText("Input radius");
                textViewProcessCircle.setText("Area = π x ?²");
            } else if (editTextRadiusCircle.getText().toString().length() > 30){
                editTextResultCircle.setText("Max 30 digits");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_area, container, false);

        //Code for square area
        editTextLengthSquare = view.findViewById(R.id.editTextLengthSquare);
        editTextResultSquare = view.findViewById(R.id.resultTextSquare);

        textViewProcessSquare = view.findViewById(R.id.formulaSquare);

        editTextLengthSquare.addTextChangedListener(squareTextWatcher);

        //Code for triangle area
        editTextBaseTriangle = view.findViewById(R.id.editTextBaseTriangle);
        editTextHeightTriangle = view.findViewById(R.id.editTextHeightTriangle);
        editTextResultTriangle = view.findViewById(R.id.resultTextTriangle);

        textViewProcessTriangle = view.findViewById(R.id.formulaTriangle);

        editTextBaseTriangle.addTextChangedListener(triangleTextWatcher);
        editTextHeightTriangle.addTextChangedListener(triangleTextWatcher);


        //Code for circle area
        editTextRadiusCircle = view.findViewById(R.id.editTextRadiusCircle);
        editTextResultCircle = view.findViewById(R.id.resultTextCircle);

        textViewProcessCircle = view.findViewById(R.id.formulaCircle);

        editTextRadiusCircle.addTextChangedListener(circleTextWatcher);


        return view;
    }

    public double squareArea(Double length){
        return length * length;
    }

    public double triangleArea(Double base, Double height){
        return (base * height)/2;
    }

    public double circleArea(Double radius){
        return Math.PI * radius * radius;
    }

}