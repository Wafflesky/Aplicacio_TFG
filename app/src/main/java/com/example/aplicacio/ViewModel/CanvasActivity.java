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

/**
 * Classe on es troba l'eina per a pintar les seleccions de la ferida. Aqui trobarem tot el codi
 * relacionat amb connectar la vista amb el codi.
 */
public class CanvasActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private CanvasView customCanvas;
    private Spinner spinner;
    private String[] modes;
    private Button confirm;

    /**
     * Funció per a carregar la llibreria de OpenCVAndroid
     */
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i("OpenCV", "OpenCV loaded successfully");

                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    /**
     * Funció cridada en la creació de la classe, aqui connectem la vista amb el codi, afegim els
     * valors que es troben dins el spinner.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        modes = new String[]{"Necrotica", "Granulosa",
                "Infectada"};

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        spinner = findViewById(R.id.spinner);
        spinner.setBackgroundColor(Color.argb(100,255, 0, 0));

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

    /**
     * Funció que crida a la classe canvasView per a cridar la funcio clearCanvas.
     *
     * @param v La vista que trobem en el canvasActivity
     */
    public void clearCanvas(View v) {
        customCanvas.clearCanvas();
    }

    /**
     * Funció que crida a el canvasView per a guardar les imatges resultants.
     * Aqui es crida l'intent per a canviar la pantalla del ConfirmActivity
     *
     * @param v La vista que trobem en el canvasActivity
     */
    public void confirmCanvas(View v){

        customCanvas.saveBitmap();
        //intent.putExtra("image", byteArray)
        Intent intent = new Intent(this, ConfirmActivity.class);
        startActivity(intent);

    }

    /*public void changeMode(View v){

        customCanvas.changeMode();
    }*/

    /**
     * Funció que busca si openCVAndroid s'ha carregat o no
     */
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

    /**
     * Funció que canvia el color del spinner depenent del valor seleccionat. Tambe es crida a la
     * classe CanvasView per a canviar el mode de pintar
     * @param parent La vista que es troba per sobre la canvasView
     * @param view La vista que trobem en el canvasView
     * @param position Posició en que es troba el selector del spinner
     * @param id Parametre que es troba en la funcuó onItemSelected per a saber quin objecte s'ha triat
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(modes[position]);
        customCanvas.changeMode(modes[position]);
        switch(position){

            case 0:
                spinner.setBackgroundColor(Color.argb(100,255, 0, 0));
                break;
            case 1:
                spinner.setBackgroundColor(Color.argb(100,0, 255, 0));
                break;
            case 2:
                spinner.setBackgroundColor(Color.argb(100,0, 0, 255));
                break;

        }

    }

    /**
     * Funció en el cas que no s'hagi canviat el spinner
     * @param parent La vista que es troba per sobre la canvasView
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        customCanvas.changeMode(null);

    }
}
