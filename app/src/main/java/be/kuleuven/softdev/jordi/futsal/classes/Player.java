package be.kuleuven.softdev.jordi.futsal.classes;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Player implements Parcelable{
    //Implements Parcelable so you can put arrayList in intent

    private String name;
    //playTime in seconds
    private long playTime;


    public Player(String name)
    {
        this.name = name;
        playTime = 0;
    }



    public String getName() {
        return name;
    }

    public void addPlayTime(long addedTime)
    {
        playTime+= addedTime;
    }

    public long getPlayTime(){
        return playTime;
    }


    //Override to String for implementation in Spinner
    //Todo: see if this is still necessary
    @Override
    public String toString() {
        return name;
    }


    //Overrides equals and Hashcode to see which players are the longest on the field.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;

        return player.name.equals(name);
    }

    @Override
    public int hashCode() {

        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        //Uses hashcode to know what the content is
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //Object serialization/flattening.
        dest.writeString(name);
        dest.writeLong(playTime);
    }

    //Helper class that implements ParcelCreator
        protected Player(Parcel in) {
        /*
        * Reconstruct from the parcel
        * */
            name = in.readString();
            playTime = in.readLong();
        }

        public static final Creator<Player> CREATOR = new Creator<Player>() {
        /*
        * This is required to "unmarshal" data stored in a Parcel
        * */
            @Override
            public Player createFromParcel(Parcel in) {
                return new Player(in);
            }

            @Override
            public Player[] newArray(int size) {
                return new Player[size];
            }
        };
}
