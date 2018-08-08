package com.bignerdranch.android.weightlosstracker;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class weight_entry_fragment extends Fragment{
    private  DBHelper dbHelper;

    View myView;
    Button buttonCancel;
    Button buttonSave;
    Button buttonBack;
    CalendarView cv;
    public static final int CAM_REQUEST =1;
    private Button cambutton;
    private ImageView imageview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        dbHelper = new DBHelper(getActivity());

        myView = inflater.inflate(R.layout.fragment_weight_entry, container, false);


        buttonCancel = (Button) myView.findViewById(R.id.button_cancel);
        buttonSave = (Button) myView.findViewById(R.id.button_save);
        cv = (CalendarView) myView.findViewById(R.id.current_date);
        buttonBack=(Button) myView.findViewById(R.id.btn_WeightEntry_back);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int  year, int month, int dayOfMonth) {
                TextView dateTextView = (TextView)myView.findViewById(R.id.tv_current_date);
                dateTextView.setText( (month + 1) + "/" + dayOfMonth + "/" + year);
            }
        });


        buttonSave.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                String inputWeight =  ((TextView)myView.findViewById(R.id.current_weight)).getText().toString();
                String inputDate = ((TextView)myView.findViewById(R.id.tv_current_date)).getText().toString();

                if (inputWeight.equals("") )
                {
                    Toast.makeText(getActivity(),"enter weight", Toast.LENGTH_SHORT).show();

                }
               else if (inputDate.equals("") )
                {
                    Toast.makeText(getActivity(),"pick date", Toast.LENGTH_SHORT).show();

                }
                else{
                    dbHelper.insertRow(inputWeight,inputDate);
                    Toast.makeText(getActivity(),"Saved on history page", Toast.LENGTH_SHORT).show();

                }


            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new history_fragment()).commit();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new summary_fragment()).commit();
            }
        });

        cambutton = (Button) myView.findViewById(R.id.camera_btn);
        imageview =(ImageView)myView.findViewById(R.id.image_view);
        cambutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                //File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                // String pictureName =getPictureName();
                // File imageFile = new File(pictureDirectory, pictureName);
                // Uri pictureUri = Uri.fromFile(imageFile);
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                startActivityForResult(intent, CAM_REQUEST);
            }



        });

        if(!hasCamera()){
            cambutton.setEnabled(false);
        }


       return myView;
    }

    private boolean hasCamera(){
        return getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK){
            Bundle bundle = new Bundle();
            bundle = data.getExtras();
            Bitmap photo = (Bitmap)bundle.get("data");

            imageview.setImageBitmap(photo);
        }
    }




}
