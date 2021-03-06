package com.migueroncallo.acelerometro;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private Button start,stop;
    private int sensordelay,sensortype;
    private SensorManager mSensorManager;
    private Sensor mAcelSensor;
    private TextView textX, textY, textZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start= (Button) findViewById(R.id.start);
        stop= (Button) findViewById(R.id.stop);
        Spinner spinnerdelay= (Spinner) findViewById(R.id.spinnerdelay);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.delay,R.layout.support_simple_spinner_dropdown_item);
        spinnerdelay.setAdapter(adapter);
        spinnerdelay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                switch (position){
                    case 0:
                        sensordelay=SensorManager.SENSOR_DELAY_NORMAL;
                        break;
                    case 1:
                        sensordelay=SensorManager.SENSOR_DELAY_GAME;
                        break;
                    case 2:
                        sensordelay=SensorManager.SENSOR_DELAY_FASTEST;
                        break;



                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnertype= (Spinner) findViewById(R.id.spinnertype);
        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this, R.array.type, R.layout.support_simple_spinner_dropdown_item);
        spinnertype.setAdapter(adapter1);
        spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        sensortype=Sensor.TYPE_ACCELEROMETER;
                        break;
                    case 1:
                        sensortype=Sensor.TYPE_LINEAR_ACCELERATION;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCapture(MainActivity.this,sensortype,sensordelay);

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopCapture();
            }
        });
        textX=(TextView) findViewById(R.id.textViewX);
        textY=(TextView) findViewById(R.id.textViewY);
        textZ=(TextView) findViewById(R.id.textViewZ);



    }

    public void startCapture(Context context, int type,int delay){

        mSensorManager= (SensorManager) getSystemService(context.SENSOR_SERVICE);
        mAcelSensor= mSensorManager.getDefaultSensor(type);
        mSensorManager.registerListener(this,mAcelSensor,delay);

    }

    public void stopCapture(){
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this, mAcelSensor);
        } else {
            Toast.makeText(getApplicationContext(),"mSensorManager null", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPause() {
        super.onPause();
        stopCapture();
        textX.setText("X= 0");
        textY.setText("Y= 0");
        textZ.setText("Z= 0");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        textX.setText("X= "+event.values[0] + "");
        textY.setText("Y= "+event.values[1] + "");
        textZ.setText("Z= "+event.values[2] + "");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
