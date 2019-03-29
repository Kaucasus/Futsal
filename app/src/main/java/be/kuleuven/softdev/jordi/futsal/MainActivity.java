package be.kuleuven.softdev.jordi.futsal;
//TODO: Make sure notifications follow all guidelines

import android.app.NotificationChannel;
import android.app.NotificationManager;


import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import be.kuleuven.softdev.jordi.futsal.classes.Player;


import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
    }


    public void start(View view) {
        Intent intent = new Intent(this, AddPlayers.class);
        startActivity(intent);

    }

    //Set Channel ID etc for notifications
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void testStart(View view) {
        // TODO: 3/24/19 Delete this for build 
        Intent intent = new Intent(this, ActiveMatch.class);
        ArrayList<Player> playerList= new ArrayList<>();

        playerList.add(new Player("Jordi"));
        playerList.add(new Player("Sandro"));
        playerList.add(new Player("Ruben"));
        playerList.add(new Player("Milo"));
        playerList.add(new Player("Arthur"));
        playerList.add(new Player("Floris"));
        playerList.add(new Player("Robin"));

        intent.putParcelableArrayListExtra("playerList",playerList);
        startActivity(intent);
        Log.d("MyActivity", "testStart: Successfully ended mainactivity");
        finish();
    }

}
