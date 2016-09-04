package com.example.emanuel.stopwatch;

import android.os.AsyncTask;
import android.widget.TextView;

public class TimeUpdater extends AsyncTask<Void, String, Void> {
    private TextView timer;
    private String minutes;
    private String seconds;
    private String milliseconds;

    public TimeUpdater(TextView timer){
        this.timer= timer;
    }

    @Override
    protected Void doInBackground(Void... params){
        long startTime = System.nanoTime();
        while(!(this.isCancelled())){
            long timeDiff = (System.nanoTime() - startTime);

            minutes = String.format("%2.1s", Double.toString(convertToMinutes(timeDiff))).replace(" ", "0");

            seconds = Long.toString(convertToSeconds(timeDiff));

            milliseconds = Long.toString(convertToMilliseconds(timeDiff));

            this.publishProgress(minutes + ":" + formatSeconds(seconds) + ":" + formatMilliseconds(milliseconds));
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);
    }

    @Override
    protected void onProgressUpdate(String... values){
        super.onProgressUpdate(values);
        timer.setText(values[0]);
    }

    private long convertToSeconds(long timeDiff){
        return timeDiff / 1000000000;
    }

    private long convertToMilliseconds(long time){
        return time / 1000000;
    }

    private double convertToMinutes(long timeDiff){
        return timeDiff / 6e10;
    }

    // limits the number of digits in milliseconds to 3
    private String formatMilliseconds(String milliseconds){
        if(milliseconds.length() > 3) {
            return format(milliseconds.substring(milliseconds.length() - 3), 2);
        }else{
            return format(milliseconds, 2);
        }
    }

    private String formatSeconds(String seconds){
        if(Integer.parseInt(seconds) >= 60){
            Integer result = Integer.parseInt(seconds) - Integer.parseInt(minutes) * 60;
            return format(result.toString(), 2);
        }else{
            return format(seconds, 2);
        }
    }

    private String format(String number, int numOfDigits){
        return String.format("%" + numOfDigits + "s", number).replace(" ", "0");
    }
}