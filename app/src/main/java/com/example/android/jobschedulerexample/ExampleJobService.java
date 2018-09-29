package com.example.android.jobschedulerexample;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

/**
 * Created by Tarek on 29-Sep-18.
 */
public class ExampleJobService extends JobService {

    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;  //its false by default 

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started: ");
        doBackgroundWork(params);

        return true; // to keep the device awake till the service is finished
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.d(TAG, "run: " + i);
                    if (jobCancelled) {
                        return;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "Job finished");
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }
}
