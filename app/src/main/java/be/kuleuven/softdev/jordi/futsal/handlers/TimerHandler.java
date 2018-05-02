package be.kuleuven.softdev.jordi.futsal.handlers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public final class TimerHandler extends Handler{
    private final static int DO_UPDATE_TEXT = 0;
    private final static int DO_THAT = 1;
    public long startTime = System.currentTimeMillis();


    public TimerHandler(Looper looper)
    {
        super(looper);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        switch (msg.what){
            case DO_UPDATE_TEXT:

                updateUITimer(System.currentTimeMillis() - startTime);
                sendEmptyMessageDelayed(TimerHandler.DO_UPDATE_TEXT,1000);
                break;


        }


    }

    public void updateUITimer(long time)
    {
        //get a long that represents the time since the app has been running.
        String timeString = (time / (1000*60)) % 60 + " : " + (time / 1000)%60;

    }

}
