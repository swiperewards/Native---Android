package com.winjit.swiperewards.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LevelDetails {

    @SerializedName("userLevel")
    @Expose
    private int userLevel;

    @SerializedName("userXP")
    @Expose
    private int userXP;


    @SerializedName("levelMin")
    @Expose
    private int levelMin;


    @SerializedName("levelMax")
    @Expose
    private int levelMax;


    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getUserXP() {
        return userXP;
    }

    public void setUserXP(int userXP) {
        this.userXP = userXP;
    }

    public int getLevelMin() {
        return levelMin;
    }

    public void setLevelMin(int levelMin) {
        this.levelMin = levelMin;
    }

    public int getLevelMax() {
        return levelMax;
    }

    public void setLevelMax(int levelMax) {
        this.levelMax = levelMax;
    }
}
