package com.kalsym.taskSchedular;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Zeeshan Ali
 */
public class TimeSchedularCdr {

    Runnable Task;
    int taskFrequency;
    String runTimes;
    int currentMint = 0;
    int initialDelay = 0;
    int successiveExecutionPeriod = 0;
    int interval = 0;

    /**
     * Parameterized Constructor
     *
     * @param frequency the number of times time scheduler runs in an hour. In
     * range of 2-6
     * @param times the are multiple time in 24 hour format in a string array.
     * e.g {"00:45","12:56","23:34"}
     * @param interval
     * @param TaskToRun Runnable task to run
     */
    public TimeSchedularCdr(int frequency, String times, int interval,
            Runnable TaskToRun) {
        this.Task = TaskToRun;
        taskFrequency = frequency;
        runTimes = times;
        this.interval = interval;
    }

    /**
     * Parameterized Constructor
     *
     * @param times the are multiple time in 24 hour format in a string array.
     * e.g {"00:45","12:56","23:34"}
     * @param TaskToRun Runnable task to run
     */
    public TimeSchedularCdr(String times, Runnable TaskToRun) {
        //default frequency = 2; mean runs 2 times in a hour
        this.Task = TaskToRun;
        runTimes = times;
        taskFrequency = 2;
    }

    public void start() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        try {
            setDelayAndPeriod();
            TaskSchedularCdr taskSchedular = new TaskSchedularCdr();
            //LogProperties.WriteLog("[TimeSchedular] initail Delay:" + initialDelay);
            taskSchedular.setRunnableTask(runTimes, successiveExecutionPeriod, Task, interval);
            scheduler.scheduleAtFixedRate(taskSchedular, initialDelay, interval, TimeUnit.MINUTES);
        } catch (Exception e) {
            //LogProperties.WriteLog("[TimeSchedular]" + e);
        }
    }

    public Date loadDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
        String dateInString = "May 11, 2014 22:40:56 PM";

        try {
            Date date = formatter.parse(dateInString);
            System.out.println(date);
            System.out.println(formatter.format(date));
            return date;
        } catch (ParseException e) {
            //LogProperties.WriteLog("[TimeSchedular]" + e);
        }
        return null;
    }

    private void setDelayAndPeriod() {
        Calendar calInstance = Calendar.getInstance();
        currentMint = calInstance.get(Calendar.MINUTE);
        // Get Initail Delay with respect to frequency
        switch (taskFrequency) {
            case 1:
                successiveExecutionPeriod = 60;
                if (currentMint == 0) {
                    initialDelay = 0;
                } else {
                    initialDelay = 60 - currentMint;
                }
                break;
            case 2:
                successiveExecutionPeriod = 30;
                if (currentMint == 0) {
                    initialDelay = 0;
                } else if (currentMint <= 30) {
                    initialDelay = 30 - currentMint;
                } else {
                    initialDelay = 60 - currentMint;
                }
                break;
            case 3:
                successiveExecutionPeriod = 20;
                if (currentMint == 0) {
                    initialDelay = 0;
                } else if (currentMint <= 20) {
                    initialDelay = 20 - currentMint;
                } else if (currentMint <= 40) {
                    initialDelay = 40 - currentMint;
                } else {
                    initialDelay = 60 - currentMint;
                }
                break;
            case 4:
                successiveExecutionPeriod = 15;
                if (currentMint == 0) {
                    initialDelay = 0;
                } else if (currentMint <= 15) {
                    initialDelay = 15 - currentMint;
                } else if (currentMint <= 30) {
                    initialDelay = 30 - currentMint;
                } else if (currentMint <= 45) {
                    initialDelay = 45 - currentMint;
                } else {
                    initialDelay = 60 - currentMint;
                }
                break;
            case 5:
                successiveExecutionPeriod = 12;
                if (currentMint == 0) {
                    initialDelay = 0;
                } else if (currentMint <= 12) {
                    initialDelay = 12 - currentMint;
                } else if (currentMint <= 24) {
                    initialDelay = 24 - currentMint;
                } else if (currentMint <= 36) {
                    initialDelay = 36 - currentMint;
                } else if (currentMint <= 48) {
                    initialDelay = 48 - currentMint;
                } else {
                    initialDelay = 60 - currentMint;
                }
                break;
            case 6:
                successiveExecutionPeriod = 10;
                if (currentMint == 0) {
                    initialDelay = 0;
                } else if (currentMint <= 10) {
                    initialDelay = 10 - currentMint;
                } else if (currentMint <= 20) {
                    initialDelay = 20 - currentMint;
                } else if (currentMint <= 30) {
                    initialDelay = 30 - currentMint;
                } else if (currentMint <= 40) {
                    initialDelay = 40 - currentMint;
                } else if (currentMint <= 50) {
                    initialDelay = 50 - currentMint;
                } else {
                    initialDelay = 60 - currentMint;
                }

                break;
            default:
                successiveExecutionPeriod = 30;
                if (currentMint == 0) {
                    initialDelay = 0;
                } else if (currentMint <= 30) {
                    initialDelay = 30 - currentMint;
                } else {
                    initialDelay = 60 - currentMint;
                }
        }
    }
}
