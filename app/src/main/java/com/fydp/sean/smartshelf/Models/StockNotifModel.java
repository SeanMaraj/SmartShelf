package com.fydp.sean.smartshelf.Models;

/**
 * Created by Sean on 2016-03-10.
 */
public class StockNotifModel
{

    private int notifId;
    private float initialWeight;
    private float percentage;
    private String operator;

    public StockNotifModel(int notifId, float threshhold, float initialWeight, String operator)
    {
        this.notifId = notifId;
        this.percentage = threshhold;
        this.initialWeight = initialWeight;
        this.operator = operator;
    }

    public int getNotifId()
    {
        return notifId;
    }

    public void setNotifId(int notifId)
    {
        this.notifId = notifId;
    }

    public float getInitialWeight()
    {
        return initialWeight;
    }

    public void setInitalWeight(float weight)
    {
        this.initialWeight = weight;
    }

    public float getPercentage()
    {
        return percentage;
    }

    public void setPercentage(float percentage)
    {
        this.percentage = percentage;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }
}
