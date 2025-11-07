package com.example.myapplication8;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build; 
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity {
 Button notify;
 EditText e;
 private final String CHANNEL_ID = "default_channel";
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_main);
 notify = findViewById(R.id.button);
 e = findViewById(R.id.editText);
 createNotificationChannel(); // Required for Android 8.0+
 notify.setOnClickListener(new View.OnClickListener() {
 @Override
 public void onClick(View v) {
 // Intent to start SecondActivity
 Intent intent = new Intent(MainActivity.this, second_activity.class);
 intent.putExtra("message", e.getText().toString()); // Send message to SecondActivity
 PendingIntent pending = PendingIntent.getActivity(
 MainActivity.this,
 0,
 intent,
 PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
 );
 // Build notification
 Notification noti = new Notification.Builder(MainActivity.this, CHANNEL_ID)
 .setContentTitle("New Message")
 .setContentText(e.getText().toString())
 .setSmallIcon(R.mipmap.ic_launcher)
 .setContentIntent(pending)
 .setAutoCancel(true)
 .build();
 // Show notification
 NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
 manager.notify(0, noti);
 }
 });
 }
 // Create notification channel for Android 8.0+ 
private void createNotificationChannel() {
 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
 CharSequence name = "Default Channel";
 String description = "Channel for notifications";
 int importance = NotificationManager.IMPORTANCE_DEFAULT;
 NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
 channel.setDescription(description);
 NotificationManager manager = getSystemService(NotificationManager.class);
 manager.createNotificationChannel(channel);
 }
 }
}
