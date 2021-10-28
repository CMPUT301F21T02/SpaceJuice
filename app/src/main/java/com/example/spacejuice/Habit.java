package com.example.spacejuice;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class Habit {
    private static int TITLE_LENGTH = 20;
    private static int REASON_LENGTH = 30;
    private Indicator indicator;
    private String title;
    private String reason;
    private Date startDate;
    public Schedule schedule = new Schedule();

    public Habit(String title, String reason, Date date, Schedule schedule) {
        setTitle(title);
        setReason(reason);
        setStartDate(date);
        this.indicator = new Indicator();
        this.schedule = schedule;

    }

    public Habit(String title, String reason, int iLevel) {
        setTitle(title);
        setReason(reason);
        setStartDate(new Date());
        this.indicator = new Indicator();
        if (iLevel != -1) {
            indicator.setLevel(iLevel);
        }
    }

    public void setTitle(String s) {
        int l = s.length();
        if (l > TITLE_LENGTH) { l = TITLE_LENGTH; }
        this.title = s.substring(0,l);
        Log.d("debugInfo", "title set to " + this.title);
    }

    public void setReason(String s) {
        int l = s.length();
        if (l > REASON_LENGTH) { l = REASON_LENGTH; }
        this.reason = s.substring(0,l);
        Log.d("debugInfo", "reason set to " + this.reason);
    }

    public void setIndicator(int iLevel) {

        this.indicator.setLevel(iLevel);
    }

    public void setStartDate(Date d) {
        this.startDate = d;
    }

    public String getTitle() {
        return this.title;
    }

    public String getReason() {
        return this.reason;
    }

    public Indicator getIndicator() { return this.indicator; }

    public Date getStartDate() {
        return this.startDate;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public Boolean isToday() {
        // returns true if the Habit is scheduled for the current day of the week

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                if (schedule.Sun()) {
                    return true;
                } else { return false; }
            case Calendar.MONDAY:
                if (schedule.Mon()) {
                    return true;
                } else { return false; }
            case Calendar.TUESDAY:
                if (schedule.Tue()) {
                    return true;
                } else { return false; }
            case Calendar.WEDNESDAY:
                if (schedule.Wed()) {
                    return true;
                } else { return false; }
            case Calendar.THURSDAY:
                if (schedule.Thu()) {
                    return true;
                } else { return false; }
            case Calendar.FRIDAY:
                if (schedule.Fri()) {
                    return true;
                } else { return false; }
            case Calendar.SATURDAY:
                if (schedule.Sat()) {
                    return true;
                } else { return false; }
        }
        return false;
    }
}
