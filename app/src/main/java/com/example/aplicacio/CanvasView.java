package com.example.aplicacio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.renderscript.Type;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.sql.SQLOutput;
import java.util.LinkedList;

// https://examples.javacodegeeks.com/android/core/graphics/canvas-graphics/android-canvas-example/

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
    private float centerX;
    private float centerY;

    private static final float TOLERANCE = 5;

    private LinkedList<Float> xPoints;
    private LinkedList<Float> yPoints;

    private boolean paintNotFinished = true;
    private boolean mask = false;
    private boolean isPaint = false;
    private boolean erase = false;
    private boolean paintPoint = false;

    private String paintMode = null;


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
        //mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(10f);
        mPaint.setARGB(255,255,0,0);

        redPaint = new Paint();
        redPaint.setAntiAlias(true);
        redPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        redPaint.setStrokeJoin(Paint.Join.ROUND);
        redPaint.setStrokeWidth(10f);
        redPaint.setARGB(150,255,0,0);

        greenPaint = new Paint();
        greenPaint.setAntiAlias(true);
        greenPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        greenPaint.setStrokeJoin(Paint.Join.ROUND);
        greenPaint.setStrokeWidth(10f);
        greenPaint.setARGB(150,0,255,0);

        bluePaint = new Paint();
        bluePaint.setAntiAlias(true);
        bluePaint.setStyle(Paint.Style.STROKE);
        //mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
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
        //mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        erasePaint.setStrokeJoin(Paint.Join.ROUND);
        erasePaint.setStrokeWidth(20f);
        //erasePaint.setARGB(255,0,0,0);
        erasePaint.setColor(Color.TRANSPARENT);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        height = h;
        width = w;
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
        //mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mBitmap = bitmapSingleton.INSTANCE.getBitmap1();

        bmp_Copy = mBitmap.copy(Bitmap.Config.ARGB_8888,true);
        bmp_Copy = Bitmap.createScaledBitmap(bmp_Copy, w, h, false);
    }


    // override onDraw
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Canvas BackCanvas = new Canvas(bmp_Copy);

        canvas.drawBitmap(bmp_Copy,0f,0f,null);
        //canvas.drawPath(pathNou,redPaint);
        //Canvas endCanvas = new Canvas(output);

            /*
            if(mask){
                canvas.drawPath(pathNou,bluePaint);
            }
            */
        //canvas.drawPath(paintPath, freePaint);
        //canvas.drawPath(mPath, mPaint);
        //pintar end differents modes provoca un canvi de el que s'esta pintant. Seria millor mirar
        // d'aplicar diferents paints o algo, fer la recerca per si es pot pintar un mateix espai amb diferents paints


        //Sembla molt important pintar les coses aqui, fer un flag que em fagi la mascara aqui?
        //if(mask){

        //mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        //mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
            /*
                endCanvas.drawBitmap(bmp_Copy,0f,0f,null);
                endCanvas.drawPath(mPath,mPaint);
                endCanvas.drawPath(paintPath,freePaint);
                canvas.drawPath(pathNou,mPaint);


                canvas.drawBitmap(output,0f,0f,null);
*/


        //}

        if(mask){
            //mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
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
        /*
        else{
            mPaint.setStyle(Paint.Style.STROKE);
        }*/


        //else{
            /*
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setXfermode(null);
            int halfx = (int) width / 3;
            int halfy = (int) height / 3;
            canvas.drawBitmap(bmp_Copy, 0, 0, null);
             */

            /*
            //if(!erase) {

                switch (paintMode) {

                    case 1:
                        //Canvas grain = new Canvas();
                        //redPaint = mPaint;

                        //redPaint.setARGB(100, 255, 0, 0);
                        canvas.drawPath(redPath, mPaint);

                        break;
                    case 2:
                        //Canvas necro = new Canvas();
                        //mPaint.setARGB(100, 0, 255, 0);
                        canvas.drawPath(greenPath, mPaint);
                        break;
                    case 3:
                        //Canvas infec = new Canvas();
                        //mPaint.setARGB(100, 0, 0, 255);
                        canvas.drawPath(bluePath, mPaint);
                        break;

                }

                */

        canvas.drawPath(redPath,redPaint);
        canvas.drawPath(greenPath,greenPaint);
        canvas.drawPath(bluePath,bluePaint);

            /*
                if(paintMode == 4){
                    erasePaint.setStyle(Paint.Style.STROKE);
                    erasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                    canvas.drawPath(redPath,erasePaint);
                }

                */
        //}
        //else{

        //mPaint.setARGB(0,0,0,0);
        //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        //}


        //canvas.drawPath(mPath, mPaint);
        //canvas.drawPath(paintPath,freePaint);

        //canvas.setBitmap(bmp_Paint);
        // draw the mPath with the mPaint on the canvas when onDraw

        //tempCanvas.drawBitmap(tmp, 0, 0, mPaint);
        //}


        //Mirar si aqui puc mostrar un opencV fet
    }

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

    public void clearCanvas() {
        System.out.println("Amogus");
        mPath.reset();
        paintPath.reset();
        switch(paintMode){
            case "Necrotica":
                redPath.reset();
                break;
            case "Granulosa":
                greenPath.reset();
                break;
            case "Infectada":
                bluePath.reset();
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

    public void saveBitmap(){
        mask = false;
        Canvas canvas = new Canvas(bmp_Copy);//TODO: Aixi em marco el que es important
        Canvas endCanvas = new Canvas(output);//TODO: Aixi em marco el que es important

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

        //mPath.addPath(paintPath);//TODO: Aixi em marco el que es important
        /**
         * Aixo el que fa es ens posa en mode "Mascara2
         */
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//TODO: Aixi em marco el que es important
        mPath.setFillType(Path.FillType.INVERSE_WINDING);//TODO: Aixi em marco el que es important
        //freePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));

        //amb aixo no borra per sobre, pero no surt despres, mirar alguna altra forma
        //freePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        //DST OVER ens deixa amb la zona vermella per afora

        //freePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));

            /*
            redCanvas.drawBitmap(bmp_Copy,0,0,null);
            redCanvas.drawPath(redPath,redPaint);

            greenCanvas.drawBitmap(bmp_Copy,0,0,null);
            greenCanvas.drawPath(greenPath,greenPaint);

            blueCanvas.drawBitmap(bmp_Copy,0,0,null);
            blueCanvas.drawPath(bluePath,bluePaint);
            */

        //paintPath.setFillType(Path.FillType.INVERSE_WINDING);
        endCanvas.drawBitmap(bmp_Copy,0f,0f,null); //TODO: Aixi em marco el que es important

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

        newRedPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//TODO: Aixi em marco el que es important
        newRedPath.setFillType(Path.FillType.INVERSE_WINDING);//TODO: Aixi em marco el que es important
        newGreenPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//TODO: Aixi em marco el que es important
        newGreenPath.setFillType(Path.FillType.INVERSE_WINDING);//TODO: Aixi em marco el que es important
        newBluePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//TODO: Aixi em marco el que es important
        newBluePath.setFillType(Path.FillType.INVERSE_WINDING);//TODO: Aixi em marco el que es important

        newRedCanvas.drawPath(newRedPath,newRedPaint);
        newGreenCanvas.drawPath(newGreenPath,newGreenPaint);
        newBlueCanvas.drawPath(newBluePath,newBluePaint);
        //endCanvas.drawPath(paintPath,freePaint);
        //endCanvas.drawPath(mPath,freePaint);
        //endCanvas.drawPath(mPath,mPaint);//TODO: Aixi em marco el que es important
        //endCanvas.drawPath(paintPath,freePaint);
        //endCanvas.drawPath(paintPath,mPaint);
        //endCanvas.drawPath(paintPath,freePaint);
        //canvas.drawBitmap(output,0f,0f,null);//TODO: Aixi em marco el que es important

        //TODO: L'unica idea que tinc es que els canvas transparents estigui per sota del canvas pintat normal
        // com si fos una copia

        bitmapSingleton.INSTANCE.storeNecroticBitmap(newRedBitmap);
        bitmapSingleton.INSTANCE.storeGrainBitmap(newGreenBitmap);
        bitmapSingleton.INSTANCE.storeInfectedBitmap(newBlueBitmap);

        //bitmapSingleton.INSTANCE.storeCanvasBitmap(output);//TODO: Aixi em marco el que es important
        //output.recycle();

        newRedPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));//TODO: Aixi em marco el que es important
        newRedPath.setFillType(Path.FillType.EVEN_ODD);//TODO: Aixi em marco el que es important
        newGreenPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));//TODO: Aixi em marco el que es important
        newGreenPath.setFillType(Path.FillType.EVEN_ODD);//TODO: Aixi em marco el que es important
        newBluePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));//TODO: Aixi em marco el que es important
        newBluePath.setFillType(Path.FillType.EVEN_ODD);//TODO: Aixi em marco el que es important

    }
/*
    public void changeMode() {

        isPaint = !isPaint; //TODO: Aixo esta aqui perque es el que em deixa canviar entre stroke paint i Stroke fill
        //erase = !erase;
            /*
            mask = false;
            if(paintMode != 3){
                paintMode++;
            }
            else{
                paintMode = 1;
            }


    }*/

    public void changeMode(String mode){
        System.out.println(mode);
        paintMode = mode;
    }



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
        System.out.println(xPoints);
        System.out.println(yPoints);

        centerX = calculateCenter(xPoints);
        centerY = calculateCenter(yPoints);
        System.out.println(centerX);
        System.out.println(centerY);
        pathNou.set(mPath);

        paintPoint = true;
        mask = true;


    }

    private float calculateCenter(LinkedList<Float> point){

        float sum = 0;
        for (int i = 0; i < point.size(); i++){

            float temp = point.get(i);
            sum = sum + temp;
        }
        return sum/point.size();

    }

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

    //return false;
    // }

}

