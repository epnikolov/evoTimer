package evo.evotimer.events;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import evo.evotimer.MainActivity;
import evo.evotimer.utils.Helper;

/**
 * Created by Evo on 7/12/2018.
 */

public class OnSpinnerItemSelectListener implements AdapterView.OnItemSelectedListener {

    private MainActivity mainActivity;
    private Spinner roundsViewSp, cyclesViewSp, cycleDuratViewSp, restDuratCycleSp;
    private TextView totalTimeTv;

    public OnSpinnerItemSelectListener(MainActivity mainActivity, TextView totalTimeTv,
                                       Spinner cyclesViewSp, Spinner cycleDuratViewSp,
                                       Spinner restDuratCycleSp, Spinner roundsViewSp) {
        this.mainActivity = mainActivity;
        this.totalTimeTv = totalTimeTv;
        this.cyclesViewSp = cyclesViewSp;
        this.cycleDuratViewSp = cycleDuratViewSp;
        this.restDuratCycleSp = restDuratCycleSp;
        this.roundsViewSp = roundsViewSp;

        this.setTotalTime(totalTimeTv, cyclesViewSp, cycleDuratViewSp, restDuratCycleSp, roundsViewSp);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        this.setTotalTime(totalTimeTv, cyclesViewSp, cycleDuratViewSp, restDuratCycleSp, roundsViewSp);
    }

    private void setTotalTime (TextView totalTimeTv,
                          Spinner cyclesViewSp, Spinner cycleDuratViewSp,
                          Spinner restDuratCycleSp, Spinner roundsViewSp){
        int cycles = Integer.valueOf(cyclesViewSp.getSelectedItem().toString());
        int cycleDurat = Integer.valueOf(cycleDuratViewSp.getSelectedItem().toString());
        int restDurat = Integer.valueOf(restDuratCycleSp.getSelectedItem().toString());
        int rounds = Integer.valueOf(roundsViewSp.getSelectedItem().toString());

        int totalTimeSec = rounds * ( cycles * cycleDurat + restDurat ) - restDurat;
        mainActivity.setTotalSeconds(totalTimeSec);

        totalTimeTv.setText(Helper.getMinSecStrFromSec(totalTimeSec));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
