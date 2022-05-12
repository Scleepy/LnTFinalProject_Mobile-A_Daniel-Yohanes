package com.daniel.courseapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.daniel.courseapplication.R;

import org.w3c.dom.Text;

public class volume extends Fragment {

    EditText editTextSideCube, editTextResultCube;
    TextView textViewProcessCube;

    EditText editTextBaseSquarePyramid, editTextHeightSquarePyramid, editTextResultSquarePyramid;
    TextView textViewProcessSquarePyramid;

    EditText editTextLengthRectangularPyramid, editTextWidthRectangularPyramid, editTextHeightRectangularPyramid, editTextResultRectangularPyramid;
    TextView textViewProcessRectangularPyramid;

    EditText editTextRadiusCylinder, editTextHeightCylinder, editTextResultCylinder;
    TextView textViewProcessCylinder;

    public volume(){

    }

    TextWatcher cubeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if(!editTextSideCube.getText().toString().isEmpty() && editTextSideCube.getText().toString().length() <= 20){
                String side = editTextSideCube.getText().toString();

                Double result = cubeVolume(Double.parseDouble(side));

                editTextResultCube.setText(String.format("%,.2f", result));

                textViewProcessCube.setText("Volume = " + side + "³");
            } else if (editTextSideCube.getText().toString().length() > 20){
                editTextResultCube.setText("Max 20 digits");
            } else if (editTextSideCube.getText().toString().isEmpty()){
                textViewProcessCube.setText("Area = ?³");
                editTextResultCube.setText("Input side");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    TextWatcher squarePyramidTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!editTextBaseSquarePyramid.getText().toString().isEmpty() && !editTextHeightSquarePyramid.getText().toString().isEmpty() && (editTextBaseSquarePyramid.getText().toString().length() + editTextHeightSquarePyramid.getText().toString().length() <= 15)){

                String base = editTextBaseSquarePyramid.getText().toString();
                String height = editTextHeightSquarePyramid.getText().toString();

                Double result = squarePyramidVolume(Double.parseDouble(base), Double.parseDouble(height));

                editTextResultSquarePyramid.setText(String.format("%,.2f", result));
                textViewProcessSquarePyramid.setText("Volume =     x " + base + " x " + height);
            } else if (editTextBaseSquarePyramid.getText().toString().isEmpty() && editTextHeightSquarePyramid.getText().toString().isEmpty()) {
                editTextResultSquarePyramid.setText("Input values");
                textViewProcessSquarePyramid.setText("Volume =     x ? x ?");

            } else if (editTextBaseSquarePyramid.getText().toString().length() + editTextHeightSquarePyramid.getText().toString().length() > 15){

                editTextResultSquarePyramid.setText("Both max 15 digits");

            } else if (editTextBaseSquarePyramid.getText().toString().isEmpty()){
                editTextResultSquarePyramid.setText("Input base");

                String height = editTextHeightSquarePyramid.getText().toString();

                textViewProcessSquarePyramid.setText("Volume =     x ? x " + height);

            } else if (editTextHeightSquarePyramid.getText().toString().isEmpty()) {
                editTextResultSquarePyramid.setText("Input height");

                String base = editTextBaseSquarePyramid.getText().toString();

                textViewProcessSquarePyramid.setText("Volume =     x " + base + " x ?");
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    TextWatcher rectangularPyramidTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!editTextLengthRectangularPyramid.getText().toString().isEmpty() && !editTextWidthRectangularPyramid.getText().toString().isEmpty() && !editTextHeightRectangularPyramid.getText().toString().isEmpty() && (editTextLengthRectangularPyramid.getText().toString().length() + editTextWidthRectangularPyramid.getText().toString().length() + editTextHeightRectangularPyramid.getText().toString().length() <= 20)){
                String length = editTextLengthRectangularPyramid.getText().toString();
                String width = editTextWidthRectangularPyramid.getText().toString();
                String height = editTextHeightRectangularPyramid.getText().toString();

                Double result = rectangularPyramidVolume(Double.parseDouble(length), Double.parseDouble(width), Double.parseDouble(height));

                editTextResultRectangularPyramid.setText(String.format("%,.2f", result));
                textViewProcessRectangularPyramid.setText("Volume =     x " + length + " x " + width + " x " + height);

            } else if (editTextLengthRectangularPyramid.getText().toString().isEmpty() && editTextWidthRectangularPyramid.getText().toString().isEmpty() && editTextHeightRectangularPyramid.getText().toString().isEmpty()){
                editTextResultRectangularPyramid.setText("Input values");
                textViewProcessRectangularPyramid.setText("Volume =     x ? x ? x ?");

            } else if (editTextLengthRectangularPyramid.getText().toString().length() + editTextWidthRectangularPyramid.getText().toString().length() + editTextHeightRectangularPyramid.getText().toString().length() > 20) {
                editTextResultRectangularPyramid.setText("All max 20 digits");

            } else if (editTextLengthRectangularPyramid.getText().toString().isEmpty()){
                editTextResultRectangularPyramid.setText("Input length");

                if (editTextWidthRectangularPyramid.getText().toString().isEmpty()){

                    String height = editTextHeightRectangularPyramid.getText().toString();

                    textViewProcessRectangularPyramid.setText("Volume =     x ? x ? x " + height);

                    editTextResultRectangularPyramid.setText("Input length & width");


                } else if (editTextHeightRectangularPyramid.getText().toString().isEmpty()){

                    String width = editTextWidthRectangularPyramid.getText().toString();

                    textViewProcessRectangularPyramid.setText("Volume =     x ? x " + width + " x ?");

                    editTextResultRectangularPyramid.setText("Input length & height");

                } else {

                    String height = editTextHeightRectangularPyramid.getText().toString();
                    String width = editTextWidthRectangularPyramid.getText().toString();

                    textViewProcessRectangularPyramid.setText("Volume =     x ? x " + width + " x " + height);

                }

            } else if (editTextWidthRectangularPyramid.getText().toString().isEmpty()){
                editTextResultRectangularPyramid.setText("Input width");

                if (editTextLengthRectangularPyramid.getText().toString().isEmpty()){

                    String height = editTextHeightRectangularPyramid.getText().toString();

                    textViewProcessRectangularPyramid.setText("Volume =     x ? x ? x " + height);

                    editTextResultRectangularPyramid.setText("Input length & width");

                } else if (editTextHeightRectangularPyramid.getText().toString().isEmpty()){

                    String length = editTextLengthRectangularPyramid.getText().toString();

                    textViewProcessRectangularPyramid.setText("Volume =     x " + length + " x ? x ?");

                    editTextResultRectangularPyramid.setText("Input width & height");

                } else {

                    String length = editTextLengthRectangularPyramid.getText().toString();
                    String height = editTextHeightRectangularPyramid.getText().toString();

                    textViewProcessRectangularPyramid.setText("Volume =     x " + length + " x ? x " + height);

                }


            } else if (editTextHeightRectangularPyramid.getText().toString().isEmpty()){
                editTextResultRectangularPyramid.setText("Input height");

                if(editTextLengthRectangularPyramid.getText().toString().isEmpty()){
                    String width = editTextWidthRectangularPyramid.getText().toString();

                    textViewProcessRectangularPyramid.setText("Volume =     x ? x " + width + " x ?");

                    editTextResultRectangularPyramid.setText("Input length & height");

                } else if (editTextWidthRectangularPyramid.getText().toString().isEmpty()){
                    String length = editTextLengthRectangularPyramid.getText().toString();

                    textViewProcessRectangularPyramid.setText("Volume =     x " + length + " x ? x ?");

                    editTextResultRectangularPyramid.setText("Input width & height");
                } else {

                    String length = editTextLengthRectangularPyramid.getText().toString();
                    String width = editTextWidthRectangularPyramid.getText().toString();

                    textViewProcessRectangularPyramid.setText("Volume =     x " + length + " x " + width + " x ?");

                }


            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    TextWatcher cylinderTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!editTextRadiusCylinder.getText().toString().isEmpty() && !editTextHeightCylinder.getText().toString().isEmpty() && (editTextRadiusCylinder.getText().toString().length() + editTextHeightCylinder.getText().toString().length() <= 25)) {

                String radius = editTextRadiusCylinder.getText().toString();
                String height = editTextHeightCylinder.getText().toString();

                Double result = cylinderVolume(Double.parseDouble(radius), Double.parseDouble(height));

                editTextResultCylinder.setText(String.format("%,.2f", result));
                textViewProcessCylinder.setText("Volume = π x " + radius + "² x " + height);

            } else if (editTextRadiusCylinder.getText().toString().isEmpty() && editTextHeightCylinder.getText().toString().isEmpty()) {
                editTextResultCylinder.setText("Input values");
                textViewProcessCylinder.setText("Volume = π x ?² x ?");

            } else if (editTextRadiusCylinder.getText().toString().length() + editTextHeightCylinder.getText().toString().length() > 25){

                editTextResultCylinder.setText("Both max 25 digits");

            } else if (editTextRadiusCylinder.getText().toString().isEmpty()){
                editTextResultCylinder.setText("Input radius");

                String height = editTextHeightCylinder.getText().toString();

                textViewProcessCylinder.setText("Volume = π x ?² x " + height);

            } else if (editTextHeightCylinder.getText().toString().isEmpty()) {
                editTextResultCylinder.setText("Input height");

                String radius = editTextRadiusCylinder.getText().toString();

                textViewProcessCylinder.setText("Volume = π x " + radius + "² x ?");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_volume, container, false);

        //Code for cube volume
        editTextSideCube = view.findViewById(R.id.editTextSideSquare);
        editTextResultCube = view.findViewById(R.id.resultTextCube);
        textViewProcessCube = view.findViewById(R.id.formulaCube);

        editTextSideCube.addTextChangedListener(cubeTextWatcher);

        //Code for square pyramid volume
        editTextBaseSquarePyramid = view.findViewById(R.id.editTextBaseSquarePyramid);
        editTextHeightSquarePyramid = view.findViewById(R.id.editTextHeightSquarePyramid);
        editTextResultSquarePyramid = view.findViewById(R.id.resultTextSquarePyramid);

        textViewProcessSquarePyramid = view.findViewById(R.id.formulaSquarePyramid);

        editTextBaseSquarePyramid.addTextChangedListener(squarePyramidTextWatcher);
        editTextHeightSquarePyramid.addTextChangedListener(squarePyramidTextWatcher);

        //Code for retangular pyramid volume
        editTextLengthRectangularPyramid = view.findViewById(R.id.editTextLengthRectangularPyramid);
        editTextWidthRectangularPyramid = view.findViewById(R.id.editTextWidthRectangularPyramid);
        editTextHeightRectangularPyramid = view.findViewById(R.id.editTextHeightRectangularPyramid);
        editTextResultRectangularPyramid = view.findViewById(R.id.resultTextRectangularPyramid);

        textViewProcessRectangularPyramid = view.findViewById(R.id.formulaRectangularPyramid);

        editTextLengthRectangularPyramid.addTextChangedListener(rectangularPyramidTextWatcher);
        editTextWidthRectangularPyramid.addTextChangedListener(rectangularPyramidTextWatcher);
        editTextHeightRectangularPyramid.addTextChangedListener(rectangularPyramidTextWatcher);

        //Code for cylinder volume
        editTextRadiusCylinder = view.findViewById(R.id.editTextRadiusCylinder);
        editTextHeightCylinder = view.findViewById(R.id.editTextHeightCylinder);
        editTextResultCylinder = view.findViewById(R.id.resultTextCylinder);

        textViewProcessCylinder = view.findViewById(R.id.formulaCylinder);

        editTextRadiusCylinder.addTextChangedListener(cylinderTextWatcher);
        editTextHeightCylinder.addTextChangedListener(cylinderTextWatcher);

        return view;
    }

    public double cubeVolume(Double side){
        return side * side * side;
    }

    public double squarePyramidVolume(Double base, Double height){
        return (base * height)/3;
    }

    public double rectangularPyramidVolume(Double length, Double width, Double height){
        return (length * width * height)/3;
    }

    public double cylinderVolume(Double radius, Double height){
        return Math.PI * radius * radius * height;
    }

}