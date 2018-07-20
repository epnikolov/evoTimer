package evo.evotimer.events;

import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import evo.evotimer.MainActivity;
import evo.evotimer.R;
import evo.evotimer.utils.Helper;
import evo.evotimer.utils.WorkoutEventType;

/**
 * Created by Evo on 7/12/2018.
 */

public class OnStartStopClickListener implements View.OnClickListener {

    private MainActivity mainActivity;
    private boolean started = false;
    private volatile int remainingSeconds;
    private TextView timeTextView;
    private Timer timer;
    private Spinner cyclesViewSp;
    private Spinner cycleDuratViewSp;
    private Spinner restDuratCycleSp;
    private MediaPlayer mediaPlayerStartCycle;
    private MediaPlayer mediaPlayerRest;
    private MediaPlayer mediaPlayerEnd;

    public OnStartStopClickListener(MainActivity mainActivity, TextView timeTextView,
                                    Spinner cyclesViewSp, Spinner cycleDuratViewSp,
                                    Spinner restDuratCycleSp) {
        this.mainActivity = mainActivity;
        this.timeTextView = timeTextView;
        this.cyclesViewSp = cyclesViewSp;
        this.cycleDuratViewSp = cycleDuratViewSp;
        this.restDuratCycleSp = restDuratCycleSp;
        this.mediaPlayerStartCycle = MediaPlayer.create(mainActivity.getApplicationContext(), R.raw.go);
        this.mediaPlayerRest = MediaPlayer.create(mainActivity.getApplicationContext(), R.raw.rest);
        this.mediaPlayerEnd = MediaPlayer.create(mainActivity.getApplicationContext(), R.raw.rest);
    }

    @Override
    public void onClick(View view) {
        Button startB = view.findViewById(R.id.startStopButton);
        remainingSeconds = mainActivity.getTotalSeconds();
        final int cycles = Integer.valueOf(cyclesViewSp.getSelectedItem().toString());
        final int cycleDurat = Integer.valueOf(cycleDuratViewSp.getSelectedItem().toString());
        final int restDurat = Integer.valueOf(restDuratCycleSp.getSelectedItem().toString());

        if(started){
            started = false;
            startB.setText("Start");
            startB.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.colorPrimaryDark));
            timer.cancel();
            timeTextView.setText(Helper.getMinSecStrFromSec(remainingSeconds));

        } else {
            started = true;
            startB.setText("Stop");
            startB.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.customRed));
            CheckBox cbNotifications = mainActivity.findViewById(R.id.notificationsCb);
            final boolean isNotifOn = cbNotifications.isChecked();
            timer = new Timer("countdown", false);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    remainingSeconds--;
                    WorkoutEventType eventType = Helper.getWorkoutEvent(remainingSeconds, cycles, cycleDurat, restDurat);

                    handleEvent(eventType, isNotifOn);

                }
            }, 0, 1000);
        }
    }

    private void handleEvent(WorkoutEventType eventType, boolean isNotifOn){
        if(WorkoutEventType.END.equals(eventType)){
            timer.cancel();
            started = false;
            remainingSeconds = mainActivity.getTotalSeconds();
            mediaPlayerEnd.start();

            mainActivity.runOnUiThread(() -> {
                Button bStartStop = mainActivity.findViewById(R.id.startStopButton);
                bStartStop.setText("Start");
                bStartStop.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.colorPrimaryDark));
                timeTextView.setText("DONE!");
                if(isNotifOn) {
                    mainActivity.sendNotification("Completed!", "End.", 003);
                }
            });

            return;
        }

        if(WorkoutEventType.CYCLE_STARTING.equals(eventType)){
            mainActivity.runOnUiThread(() -> {
                timeTextView.setTextColor(ContextCompat.getColor(mainActivity, R.color.colorPrimaryDark));
                if(isNotifOn) {
                    mainActivity.sendNotification("GO!", "GO!", 001);
                }
            });

        } else if(WorkoutEventType.CYCLE_PREPARE.equals(eventType)){
            mediaPlayerStartCycle.start();

        } else if(WorkoutEventType.REST_STARTING.equals(eventType)){
            mediaPlayerRest.start();

            mainActivity.runOnUiThread(() -> {
                timeTextView.setTextColor(ContextCompat.getColor(mainActivity, R.color.customCrey));
                if(isNotifOn) {
                    mainActivity.sendNotification("Rest...", "Rest...", 002);
                }
            });
        }

        mainActivity.runOnUiThread(() -> {
            timeTextView.setText(Helper.getMinSecStrFromSec(remainingSeconds));
        });
    }
}