package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class ParkingDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_details);

        TextView parkingLotName_display = findViewById(R.id.parking_lot_name);

        Intent intent = getIntent();
        if (intent != null) {
            String parkingLotName = intent.getStringExtra("parking_lot_name");
            parkingLotName_display.setText(parkingLotName);
        }

        ((TimePicker)findViewById(R.id.parking_duration_picker)).setOnTimeChangedListener((timePicker, hourOfDay, minute) -> {

            EditText licensePlateNumberEditText = findViewById(R.id.license_plate_number);
            String licensePlateNumber = licensePlateNumberEditText.getText().toString();
            TextView parkingExpirationTimeTextView = findViewById(R.id.parking_expiration_time);


            if (!licensePlateNumber.isEmpty()) {
                int parkingDurationHours = hourOfDay;
                int parkingDurationMinutes = minute;

                Calendar expirationTime = Calendar.getInstance();
                expirationTime.set(Calendar.HOUR_OF_DAY, parkingDurationHours);
                expirationTime.set(Calendar.MINUTE, parkingDurationMinutes);
                expirationTime.set(Calendar.SECOND, 0);

                DateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                String formattedExpirationTime = timeFormat.format(expirationTime.getTime());


                parkingExpirationTimeTextView.setText("Parking expires at: " + formattedExpirationTime);

//                Toast.makeText(ParkingDetailsActivity.this, "Parking bought successfully", Toast.LENGTH_SHORT).show();

                createCountDownTimer(expirationTime);
            } else {
                licensePlateNumberEditText.setError("Please enter a valid license plate number");
            }
        });

//        TimePicker parkingDurationPicker = findViewById(R.id.parking_duration_picker);
//        parkingDurationPicker.setIs24HourView(true);
    }

    public CountDownTimer timer;

    private void createCountDownTimer(Calendar expirationTime) {
        TextView countdownTimerTextView = findViewById(R.id.countdown_timer);
        // Get the current time
        final Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeInMillis(System.currentTimeMillis());

        long durationInMillis = expirationTime.getTimeInMillis() - currentTime.getTimeInMillis();
        if(timer != null){
            timer.cancel();
            timer = null;
        }
        if(durationInMillis > 0){
            timer = new CountDownTimer(durationInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                    String timeDiffFormatted = timeFormat.format(new Date(millisUntilFinished));
                    long differenceInSeconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;
                    long differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60;
                    long differenceInHours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                    String countdown = String.format(Locale.ENGLISH, "%02dh:%02dm:%02ds", differenceInHours, differenceInMinutes, differenceInSeconds);
                    countdownTimerTextView.setText(String.format(Locale.getDefault(), "Time left: %s", countdown));

                }

                @Override
                public void onFinish() {
                    countdownTimerTextView.setText("Parking expired");
                    showParkingExpiredNotification();
                    timer = null;
                }
            };
            timer.start();
        }else{
            countdownTimerTextView.setText("Parking expired");
            showParkingExpiredNotification();
        }

    }

    private void showParkingExpiredNotification() {
        String channelId = "parking_alert_channel";
        String channelName = "Parking Alert Channel";
        String title = "Parking Alert";
        String message = "Parking Expired";

        // Get the default notification sound
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Create the media player object
        final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), alarmSound);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ParkingDetailsActivity.this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Change this to your app's icon
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());

        // Play the notification sound
        mediaPlayer.start();

        // Set a listener to stop the media player when the sound is finished
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        });
    }




}








