package com.bignerdranch.android.weightlosstracker;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;


public class settings_fragment extends Fragment{
    View myView;
    DBHelper dbHelper;

    Button buttonCancel;
    Button buttonSave;
    Button buttonBack;
    private TextView text;
    private DatePickerDialog datePickerDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_settings, container, false);
        dbHelper = new DBHelper(getActivity());

        buttonCancel = (Button) myView.findViewById(R.id.settings_button_cancel);
        buttonSave = (Button) myView.findViewById(R.id.settings_button_save);
        buttonBack=(Button) myView.findViewById(R.id.btn_settings_back);
        text =(TextView) myView.findViewById(R.id.goal_date_edittext);

        updateTextview();


        buttonSave.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                EditText weightGoal = (EditText) myView.findViewById(R.id.weight_goal_edittext);
                EditText goalDate = (EditText) myView.findViewById(R.id.goal_date_edittext);
                RadioButton genderMaleRadioBtn = (RadioButton) myView.findViewById(R.id.male_radio_button);
                RadioButton genderFemaleRadioBtn = (RadioButton) myView.findViewById(R.id.female_radio_button);
                EditText height = (EditText) myView.findViewById(R.id.height_edittext);


                dbHelper.deleteAllSettingsRow();
                dbHelper.insertSettingsRow("weightGoal", weightGoal.getText().toString());
                dbHelper.insertSettingsRow("goalDate", goalDate.getText().toString());
                String gender = "";
                if (genderMaleRadioBtn.isChecked()){
                    gender = "male";
                }
                else{
                    gender = "female";
                }

                dbHelper.insertSettingsRow("gender", gender);
                dbHelper.insertSettingsRow("height", height.getText().toString());

                Toast.makeText(getActivity(),"Record Saved", Toast.LENGTH_SHORT).show();
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new summary_fragment()).commit();
            }
        });


        buttonBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new summary_fragment()).commit();
            }
        });



        return myView;
    }

    private void updateTextview() {
        EditText weightGoal = (EditText) myView.findViewById(R.id.weight_goal_edittext);
        EditText goalDate = (EditText) myView.findViewById(R.id.goal_date_edittext);
        RadioButton genderMaleRadioBtn = (RadioButton) myView.findViewById(R.id.male_radio_button);
        RadioButton genderFemaleRadioBtn = (RadioButton) myView.findViewById(R.id.female_radio_button);
        EditText height = (EditText) myView.findViewById(R.id.height_edittext);

        String weightGoalStored = dbHelper.getSettingsRow("weightGoal");
        String goalDateStored = dbHelper.getSettingsRow("goalDate");
        String genderStored = dbHelper.getSettingsRow("gender");
        String heightStored = dbHelper.getSettingsRow("height");

        weightGoal.setText(weightGoalStored.toString());
        goalDate.setText(goalDateStored.toString());
        height.setText(heightStored.toString());
        if (genderStored.equals("male")) {
            genderMaleRadioBtn.setChecked(true);
        }
        if (genderStored.equals("female")) {
            genderFemaleRadioBtn.setChecked(true);

        }

    }


}
