package uk.team_1.draw_n_shake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class CanvasActivity extends AppCompatActivity implements SensorEventListener {
    //--------------------Creating the canvas-------------------------------------------------------
    private CanvasView sketchPad; //creating the canvas

    //---------------------------------Drawing dot locations and variables--------------------------
    private int etchDrawRadius = 5;
    private float etchX;
    private float etchY;
    //----------------------------------------------------------------------------------------------
    //-----------------------sensor variables--------------------------------------------------------
    private Timer timer;
    private Handler handler; //as timer tasks are not allowed to call other objects directly


    private SensorManager sensorManager;
    private Sensor tiltControls;

    private float tiltX;
    private float tiltY;
    private float tiltZ;
    private long timeSinceSensorUpdate = 0;
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        View window = getWindow().getDecorView(); // android host window

        // uses bitwise OR to combine flags
        window.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | // hide nav buttons
                View.SYSTEM_UI_FLAG_FULLSCREEN        // hide status bar
        );


        //-----------------making etch follow the accelerometer values------------------------------
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        tiltControls = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, tiltControls, SensorManager.SENSOR_DELAY_NORMAL);

        //-----Finding centre of the screen, and creating the canvas as soon as the app starts------
        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);

        int screenWidth = screenSize.x;
        int screenHeight = screenSize.y;

        etchX = screenWidth / 2 - etchDrawRadius;
        etchY = screenHeight / 2 - etchDrawRadius ; // finding the centre of the screen

        sketchPad = new CanvasView(CanvasActivity.this);
        setContentView(sketchPad);
        //-----------------------------------------------------------------------------------------=

        //-------------------------Sensor code, and movment controls using timers-------------------

       handler = new Handler()
        {
            @Override
            public void handleMessage(Message message)   //this will send a message to canvas
            {                                           //so the etch point position gets updated
                sketchPad.invalidate();
            }
        };

        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                if (tiltX > tiltY)
                {
                    if (tiltX < 0)
                    {
                        tiltX += 5;
                    }
                    else
                    {
                        tiltX -= 5;
                    }
                }
                else
                {
                    if ( tiltY < 100)
                    {
                        etchY += 5;
                    }
                    else
                    {
                        etchY -= 5;
                    }
                }

            handler.sendEmptyMessage(0);
            }
        }, 0, 100); //this code will get executed every 100ms
                                  // updating the position of the etching point every 100ms

    }
    //----------------------------sensor events----------------------------------------------------
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) //sents information whenever sensor data
    {                                                   // changes

        Sensor tiltSensor = sensorEvent.sensor;

        if (tiltSensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            long currentTime = System.currentTimeMillis();
            //need to limit how often sensor update as they are sensitive
            if ((currentTime - timeSinceSensorUpdate)>100)
            {
                timeSinceSensorUpdate = currentTime;

                tiltX = sensorEvent.values[0];
                tiltX = sensorEvent.values[1];
                tiltZ = sensorEvent.values[2];
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {

    }
    //-------------------------------------------------------------------------------------------
    //-------------------------Creating a paintable area-------------------------------------------
    private class CanvasView extends View
    {
        private Paint etch;

        public CanvasView (Context context)
        {
            super(context);
            setFocusable(true);

            etch = new Paint();
        }
    //----------------------------------------------------------------------------------------------

    //-----------------------------Setting stile for the etching brush------------------------------
        public void onDraw ( Canvas screen)
        {
            etch.setStyle(Paint.Style.FILL);
            etch.setAntiAlias(true);
            etch.setTextSize(30f);

            screen.drawCircle(etchX, etchY, etchDrawRadius, etch);
        }
    //----------------------------------------------------------------------------------------------
    }

}
