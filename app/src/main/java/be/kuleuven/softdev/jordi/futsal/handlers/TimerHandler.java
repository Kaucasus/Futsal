package be.kuleuven.softdev.jordi.futsal.handlers;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import be.kuleuven.softdev.jordi.futsal.HandlerTest;

public final class TimerHandler extends Handler{

    final HandlerTest ht;

    public final static int DO_UPDATE_TIMER = 0;
    public final static int DO_TOGGLE_TIMER = 1;
    private long time;
    private boolean paused;


    public TimerHandler(HandlerTest ht)
    {
        this.ht = ht;
        time=0;
        paused = false;
    }



    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        switch (msg.what){
            case DO_UPDATE_TIMER:
                if(!paused){
                Log.d("TimerExample","going for: "+(time));
                updateUITimer(time);
                time++;
                sendEmptyMessageDelayed(TimerHandler.DO_UPDATE_TIMER,1000);
                }
                break;

            case DO_TOGGLE_TIMER:
                if(paused)
                {
                    paused = false;
                    sendEmptyMessage(DO_UPDATE_TIMER);
                }else {
                    paused = true;
                }
                Log.d("TimerExample", "Toggled Timer");


        }


    }

    private void updateUITimer(long time)
    {
        //get a long that represents the time since the app has been running.
        String timeString = ((time/60) % 60 )+ " : " + (time%60);
        ht.updateUI(timeString);
    }

    public long getTime()
    {
        return (time);
    }

    //1 log gewoon elke seconde de tijd in Log.d
    //todo
    /*
    ook als de app aan het sleepen is


    daarna als de handler gestart wordt, geef je de activity mee
    dan op die referentie functies oproepen uit
     */

}
