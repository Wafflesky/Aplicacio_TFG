package com.example.aplicacio.ViewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.example.aplicacio.Model.bitmapSingleton;

import java.util.LinkedList;

// https://examples.javacodegeeks.com/android/core/graphics/canvas-graphics/android-canvas-example/

/**
 *  Aquesta classe correspon a la vista que utilitza el CanvasActivity per a poder pintar la selecció.
 *  A diferència de les altres vistes aquesta s'ha de crear des del codi a causa de la complexitat d'aquesta.
 *  La vista esta formada per diferents elements que trobem en les vistes que agrupades ens permeten
 *  crear el canvas on pintem.
 *
 */
public class CanvasView extends View{

    public int width;
    public int height;

    private Bitmap mBitmap;

    private Bitmap redBitmap;
    private Bitmap greenBitmap;
    private Bitmap blueBitmap;

    private Bitmap newRedBitmap;
    private Bitmap newGreenBitmap;
    private Bitmap newBlueBitmap;

    private Bitmap bmp_Copy;
    private Bitmap output;

    private Path mPath;
    private Path paintPath;
    private Path redPath;
    private Path greenPath;
    private Path bluePath;
    private Path pathNou;

    Context context;

    private Paint mPaint;
    private Paint redPaint;
    private Paint greenPaint;
    private Paint bluePaint;
    private Paint freePaint;
    private Paint erasePaint;

    private float mX, mY;


    private static final float TOLERANCE = 5;

    private LinkedList<Float> xPoints;
    private LinkedList<Float> yPoints;

    private boolean paintNotFinished = true;
    private boolean mask = false;
    private boolean isPaint = false;
    private boolean erase = false;
    private boolean paintPoint = false;

    private String paintMode = null;

    /**
     * Constructor de la classe canvasView, aqui s'inicialitzen les variables corresponents a el
     * pinzell i la pintura utilitzats.
     * @param c Context de la vista
     * @param attrs Atributs que agafem de la classe de la que hereda
     */
    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
        xPoints = new LinkedList<>();
        yPoints = new LinkedList<>();

        // we set a new Path
        mPath = new Path();
        mPath.setFillType(Path.FillType.EVEN_ODD);

        redPath = new Path();
        redPath.setFillType(Path.FillType.EVEN_ODD);

        greenPath = new Path();
        greenPath.setFillType(Path.FillType.EVEN_ODD);

        bluePath = new Path();
        bluePath.setFillType(Path.FillType.EVEN_ODD);

        //we set a path for the Free painting mode
        paintPath = new Path();
        paintPath.setFillType(Path.FillType.EVEN_ODD);

        pathNou = new Path();
        pathNou.setFillType(Path.FillType.EVEN_ODD);
        pathNou.addRect(40,60,100,100, Path.Direction.CW);

        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(10f);
        mPaint.setARGB(255,255,0,0);

        redPaint = new Paint();
        redPaint.setAntiAlias(true);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setStrokeJoin(Paint.Join.ROUND);
        redPaint.setStrokeWidth(10f);
        redPaint.setARGB(150,255,0,0);

        greenPaint = new Paint();
        greenPaint.setAntiAlias(true);
        greenPaint.setStyle(Paint.Style.STROKE);
        greenPaint.setStrokeJoin(Paint.Join.ROUND);
        greenPaint.setStrokeWidth(10f);
        greenPaint.setARGB(150,0,255,0);

        bluePaint = new Paint();
        bluePaint.setAntiAlias(true);
        bluePaint.setStyle(Paint.Style.STROKE);
        bluePaint.setStrokeJoin(Paint.Join.ROUND);
        bluePaint.setStrokeWidth(10f);
        bluePaint.setARGB(150,0,0,255);

        //We also set a paint for the free painting mode so there arent any changes between them
        freePaint = new Paint();
        freePaint.setAntiAlias(true);
        freePaint.setStyle(Paint.Style.STROKE);
        freePaint.setStrokeJoin(Paint.Join.ROUND);
        freePaint.setStrokeWidth(50f);
        mPaint.setARGB(150,255,0,0);

        erasePaint = new Paint();
        erasePaint.setAntiAlias(true);
        erasePaint.setStyle(Paint.Style.STROKE);
        erasePaint.setStrokeJoin(Paint.Join.ROUND);
        erasePaint.setStrokeWidth(20f);
        erasePaint.setColor(Color.TRANSPARENT);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    /**
     * Funció on inicialitzem els bitmaps on es pinta i on s'escala el bitmap que conte la
     * imatge per a introduïrla en el canvas.
     * @param w Amplada del canvas
     * @param h Altura del canvas
     * @param oldw Amplada de la imatge
     * @param oldh altura de la imatge
     */
    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        height = oldh;
        width = oldw;
        output = Bitmap.createBitmap(w,
                h, Bitmap.Config.ARGB_8888);
        redBitmap = Bitmap.createBitmap(w,
                h, Bitmap.Config.ARGB_8888);
        greenBitmap = Bitmap.createBitmap(w,
                h, Bitmap.Config.ARGB_8888);
        blueBitmap = Bitmap.createBitmap(w,
                h, Bitmap.Config.ARGB_8888);

        newRedBitmap = Bitmap.createBitmap(w,
                h, Bitmap.Config.ARGB_8888);
        newGreenBitmap = Bitmap.createBitmap(w,
                h, Bitmap.Config.ARGB_8888);
        newBlueBitmap = Bitmap.createBitmap(w,
                h, Bitmap.Config.ARGB_8888);
        // your Canvas will draw onto the defined Bitmap
        mBitmap = bitmapSingleton.INSTANCE.getBitmap1();

        bmp_Copy = mBitmap.copy(Bitmap.Config.ARGB_8888,true);
        bmp_Copy = Bitmap.createScaledBitmap(bmp_Copy, w, h, false);
    }

    /**
     * Funció que es crida en bucle per a dibuixar tot el contingut de la pantalla i actualitzar el
     * que es pinta sobre aquesta. Aqui primer es dibuixa la imatge en el canvas i despres es
     * dibuixen les seleccions que ha fet l'usuari a sobre.
     * @param canvas Canvas on es pinta la imatge i la selecció
     */
    // override onDraw
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Canvas BackCanvas = new Canvas(bmp_Copy);

        canvas.drawBitmap(bmp_Copy,0f,0f,null);

        if(mask){
            //mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            //canvi del traç de la selecció a omplir l'interior d'aquest
            switch(paintMode){
                case "Necrotica":
                    redPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    mask = false;
                    break;
                case "Granulosa":
                    greenPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    mask = false;
                    break;
                case "Infectada":
                    bluePaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    mask = false;
                    break;
                    /*
                case 4:
                    erasePaint.setStyle(Paint.Style.STROKE);
                    */

            }
        }

        canvas.drawPath(redPath,redPaint);
        canvas.drawPath(greenPath,greenPaint);
        canvas.drawPath(bluePath,bluePaint);

    }

    /**
     * Funció que es crida quan la vista detecta que s'ha presionat la pantalla
     * @param x Coordenada horitzontal on s'ha clicat
     * @param y Coordenada vertical on s'ha clicat
     */
    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
        if(isPaint){
            paintPath.moveTo(x,y);
        }
        else {
            mPath.moveTo(x, y);
        }
        switch(paintMode){
            case "Necrotica":
                redPath.moveTo(x,y);
                break;
            case "Granulosa":
                greenPath.moveTo(x,y);
                break;
            case "Infectada":
                bluePath.moveTo(x,y);
                break;
        }
        mX = x;
        mY = y;
    }

    /**
     * Funció que es crida quan la vista detecta quan l'usuari mou el dit a una altra posició de la pantalla
     * @param x Coordenada horitzontal on s'ha mogut
     * @param y Coordenada vertical on s'ha mogut
     */
    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            if(isPaint){
                paintPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            }
            else {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            }
            switch(paintMode){
                case "Necrotica":
                    redPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                    break;
                case "Granulosa":
                    greenPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                    break;
                case "Infectada":
                    bluePath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                    break;
            }

            mX = x;
            mY = y;
        }
    }

    /**
     * Funció que es crida per a borrar la selecció que s'ha fet en el canvas corresponent a el
     * teixit seleccionat al spinner
     */
    public void clearCanvas() {

        mPath.reset();
        paintPath.reset();
        switch(paintMode){
            case "Necrotica":
                redPath.reset();
                redPaint.setStyle(Paint.Style.STROKE);
                break;
            case "Granulosa":
                greenPath.reset();
                greenPaint.setStyle(Paint.Style.STROKE);
                break;
            case "Infectada":
                bluePath.reset();
                bluePaint.setStyle(Paint.Style.STROKE);
                break;
        }
        invalidate();
        paintNotFinished = true;
        paintPoint = false;
        mask = false;
        mPaint.setStyle(Paint.Style.STROKE);
        xPoints = new LinkedList<>();
        yPoints = new LinkedList<>();
        mPath.setFillType(Path.FillType.EVEN_ODD);
        paintPath.setFillType(Path.FillType.EVEN_ODD);
        //matLoaded = false;

        //System.out.println("Amgous");
    }

    /**
     * Funció que es crida un cop l'usuari clica el botó de confirmació. Aqui es fa la operació per
     * a crear les màscares de la imatge i es on es guarda la informacio de les seleccions per a
     * poder-les utilitzar en les pantalles que venen més tard en la execució .
     */
    public void saveBitmap(){
        mask = false;
        Canvas canvas = new Canvas(bmp_Copy);
        Canvas endCanvas = new Canvas(output);

        Canvas redCanvas = new Canvas(redBitmap);
        Canvas greenCanvas = new Canvas(greenBitmap);
        Canvas blueCanvas = new Canvas(blueBitmap);

        Canvas newRedCanvas = new Canvas(newRedBitmap);
        Canvas newGreenCanvas = new Canvas(newGreenBitmap);
        Canvas newBlueCanvas = new Canvas(newBlueBitmap);


        Paint newRedPaint = redPaint;
        Paint newGreenPaint = greenPaint;
        Paint newBluePaint = bluePaint;

        Path newRedPath = redPath;
        Path newGreenPath = greenPath;
        Path newBluePath = bluePath;
        
        //freePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        //freePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        //mPaint.setStyle(Paint.Style.STROKE);

        //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        //mPath.setFillType(Path.FillType.INVERSE_WINDING);

        //endCanvas.drawBitmap(bmp_Copy,0f,0f,null);

        redCanvas.drawBitmap(bmp_Copy,0f,0f,null);
        greenCanvas.drawBitmap(bmp_Copy,0f,0f,null);
        blueCanvas.drawBitmap(bmp_Copy,0f,0f,null);

        newRedCanvas.drawBitmap(bmp_Copy,0f,0f,null);
        newGreenCanvas.drawBitmap(bmp_Copy,0f,0f,null);
        newBlueCanvas.drawBitmap(bmp_Copy,0f,0f,null);

        redCanvas.drawPath(redPath,redPaint);
        greenCanvas.drawPath(greenPath, greenPaint);
        blueCanvas.drawPath(bluePath,bluePaint);

        bitmapSingleton.INSTANCE.storeSelectedNecroticBitmap(redBitmap);
        bitmapSingleton.INSTANCE.storeSelectedGrainBitmap(greenBitmap);
        bitmapSingleton.INSTANCE.storeSelectedInfectedBitmap(blueBitmap);

        newRedPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        newRedPath.setFillType(Path.FillType.INVERSE_WINDING);
        newGreenPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        newGreenPath.setFillType(Path.FillType.INVERSE_WINDING);
        newBluePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        newBluePath.setFillType(Path.FillType.INVERSE_WINDING);

        newRedCanvas.drawPath(newRedPath,newRedPaint);
        newGreenCanvas.drawPath(newGreenPath,newGreenPaint);
        newBlueCanvas.drawPath(newBluePath,newBluePaint);

        bitmapSingleton.INSTANCE.storeNecroticBitmap(newRedBitmap);
        bitmapSingleton.INSTANCE.storeGrainBitmap(newGreenBitmap);
        bitmapSingleton.INSTANCE.storeInfectedBitmap(newBlueBitmap);

        newRedPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        newRedPath.setFillType(Path.FillType.EVEN_ODD);
        newGreenPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        newGreenPath.setFillType(Path.FillType.EVEN_ODD);
        newBluePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        newBluePath.setFillType(Path.FillType.EVEN_ODD);

    }

    /**
     * Funció que canvia el mode en que es pinta, per a canviar el teixit que s'està seleccionant
     * @param mode Valor del spinner
     */
    public void changeMode(String mode){
        System.out.println(mode);
        paintMode = mode;
    }


    /**
     * Funció que es crida una vegada es detecta que l'usuari ha deixat de presionar la pantalla.
     * Aqui s'acaba de pintar i s'omple la forma de la selecció per dins.
     */
    // when ACTION_UP stop touch
    private void upTouch() {
        if(isPaint){
            paintPath.lineTo(mX,mY);
        }
        else {
            mPath.lineTo(mX, mY);
        }
        switch(paintMode){
            case "Necrotica":
                redPath.moveTo(mX,mY);
                redPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                break;
            case "Granulosa":
                greenPath.moveTo(mX,mY);
                greenPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                break;
            case "Infectada":
                bluePath.moveTo(mX,mY);
                bluePaint.setStyle(Paint.Style.FILL_AND_STROKE);
                break;
        }
        paintNotFinished = false;

        pathNou.set(mPath);

        paintPoint = true;
        mask = true;


    }

    /**
     * Funció que detecta que hi ha hagut algun contacte amb la pantalla i crida a la funció
     * corresponent al cas.
     * @param event Aixo ens diu quin tipus d'acció ha fet l'usuari
     * @return Retorna si s'ha detectat a lo'usuari o no
     */
    //override the onTouchEvent
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (paintMode != null) {
            float x = event.getX();
            float y = event.getY();

            xPoints.add(x);
            yPoints.add(y);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startTouch(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    moveTouch(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    upTouch();
                    invalidate();
                    break;
            }
            return true;
        }
        return false;
    }


}

