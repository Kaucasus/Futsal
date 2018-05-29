package be.kuleuven.softdev.jordi.futsal.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {
    private int home;
    private int out;
    private long timestamp;

    public Score(int home, int out, long timestamp) {
        this.home = home;
        this.out = out;
        this.timestamp = timestamp;
    }

    //Getters and setters



    public int getHome() {
        return home;
    }

    public int getOut() {
        return out;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;

    }

    //Public methods

    public void incrementOut()
    {
        out++;
    }

    public void incrementHome(){
        home++;
    }

    public String scoreBoardString()
    {
        StringBuilder string = new StringBuilder();
        string.append(home);
        string.append("-");
        string.append(out);

        return string.toString();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(home);
        dest.writeInt(out);
        dest.writeLong(timestamp);
    }

    //Parcelable
    protected Score(Parcel in) {
        home = in.readInt();
        out = in.readInt();
        timestamp = in.readLong();
    }

    public static final Creator<Score> CREATOR = new Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };
}
