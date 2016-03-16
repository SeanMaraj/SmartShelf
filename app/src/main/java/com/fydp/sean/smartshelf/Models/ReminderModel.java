package com.fydp.sean.smartshelf.Models;

/**
 * Created by seanm on 2016-02-03.
 */
public class ReminderModel
{
    private int id;
    private int notifId;
    private int zoneId;
    private int baseId;
    private String date;
    private String time;
    private String desc;
    private int isActive;

    // Constructor
    public ReminderModel(int id, int notifId, int zoneId, int baseId, String date, String time, String desc, int isActive)
    {
        this.id = id;
        this.notifId = notifId;
        this.zoneId = zoneId;
        this.baseId = baseId;
        this.date = date;
        this.time = time;
        this.desc = desc;
        this.isActive = isActive;
    }



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


    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public int getIsActive()
    {
        return isActive;
    }

    public void setIsActive(int isActive)
    {
        this.isActive = isActive;
    }
}
