package be.kuleuven.softdev.jordi.futsal.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Goal implements Parcelable{
    private Player scorer;
    private Player assister;
    private long timestamp;
    private Score score;

    public Goal(Player scorer, Player assister, long timestamp, Score score) {
        this.scorer = scorer;
        this.assister = assister;
        this.timestamp = timestamp;
        this.score = score;
    }



    public Player getScorer() {
        return scorer;
    }

    public Player getAssister() {
        return assister;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Score getScore() {
        return score;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(scorer, flags);
        dest.writeParcelable(assister, flags);
        dest.writeLong(timestamp);
        dest.writeParcelable(score, flags);
    }

    //Parcelable helper class
    protected Goal(Parcel in) {
        scorer = in.readParcelable(Player.class.getClassLoader());
        assister = in.readParcelable(Player.class.getClassLoader());
        timestamp = in.readLong();
        score = in.readParcelable(Score.class.getClassLoader());
    }

    public static final Creator<Goal> CREATOR = new Creator<Goal>() {
        @Override
        public Goal createFromParcel(Parcel in) {
            return new Goal(in);
        }

        @Override
        public Goal[] newArray(int size) {
            return new Goal[size];
        }
    };
}
