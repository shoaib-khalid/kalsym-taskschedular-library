package com.kalsym.taskSchedular;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Zeeshan Ali
 */
public class TaskSchedular implements Runnable {

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Runnable task;
    private String[] runTimes;
    boolean isrunning = false;
    private int successive_execution_period;
    private int interval;

    /**
     *
     * @param times the are multiple time in 24 hour format in a string array.
     * e.g {"00:45","12:56","23:34"}
     * @param period successive execution period. depends upon frequency of task
     * in TimeSchedular
     * @param Task the Runnable task to run.
     */
    public void setRunnableTask(String[] times, int period, Runnable Task) {
        task = Task;
        runTimes = times;
        successive_execution_period = period;
    }

    @Override
    public void run() {
        //LogProperties.WriteLog("[TaskScheduler] Called");
        int runTimesLength = runTimes.length;
        try {
            //loop through all daily_schedules
            for (int i = 0; i < runTimesLength; i++) {
                Calendar calenderInstance = Calendar.getInstance();
                int daily_schedule_hour = Integer.parseInt(runTimes[i].substring(0, 2));
                int daily_schedule_mint = Integer.parseInt(runTimes[i].substring(3, 5));

                // if hours are equal
                if (daily_schedule_hour == calenderInstance.get(Calendar.HOUR_OF_DAY)) {

                    // if minutes difference is less than task_schedular_time schedule a task
                    int mints_difference = (daily_schedule_mint - calenderInstance.get(Calendar.MINUTE));
                    if (mints_difference <= successive_execution_period && mints_difference >= -2) {
                        if (!isrunning) {
                            Future<?> future = scheduler.schedule(task, mints_difference, TimeUnit.SECONDS);
                            isrunning = true;
                            future.get();// exectue task untill it is completed.
                            //LogProperties.WriteLog("Task is Starting in " + mints_difference + " minutes");
                            if (future.isDone()) {
                                //LogProperties.WriteLog("Process is completed");
                                isrunning = false;
                            }
                        } else {
                            //LogProperties.WriteLog("Previous thread is still Running");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            //LogProperties.WriteLog("[TaskSchedular]" + ex);
        }

    }
}
