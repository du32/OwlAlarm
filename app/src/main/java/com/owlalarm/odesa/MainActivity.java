package com.owlalarm.odesa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;


public class MainActivity extends FragmentActivity {
    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);
    private TimePicker alarm_timePicker;
    TextView textView1;
    Button button;

    Sharing_Connection btn_Con =new Sharing_Connection(this);


    private static TextView textView2;
    public static TextView getTextView2() {
        return textView2;
    }
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        textView1 = (TextView)findViewById(R.id.msg1);
        textView1.setText(timeHour + ":" + timeMinute);
        textView2 = (TextView)findViewById(R.id.msg2);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

        View.OnClickListener listener1 = new View.OnClickListener() {

            public void onClick(View view) {

                textView2.setText("Alarm is on....!");


                Bundle bundle = new Bundle();



                bundle.putInt(Constants.HOUR, timeHour);
                bundle.putInt(Constants.MINUTE, timeMinute);

                Fragment fragment = new Fragment(new MyHandler());

                fragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(fragment,Constants.TIME_PICKER);
                transaction.commit();
            }
        };


        View.OnClickListener listener2 = new View.OnClickListener() {
            public void onClick(View view) {
                textView2.setText("Alarm is off....!");
                cancelAlarm();
            }
        };



        Button btn1 = (Button)findViewById(R.id.bt_stop);
        btn1.setOnClickListener(listener2);


        Button btn2 = (Button)findViewById(R.id.bt_start);
        btn2.setOnClickListener(listener1);



    }



    public void btn_share_App(View view) {
        btn_Con.Share_App();
    }

    public void btn_MoreApp(View view) {
        btn_Con.Rate_App();
    }





    class MyHandler extends Handler {
        @Override
        public void handleMessage (Message msg){
            Bundle bundle = msg.getData();
            timeHour = bundle.getInt(Constants.HOUR);
            timeMinute = bundle.getInt(Constants.MINUTE);
            textView1.setText(timeHour + ":" + timeMinute);
            setAlarm();
        }
    }







    private void setAlarm(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }






    private void cancelAlarm() {
        if (alarmManager!= null) {
            alarmManager.cancel(pendingIntent);
            AlarmReceiver.ringtone.stop();

        }
    }


}
