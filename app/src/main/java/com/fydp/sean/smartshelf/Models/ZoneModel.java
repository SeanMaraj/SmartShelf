package com.fydp.sean.smartshelf.Models;

/**
 * Created by Sean on 2015-07-19.
 */
public class ZoneModel {

    private int zoneNumber;
    private String zoneName;
    private int percentage;
    private int initialWeight;

    public ZoneModel(int zoneNumber, String zoneName, int percentage, int initialWeight)
    {
        setZoneNumber(zoneNumber);
        setZoneName(zoneName);
        setPercentage(percentage);
        setInitialWeight(initialWeight);
    }

    public int getZoneNumber() {
        return zoneNumber;
    }

    public void setZoneNumber(int zoneNumber) {
        this.zoneNumber = zoneNumber;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getInitialWeight() {
        return initialWeight;
    }

    public void setInitialWeight(int initialWeight) {
        this.initialWeight = initialWeight;
    }
}
