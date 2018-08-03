package evo.evotimer.utils;

/**
 * Created by Evo on 7/12/2018.
 */

public class Helper {

    public static int WORK_DELAY = 4; //in seconds
    public static int REST_DELAY = 2; //in seconds

    public static String getMinSecStrFromSec(int sec){
        if(sec <= 60){
            return sec + "sec.";
        }
        int min = sec / 60;
        int remainSec = sec % 60;

        return min + "min. " + remainSec + "sec.";
    }

    public static WorkoutEventType getWorkoutEvent(int remainingSecs, int cycles, int cycleDurat, int restDurat){

        if(remainingSecs < 1){
            return WorkoutEventType.END;
        }

        int cyclesDuration = cycles * cycleDurat;
        if(remainingSecs > WORK_DELAY && remainingSecs <= cyclesDuration + WORK_DELAY && remainingSecs % cycleDurat == WORK_DELAY){
            return WorkoutEventType.CYCLE_PREPARE;
        } else if(remainingSecs <= cyclesDuration && remainingSecs % cycleDurat == 0){
            return WorkoutEventType.CYCLE_STARTING;
        }

        int roundDuration = cyclesDuration + restDurat;
        int remainder = remainingSecs % roundDuration;

        if(remainder > cycleDurat && remainder <= cyclesDuration + WORK_DELAY && remainder % cycleDurat == WORK_DELAY){
            return WorkoutEventType.CYCLE_PREPARE;
        } else if(remainder <= cyclesDuration && remainder > 0 && remainder % cycleDurat == 0){
            return WorkoutEventType.CYCLE_STARTING;
        } else if (remainder == REST_DELAY){
            return WorkoutEventType.REST_STARTING;
        } else if(remainingSecs == cyclesDuration - (cycleDurat / 2) ) {
            return WorkoutEventType.ENCOURAGE;
        } else {
            return WorkoutEventType.NOTHING;
        }
    }
}
