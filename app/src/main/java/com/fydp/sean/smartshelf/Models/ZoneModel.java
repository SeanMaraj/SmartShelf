package com.fydp.sean.smartshelf.Models;

/**
 * Created by Sean on 2015-07-19.
 */
public class ZoneModel {

    private int zoneId;
    private String message;
    private float percentage;
    private float initialWeight;
    private float currentWeight;
    private String date;
    private int activeNotifId;
    private int baseId;
    private String desc;

    public ZoneModel(int zoneId, String zoneName, float currentWeight, float initialWeight, int activeNotifId, int baseId, String desc)
    {
        setZoneId(zoneId);
        setMessage(zoneName);
        setCurrentWeight(currentWeight);
        setInitialWeight(initialWeight);
        setActiveNotifId(activeNotifId);
        this.baseId = baseId;
        this.desc = desc;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public float getInitialWeight() {
        return initialWeight;
    }

    public void setInitialWeight(float initialWeight) {
        this.initialWeight = initialWeight;
    }

    public float getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(float currentWeight) {
        this.currentWeight = currentWeight;
    }

    public float getPercentage() {

        if (initialWeight == 0) { return 0; }

        if (currentWeight >= initialWeight)
        {
            percentage = 100;
        }
        else
        {
            percentage = (currentWeight/initialWeight)*100;
        }

        return percentage;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public int getActiveNotifId()
    {
        return activeNotifId;
    }

    public void setActiveNotifId(int activeNotifId)
    {
        this.activeNotifId = activeNotifId;
    }

    public int getBaseId()
    {
        return baseId;
    }

    public void setBaseId(int baseId)
    {
        this.baseId = baseId;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }
}
