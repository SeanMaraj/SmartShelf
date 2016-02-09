package com.fydp.sean.smartshelf.Models;

/**
 * Created by seanm on 2016-02-03.
 */
public class EventModel
{
    private int id;
    private int notifId;
    private int zoneId;
    private int baseId;
    private String date;

    // Constructor
    public EventModel(int id, int notifId, int zoneId, int baseId, String date, String time, boolean repeatWeekly, boolean reapeatMonthly, boolean reapeatDaily)
    {
        this.id = id;
        this.notifId = notifId;
        this.zoneId = zoneId;
        this.baseId = baseId;
        this.date = date;
        this.time = time;
        this.repeatWeekly = repeatWeekly;
        this.reapeatMonthly = reapeatMonthly;
        this.reapeatDaily = reapeatDaily;
    }

    private String time;
    private boolean repeatWeekly;
    private boolean reapeatMonthly;
    private boolean reapeatDaily;


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getNotifId()
    {
        return notifId;
    }

    public void setNotifId(int notifId)
    {
        this.notifId = notifId;
    }

    public int getZoneId()
    {
        return zoneId;
    }

    public void setZoneId(int zoneId)
    {
        this.zoneId = zoneId;
    }

    public int getBaseId()
    {
        return baseId;
    }

    public void setBaseId(int baseId)
    {
        this.baseId = baseId;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public boolean isRepeatWeekly()
    {
        return repeatWeekly;
    }

    public void setRepeatWeekly(boolean repeatWeekly)
    {
        this.repeatWeekly = repeatWeekly;
    }

    public boolean isReapeatMonthly()
    {
        return reapeatMonthly;
    }

    public void setReapeatMonthly(boolean reapeatMonthly)
    {
        this.reapeatMonthly = reapeatMonthly;
    }

    public boolean isReapeatDaily()
    {
        return reapeatDaily;
    }

    public void setReapeatDaily(boolean reapeatDaily)
    {
        this.reapeatDaily = reapeatDaily;
    }
}
