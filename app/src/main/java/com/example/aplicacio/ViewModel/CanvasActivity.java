package com.example.aplicacio.ViewModel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.aplicacio.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class CanvasActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private CanvasView customCanvas;
    private Spinner spinner;
    private String[] modes;
    private Button confirm;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i("OpenCV", "OpenCV loaded successfully");
                    Mat imageMat = new Mat();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        modes = new String[]{"Necrotica", "Granulosa",
                "Infectada"};

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        spinner = findViewById(R.id.spinner);
        spinner.setBackgroundColor(Color.RED);

        spinner.setOnItemSelectedListener(this);

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                modes);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spinner.setAdapter(ad);


        System.out.println(modes);

    }

    public void clearCanvas(View v) {
        customCanvas.clearCanvas();
    }

    public void confirmCanvas(View v){

        customCanvas.saveBitmap();
        //intent.putExtra("image", byteArray)
        Intent intent = new Intent(this, ConfirmActivity.class);
        startActivity(intent);

    }

    /*public void changeMode(View v){

        customCanvas.changeMode();
    }*/

    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(modes[position]);
        customCanvas.changeMode(modes[position]);
        switch(position){

            case 1:
                spinner.setBackgroundColor(Color.RED);
                break;
            case 2:
                spinner.setBackgroundColor(Color.GREEN);
                break;
            case 3:
                spinner.setBackgroundColor(Color.BLUE);
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        customCanvas.changeMode(null);

    }
}
