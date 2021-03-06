package azzukunj.cottonuniversity.healthandfitnesskunjazzu;








import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import azzukunj.cottonuniversity.healthandfitnesskunjazzu.calorie.calorie;
import azzukunj.cottonuniversity.healthandfitnesskunjazzu.converttoless.converttoless;
import azzukunj.cottonuniversity.healthandfitnesskunjazzu.holocircularprogressbar.HoloCircularProgressBar;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;


public class sitUps extends AppCompatActivity implements SensorEventListener  {

    protected boolean mAnimationHasEnded = false;




    private Button HeartRate,start,stop,goback;

    private HoloCircularProgressBar mHoloCircularProgressBar;



    private ObjectAnimator mProgressBarAnimator;







    public SensorManager sensorManager;


    calorie CALORIE=new calorie();



    public static TextView z,caloriedisp;
    public int reps=5;
    public int i=0,countdown=0,totalcal;
    public float progress=0;
    public int c=0,get;
    Double calorieburnt=0.0;
    static int age,weight;

    static String xage,xweight,s,time,dd;


    Calendar calendar=Calendar.getInstance();
    SimpleDateFormat format=new SimpleDateFormat("HH:mm");
    SimpleDateFormat date=new SimpleDateFormat("dd");

private static int startstop=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sit_ups);


        HeartRate = findViewById(R.id.HeartRate);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        goback=findViewById(R.id.goback);
        z = (TextView) findViewById(R.id.caloriedispxml);
        caloriedisp = (TextView) findViewById(R.id.tv_steps);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorManager.registerListener(this, sensorManager.getDefaultSensor
                (Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);


        SharedPreferences sp = getSharedPreferences("preferences", MODE_PRIVATE);
        String id = sp.getString("id", "Email or Password is incorrect");
        xage = sp.getString(id + "age", "Email or Password is incorrect");
        xweight = sp.getString(id + "weight", "Email or Password is incorrect");

        try {
            age = Integer.parseInt(xage);
            weight = Integer.parseInt(xweight);
        } catch (NumberFormatException nfe) {
            System.out.print("wwwwrrroooonngggggggg");
        }


        time = format.format(calendar.getTime());
        dd = date.format(calendar.getTime());

        get = toMins(time);


        mHoloCircularProgressBar = (HoloCircularProgressBar) findViewById(
                R.id.holoCircularProgressBar);

        {

            mHoloCircularProgressBar.setProgressColor(Color.GREEN);

            //randomColor = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
            mHoloCircularProgressBar.setProgressBackgroundColor(Color.WHITE);
            animate(mHoloCircularProgressBar, null, 0, 1000);

        }


        HeartRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(sitUps.this, HeartRate.class);
                startActivity(I);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startstop=1;
                Toast.makeText(getApplicationContext(), "Started", LENGTH_SHORT).show();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startstop=0;
                z.setText(Integer.toString(i));
                caloriedisp.setText(s);
                animate(mHoloCircularProgressBar, null, 0.0f, 1000);
                mHoloCircularProgressBar.setMarkerProgress(0.0f);
                Toast.makeText(getApplicationContext(), "Stopped", LENGTH_SHORT).show();

            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(sitUps.this,Main2Activity.class);
               startActivity(i);
            }
        });
    }  public static int toMins(String s)
    {
        String[] hourmin=s.split(":");
        int hour=Integer.parseInt(hourmin[0]);
        int mins=Integer.parseInt(hourmin[1]);
        int hoursInMins=hour*60;
        return hoursInMins+mins;


    }


    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {


    }

    @Override
    public void onSensorChanged(SensorEvent event) {



        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {


            float zVal = event.values[2];


            reps = Math.round(zVal);
            if (startstop == 1) {
                if (reps > 5) {
                    if (c > 0) {


                        i++;
                        z.setText(Integer.toString(i));

                        calorieburnt = calorieburnt + CALORIE.calculate(age, weight);
                        s = converttoless.convert(calorieburnt);
                        caloriedisp.setText(s);

                        if (mProgressBarAnimator != null) {
                            mProgressBarAnimator.cancel();
                        }
                        float j = i;
                        progress = j / 20;
                        float putprogress = progress;
                        animate(mHoloCircularProgressBar, null, putprogress, 1000);
                        mHoloCircularProgressBar.setMarkerProgress(putprogress);

                        SharedPreferences sp = getSharedPreferences("preferences", MODE_PRIVATE);

                        String id=sp.getString("id","ppppppppppppp");

                        int plot = (int) calorieburnt.doubleValue();
                        SharedPreferences.Editor editor = sp.edit();
                        String getx = Integer.toString(get);
                        editor.putString(id+getx + "day", Integer.toString(plot));
                        editor.apply();


                        editor.putString(id+dd + "week", Integer.toString(plot));
                        editor.apply();


                        editor.putString(id+"r", Integer.toString(plot));
                        editor.apply();


                        editor.putString(id+"add or not", Integer.toString(1));
                        editor.apply();

                    /*String t=sp.getString("totalcal","0");
                    totalcal=Integer.parseInt(t);
                    totalcal=plot+totalcal;
                    editor.putString("totalcal",Integer.toString(totalcal));
                    editor.commit();
                    totalcal=0;
*/


                        c = 0;
                    }
                }
                if (reps < 6 && c < 1) {
                    c = 1;
                }
            }
        }
    }
    private void animate(final HoloCircularProgressBar progressBar,
                         final Animator.AnimatorListener listener) {
        final float progress = (float) (Math.random() * 2);
        int duration = 3000;
        animate(progressBar, listener, progress, duration);
    }

    private void animate(final HoloCircularProgressBar progressBar, final Animator.AnimatorListener listener,
                         final float progress, final int duration) {

        mProgressBarAnimator = ObjectAnimator.ofFloat(progressBar, "progress", progress);
        mProgressBarAnimator.setDuration(duration);

        mProgressBarAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationCancel(final Animator animation) {
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                progressBar.setProgress(progress);
            }

            @Override
            public void onAnimationRepeat(final Animator animation) {
            }

            @Override
            public void onAnimationStart(final Animator animation) {
            }
        });
        if (listener != null) {
            mProgressBarAnimator.addListener(listener);
        }
        mProgressBarAnimator.reverse();
        mProgressBarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                progressBar.setProgress((Float) animation.getAnimatedValue());
            }
        });
        progressBar.setMarkerProgress(progress);
        mProgressBarAnimator.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if ( keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {

        return;
    }
}

