package com.daniel.courseapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.courseapplication.R;

public class area extends Fragment {

    EditText userfieldSquare, editTextBaseTriangle, editTextHeightTriangle, resultTextTriangle, resultSquare;
    TextView formulaTriangle, processTextSquare;

    public area(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_area, container, false);

        userfieldSquare = view.findViewById(R.id.editTextLengthSquare);
        resultSquare = view.findViewById(R.id.resultTextSquare);
        processTextSquare = view.findViewById(R.id.processTextSquare);

        editTextBaseTriangle = view.findViewById(R.id.editTextBaseTriangle);
        editTextHeightTriangle = view.findViewById(R.id.editTextHeightTriangle);
        resultTextTriangle = view.findViewById(R.id.resultTextTriangle);
        formulaTriangle = view.findViewById(R.id.formulaTriangle);


        userfieldSquare.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!userfieldSquare.getText().toString().isEmpty() && userfieldSquare.getText().toString().length() <= 8){


                    String length = userfieldSquare.getText().toString();

                    Double result = squareArea(Double.parseDouble(length));

                    resultSquare.setText(Double.toString(result));

                    processTextSquare.setText(length + " x " + length);


                } else if (userfieldSquare.getText().toString().isEmpty()) {
                    resultSquare.setText("Input length");
                } else {
                    resultSquare.setText("Max 8 digits");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextBaseTriangle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!editTextBaseTriangle.getText().toString().isEmpty() && !editTextHeightTriangle.getText().toString().isEmpty()){

                    String base = editTextBaseTriangle.getText().toString();

                    String height = editTextHeightTriangle.getText().toString();

                    Double result = triangleArea(Double.parseDouble(base), Double.parseDouble(height));

                    resultTextTriangle.setText(Double.toString(result));

                    formulaTriangle.setText("Area =     x " + base + " x " + height);
                } else if (editTextBaseTriangle.getText().toString().isEmpty() && editTextHeightTriangle.getText().toString().isEmpty()){
                    resultTextTriangle.setText("Input values");
                    formulaTriangle.setText("Area =     x ? x ?");
                } else if (editTextBaseTriangle.getText().toString().isEmpty() && !editTextHeightTriangle.getText().toString().isEmpty()){
                    resultTextTriangle.setText("Input base");

                    String height = editTextHeightTriangle.getText().toString();

                    formulaTriangle.setText("Area =     x ? x " + height);
                } else if(!editTextBaseTriangle.getText().toString().isEmpty() && editTextHeightTriangle.getText().toString().isEmpty()){
                    resultTextTriangle.setText("Input height");

                    String base = editTextBaseTriangle.getText().toString();

                    formulaTriangle.setText("Area =     x " + base + " x ?");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextHeightTriangle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!editTextBaseTriangle.getText().toString().isEmpty() && !editTextHeightTriangle.getText().toString().isEmpty()){

                    String base = editTextBaseTriangle.getText().toString();

                    String height = editTextHeightTriangle.getText().toString();

                    Double result = triangleArea(Double.parseDouble(base), Double.parseDouble(height));

                    resultTextTriangle.setText(Double.toString(result));

                    formulaTriangle.setText("Area =     x " + base + " x " + height);
                } else if (editTextBaseTriangle.getText().toString().isEmpty() && editTextHeightTriangle.getText().toString().isEmpty()){
                    resultTextTriangle.setText("Input values");
                    formulaTriangle.setText("Area =     x ? x ?");
                } else if (editTextBaseTriangle.getText().toString().isEmpty() && !editTextHeightTriangle.getText().toString().isEmpty()){
                    resultTextTriangle.setText("Input base");

                    String height = editTextHeightTriangle.getText().toString();

                    formulaTriangle.setText("Area =     x ? x " + height);
                } else if(!editTextBaseTriangle.getText().toString().isEmpty() && editTextHeightTriangle.getText().toString().isEmpty()){
                    resultTextTriangle.setText("Input height");

                    String base = editTextBaseTriangle.getText().toString();

                    formulaTriangle.setText("Area =     x " + base + " x ?");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }

    public double squareArea(Double length){

        double result = length * length;

        return result;
    }

    public double triangleArea(Double base, Double height){

        double result = (base * height)/2;

        return result;
    }

}