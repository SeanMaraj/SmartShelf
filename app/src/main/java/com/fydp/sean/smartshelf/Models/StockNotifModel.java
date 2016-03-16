package com.fydp.sean.smartshelf.Models;

/**
 * Created by Sean on 2016-03-10.
 */
public class StockNotifModel
{

    private int notifId;
    private float weight;
    private float threshhold;

    public StockNotifModel(int notifId, float threshhold, float weight)
    {
        this.notifId = notifId;
        this.threshhold = threshhold;
        this.weight = weight;
    }

    public int getNotifId()
    {
        return notifId;
    }

    public void setNotifId(int notifId)
    {
        this.notifId = notifId;
    }

    public float getWeight()
    {
        return weight;
    }

    public void setWeight(float weight)
    {
        this.weight = weight;
    }

    public float getThreshhold()
    {
        return threshhold;
    }

    public void setThreshhold(float threshhold)
    {
        this.threshhold = threshhold;
    }
}
