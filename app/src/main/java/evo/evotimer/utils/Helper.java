package evo.evotimer.utils;

/**
 * Created by Evo on 7/12/2018.
 */

public class Helper {

    public static int DELAY = 4; //in seconds

    public static String getMinSecStrFromSec(int sec){
        if(sec <= 60){
            return sec + "sec.";
        }
        int min = sec / 60;
        int remainSec = sec % 60;

        return min + "min. " + remainSec + "sec.";
    }

    public static WorkoutEventType getWorkoutEvent(int sec, int cycles, int cycleDurat, int restDurat){

        if(sec < 1){
            return WorkoutEventType.END;
        }

        int cyclesDuration = cycles * cycleDurat;
        if(sec > DELAY && sec <= cyclesDuration + DELAY && sec % cycleDurat == DELAY){
            return WorkoutEventType.CYCLE_PREPARE;
        } else if(sec <= cyclesDuration && sec % cycleDurat == 0){
            return WorkoutEventType.CYCLE_STARTING;
        }

        int roundDur = cyclesDuration + restDurat;
        int remainder = sec % roundDur;

        if(remainder > cycleDurat && remainder <= cyclesDuration + DELAY && remainder % cycleDurat == DELAY ){
            return WorkoutEventType.CYCLE_PREPARE;
        } else if(remainder <= cyclesDuration && remainder > 0 && remainder % cycleDurat == 0){
            return WorkoutEventType.CYCLE_STARTING;
        } else if (remainder == 0){
            return WorkoutEventType.REST_STARTING;
        } else {
            return WorkoutEventType.NOTHING;
        }
    }
}
