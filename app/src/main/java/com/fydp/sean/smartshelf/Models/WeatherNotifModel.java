package com.fydp.sean.smartshelf.Models;

/**
 * Created by Sean on 2016-03-16.
 */
public class WeatherNotifModel
{
    private int notifId;
    private String weatherType;
    private String operator;
    private String value;

    public WeatherNotifModel(int notifId, String weatherType, String operator, String value)
    {
        this.notifId = notifId;
        this.weatherType = weatherType;
        this.operator = operator;
        this.value = value;
    }

    public String getWeatherType()
    {
        return weatherType;
    }

    public void setWeatherType(String weatherType)
    {
        this.weatherType = weatherType;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public int getNotifId()
    {
        return notifId;
    }

    public void setNotifId(int notifId)
    {
        this.notifId = notifId;
    }
}
