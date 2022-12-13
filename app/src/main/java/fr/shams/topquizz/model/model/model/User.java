package fr.shams.topquizz.model.model.model;

public class User {

    private String mFirstName;
    private int mLastScore;

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String FirstName) {
        this.mFirstName = FirstName;
    }

    public int getLastScore() {
        return mLastScore;
    }

    public void setLastScore(int lastScore) {
        mLastScore = lastScore;
    }
}
