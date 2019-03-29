package be.kuleuven.softdev.jordi.futsal.handlers;
// TODO: 2/12/19 Check if time code is all in seconds


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import be.kuleuven.softdev.jordi.futsal.ActiveMatch;

public final class TimerHandler extends Handler{
    final ActiveMatch am;

    public final static int DO_UPDATE_TIMER = 0;
    public final static int DO_TOGGLE_TIMER = 1;
    private long time;
    private boolean paused;
    private boolean notificationFired;



    public TimerHandler(ActiveMatch ht)
    {
        this.am = ht;
        time=0;
        paused = true;
        notificationFired = false;
    }

    //Overloaded constructor to make the on Saved Instance state possible
    public TimerHandler(ActiveMatch ht,long startTime)
    {
        this.am = ht;
        time=startTime;
        paused = false;
        notificationFired = false;
    }


    public void setNotificationFired(boolean notificationFired) {
        this.notificationFired = notificationFired;
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

                    if(am.checkHalfTime(time))
                    {
                        am.halftime();
                        paused = true;
                    }else if(am.checkFullTime(time))
                    {
                        paused = true;
                        am.endGame();
                    }

                    if(am.checkTimeSubstitution(time) && !notificationFired){
                        Log.d("TimeSubReached","firing notification");
                        notificationFired = true;
                        am.substitutionNotification();
                    }

                    //A bit slower then 1000 ms because the thing above takes some time

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
        am.updateUI(timeString);

    }

    //Getters and setters

    public long getTime()
    {
        return (time);
    }

    public boolean isPaused() {
        return paused;
    }

//    ook als de app aan het sleepen is
//    1 log gewoon elke seconde de tijd in Log.d
//
//
//    daarna als de handler gestart wordt, geef je de activity mee
//    dan op die referentie functies oproepen uit


}
