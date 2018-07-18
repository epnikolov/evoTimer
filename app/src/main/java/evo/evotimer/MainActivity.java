package evo.evotimer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import evo.evotimer.events.OnSpinnerItemSelectListener;
import evo.evotimer.events.OnStartStopClickListener;

public class MainActivity extends AppCompatActivity {

    private int totalSeconds;
    private final String NOTIF_CHANNNEL = "evo_timer_notification_001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner cyclesNumSp = findViewById(R.id.cyclesNumSp);
        Spinner cycleDuratSp = findViewById(R.id.cycleDuratSp);
        Spinner restDuratSp = findViewById(R.id.restDuratSp);
        Spinner roundNumSp = findViewById(R.id.roundNumSp);
        TextView totalTimeTextView = findViewById(R.id.totalTimeTextView);

        AdapterView.OnItemSelectedListener listener = new OnSpinnerItemSelectListener(this, totalTimeTextView,
                cyclesNumSp, cycleDuratSp, restDuratSp, roundNumSp);

        cyclesNumSp.setOnItemSelectedListener(listener);
        cycleDuratSp.setOnItemSelectedListener(listener);
        restDuratSp.setOnItemSelectedListener(listener);
        roundNumSp.setOnItemSelectedListener(listener);

        Button startB = findViewById(R.id.startStopButton);
        TextView timeTv = findViewById(R.id.countdownTv);
        startB.setOnClickListener(new OnStartStopClickListener(this, timeTv, cyclesNumSp, cycleDuratSp, restDuratSp));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(int totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

    public void sendNotification(String notifTitle, String notifText, int notifId) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NOTIF_CHANNNEL)
                        .setSmallIcon(R.drawable.not)
                        .setContentTitle(notifTitle)
                        .setContentText(notifText);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIF_CHANNNEL,
                    "Timer notification channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(notifId, mBuilder.build());
    }
}
