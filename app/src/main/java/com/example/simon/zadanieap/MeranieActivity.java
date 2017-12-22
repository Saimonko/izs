package com.example.simon.zadanieap;

import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MeranieActivity extends AppCompatActivity implements SensorEventListener {

    private final Handler mHandler = new Handler();


    double[] x;
    double[] y;

    private Runnable mTimer2;
    private LineGraphSeries<DataPoint> mSeries2;
    private double graphLastXValue = 5d;

    boolean paused = false;

    SensorManager sensorManager;
    double ax,ay,az;


    GraphView graph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_meranie);
        x= new double[50000];
        y= new double[50000];




        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);




        graph = (GraphView) findViewById(R.id.finalGraph);
        mSeries2 = new LineGraphSeries<>();
        graph.addSeries(mSeries2);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(400);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }



int i = 0;
    @Override
    public void onResume() {
        super.onResume();

        mTimer2 = new Runnable() {
            @Override
            public void run() {
                graphLastXValue += 0.5d;

                x[i]=graphLastXValue;
                y[i]=ay;
                i++;


                //172800 je 0.5 sekund v dni
                mSeries2.appendData(new DataPoint(graphLastXValue,ay), true, 172800);

                //ten delay je vzorkovacia frekvencia jedna vzorka 500ms  alebo 0.5s
                mHandler.postDelayed(this, 50);
            }
        };
        mHandler.postDelayed(mTimer2, 100);


    }


    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer2);
        super.onPause();
    }



    public void PauzaBtnClicked (View view){
        if(!paused) {
            onPause();
            graph.getViewport().setScalable(true);
            graph.getViewport().setScalableY(true);

            paused =true;
        }else{
            onResume();
            graph.getViewport().setScalable(false);
            graph.getViewport().setScalableY(false);
            paused = false;
        }
    }

    public void MeranieHotovoClicked(View view){



        setContentView(R.layout.graf_view);

        GraphView finalGraph = (GraphView) findViewById(R.id.finalGraph);

        View view1 = getWindow().getDecorView().findViewById(android.R.id.content);
        Bitmap graphScreenshot;

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data());
        finalGraph.getViewport().setXAxisBoundsManual(true);
        finalGraph.getViewport().setMaxX(4220);
        finalGraph.getViewport().setScalable(true);
        finalGraph.addSeries(series);

        graphScreenshot = getScreenShot(view1);


        //TODO GOOD LUCK S ODOSIELANIM
/*
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"123@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, " report");
        i.putExtra(Intent.EXTRA_TEXT   , "PFA");
        i.putExtra(Intent.EXTRA_STREAM, graphScreenshot);//pngFile

        startActivity(Intent.createChooser(i, "Send mail..."));


*/
    }

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public DataPoint[] data(){
        DataPoint[] values = new DataPoint[50000];
        for(int i=0;i<x.length;i++){
            DataPoint v = new DataPoint(x[i],y[i]);
            values[i] = v;
        }
        return values;
    }

}