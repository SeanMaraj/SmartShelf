package com.fydp.sean.smartshelf.Models;

/**
 * Created by Sean on 2015-07-19.
 */
public class ZoneModel {

    private int number;
    private String name;
    private float percentage;
    private float initialWeight;
    private float currentWeight;

    public ZoneModel(int zoneNumber, String zoneName, float currentWeight, float initialWeight)
    {
        setNumber(zoneNumber);
        setName(zoneName);
        setCurrentWeight(currentWeight);
        setInitialWeight(initialWeight);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
