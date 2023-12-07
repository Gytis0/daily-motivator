package com.example.dailymotivator;

import static com.example.dailymotivator.Utils.calculatePercentage;
import static com.example.dailymotivator.Utils.secondsInAmonth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView title;
    TextView motivationTitle;
    TextView motivation;
    TextView percentage;
    TextView dayCounter;


    HashMap<Integer, String> dailyQuotes;
    List<String> quotes = new LinkedList<String>(Arrays.asList("“Stay away from those people who try to disparage your ambitions. Small minds will always do that, but great minds will give you a feeling that you can become great too.” — Mark Twain",
            "\"Success is not final; failure is not fatal: It is the courage to continue that counts.\" — Winston S. Churchill",
            "“There are three ways to ultimate success: The first way is to be kind. The second way is to be kind. The third way is to be kind.” —Mister Rogers",
            "“If you are working on something that you really care about, you don’t have to be pushed. The vision pulls you.” — Steve Jobs",
            "“Either you run the day or the day runs you.” — Jim Rohn",
            "“Some women choose to follow men, and some choose to follow their dreams. If you’re wondering which way to go, remember that your career will never wake up and tell you that it doesn’t love you anymore.” — Lady Gaga",
            "“You've got to get up every morning with determination if you're going to go to bed with satisfaction.” — George Lorimer",
            "“Get a good idea and stay with it. Dog it, and work at it until it’s done right.” —Walt Disney",
            "“Optimism is the faith that leads to achievement. Nothing can be done without hope and confidence.” —Helen Keller",
            "“The elevator to success is out of order. You’ll have to use the stairs, one step at a time.” — Joe Girard",
            "“People often say that motivation doesn’t last. Well, neither does bathing – that’s why we recommend it daily.” — Zig Ziglar",
            "“Coming together is a beginning. Keeping together is progress. Working together is success.” — Henry Ford",
            "“Alone we can do so little, together we can do so much.” — Helen Keller",
            "“Just one small positive thought in the morning can change your whole day.” — Dalai Lama",
            "“Don't let someone else's opinion of you become your reality” — Les Brown",
            "“He that can have patience can have what he will.” ―Benjamin Franklin",
            "“Life is like riding a bicycle. To keep your balance you must keep moving.” —Albert Einstein",
            "“If you can't yet do great things, do small things in a great way.” ―Napoleon Hill",
            "“If you really want to do something, you'll find a way. If you don't, you'll find an excuse.” ―Jim Rohn",
            "“You cannot always control what goes on outside. But you can always control what goes on inside.” — Wayne Dyer",
            "“The greater the difficulty, the more the glory in surmounting it.” ―Epicurus"));

    List<String> oldQuotes = new LinkedList<String>(Arrays.asList("“Stay away from those people who try to disparage your ambitions. Small minds will always do that, but great minds will give you a feeling that you can become great too.” — Mark Twain",
            "\"Success is not final; failure is not fatal: It is the courage to continue that counts.\" — Winston S. Churchill",
            "“There are three ways to ultimate success: The first way is to be kind. The second way is to be kind. The third way is to be kind.” —Mister Rogers",
            "“If you are working on something that you really care about, you don’t have to be pushed. The vision pulls you.” — Steve Jobs",
            "“Either you run the day or the day runs you.” — Jim Rohn",
            "“Some women choose to follow men, and some choose to follow their dreams. If you’re wondering which way to go, remember that your career will never wake up and tell you that it doesn’t love you anymore.” — Lady Gaga",
            "“You've got to get up every morning with determination if you're going to go to bed with satisfaction.” — George Lorimer",
            "“Get a good idea and stay with it. Dog it, and work at it until it’s done right.” —Walt Disney",
            "“Optimism is the faith that leads to achievement. Nothing can be done without hope and confidence.” —Helen Keller",
            "“The elevator to success is out of order. You’ll have to use the stairs, one step at a time.” — Joe Girard",
            "“People often say that motivation doesn’t last. Well, neither does bathing – that’s why we recommend it daily.” — Zig Ziglar",
            "“People say nothing is impossible, but I do nothing every day.” — Winnie the Pooh",
            "“Coming together is a beginning. Keeping together is progress. Working together is success.” — Henry Ford",
            "“Alone we can do so little, together we can do so much.” — Helen Keller",
            "“Just one small positive thought in the morning can change your whole day.” — Dalai Lama",
            "“Don't let someone else's opinion of you become your reality” — Les Brown",
            "“Do the best you can. No one can do more than that.” ―John Wooden",
            "“Be miserable. Or motivate yourself. Whatever has to be done, it's always your choice.” – Wayne Dyer",
            "“True freedom is impossible without a mind made free by discipline.” ―Mortimer J. Adler",
            "“He that can have patience can have what he will.” ―Benjamin Franklin",
            "“The only one who can tell you “you can’t win” is you and you don’t have to listen.” —Jessica Ennis",
            "“Life is like riding a bicycle. To keep your balance you must keep moving.” —Albert Einstein",
            "“If you can't yet do great things, do small things in a great way.” ―Napoleon Hill",
            "“If you really want to do something, you'll find a way. If you don't, you'll find an excuse.” ―Jim Rohn",
            "“You cannot always control what goes on outside. But you can always control what goes on inside.” — Wayne Dyer",
            "“The greater the difficulty, the more the glory in surmounting it.” ―Epicurus"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.textView_Title);
        motivationTitle = findViewById(R.id.textView_motivationTitle);
        motivation = findViewById(R.id.textView_motivation);
        percentage = findViewById(R.id.textView_percentage);
        dayCounter = findViewById(R.id.textView_dayCounter);

        dailyQuotes = new HashMap<>();

        createNotificationChannel();

        approveNoRestrictions();
        approvePermissions();

        setRepeatingAlarm();

        Random random = new Random(3);

        for (int i = 10; i <= 30; i++) {
            int index = (random.nextInt(quotes.size()));
            dailyQuotes.put(i, quotes.get(index));
            quotes.remove(index);
        }

        Handler updater = new Handler();
        updater.postDelayed(new Runnable() {
            @Override
            public void run() {
                double percentageValue = calculatePercentage();
                percentage.setText("You've gone through " + percentageValue + "% of the month");

                int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                motivation.setText(dailyQuotes.get(currentDay));
                dayCounter.setText(Integer.toString(currentDay));
                updater.postDelayed(this, 1500);
            }
        }, 0);
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Milestones";
            String description = "For your milestones";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("myChannel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



    void approvePermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {  Manifest.permission.POST_NOTIFICATIONS }, 1);
            }
            else{
                DialogFragment dialog = new NotificationDialog();
                dialog.show(getSupportFragmentManager(), "NOTIFICATION_ASK");
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SCHEDULE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {  Manifest.permission.SCHEDULE_EXACT_ALARM }, 2);
            }
        }
    }

    void approveNoRestrictions(){
        PowerManager powerManager = (PowerManager) getBaseContext().getSystemService(Context.POWER_SERVICE);
        if(!powerManager.isIgnoringBatteryOptimizations(getBaseContext().getPackageName())){
            DialogFragment dialog = new BatterySaverDialog();
            dialog.show(getSupportFragmentManager(), "BATTERY_SAVER_ASK");
        };
    }

    void setRepeatingAlarm(){
        int nextPercentage = (int)calculatePercentage() + 1;
        double nextTime = ((double)(nextPercentage) / 100) * secondsInAmonth;

        Calendar nextAlarm = Calendar.getInstance();
        nextAlarm.set(Calendar.DAY_OF_MONTH, 1);
        nextAlarm.set(Calendar.HOUR_OF_DAY, 0);
        nextAlarm.set(Calendar.MINUTE, 0);
        nextAlarm.set(Calendar.SECOND, 0);
        nextAlarm.add(Calendar.SECOND, (int)nextTime);
        double delay = secondsInAmonth * 0.02 - secondsInAmonth * 0.01;

        AlarmManager alarmManager = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        intent.setAction("milestones.notification");
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Toast.makeText(getBaseContext(), "Next notification: " + nextAlarm.get(Calendar.HOUR_OF_DAY) + ":" + nextAlarm.get(Calendar.MINUTE), Toast.LENGTH_LONG).show();
        Toast.makeText(getBaseContext(), "Notification interval: " + (delay / 60 / 60) + " hours", Toast.LENGTH_LONG).show();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, nextAlarm.getTimeInMillis(), (long)delay * 1000, alarmIntent);

        //Calendar currentTime = Calendar.getInstance();
        //currentTime.add(Calendar.SECOND, 5);

        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,currentTime.getTimeInMillis(), (long)1, alarmIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Notification Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Notification Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Notification schedule permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Notification schedule permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}