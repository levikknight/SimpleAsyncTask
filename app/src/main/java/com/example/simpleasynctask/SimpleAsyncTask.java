package com.example.simpleasynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask <Void, Integer, String> {


    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> mProgressBar;

    SimpleAsyncTask(ProgressBar pv, TextView tv) {
        mTextView = new WeakReference<>(tv);
        mProgressBar = new WeakReference<>(pv);
    }

    @Override
    protected String doInBackground(Void... voids) {

        Random r = new Random();
        int n = r.nextInt(11);

        int sleepTotal = n * 300;
        int sleepSegments = 20;
        int sleepNumber = sleepTotal / sleepSegments;

        int sleepCount = 0;
        int send = 0;
        publishProgress(0);

        try {
            while (sleepCount < sleepSegments) {
                Thread.sleep(sleepNumber);
                sleepCount += 1;
                send = send + (100 / sleepSegments);
                publishProgress(send);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        publishProgress(100);
        return "Awake at last after sleeping for " + sleepTotal + " milliseconds!";
    }

    protected void onProgressUpdate(Integer... values) {
        mProgressBar.get().setProgress(values[0]);
    }

    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }
}
