package br.com.gransistemas.taurus.helpers;

import java.util.Calendar;
import java.util.Date;

public class DateBuilder {
    private Calendar calendar;

    public DateBuilder() {
        this.calendar = Calendar.getInstance();
    }

    private DateBuilder setYear(int year){
        if(year > 0 && year < 100){
            year += 2000;
        }

        calendar.set(Calendar.YEAR, year);
        return this;
    }

    private DateBuilder setMonth(int month){
        calendar.set(Calendar.MONTH, month - 1);
        return this;
    }

    private DateBuilder setDay(int day){
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return this;
    }

    private DateBuilder setHour(int hour){
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        return this;
    }

    private DateBuilder setMinute(int minute){
        calendar.set(Calendar.MINUTE, minute);
        return this;
    }

    private DateBuilder setSecond(int second){
        calendar.set(Calendar.SECOND, second);
        return this;
    }

    public DateBuilder setDate(int year, int month, int day){
        return setYear(year).setMonth(month).setDay(day);
    }

    public DateBuilder setTime(int hour, int minute, int second){
        return setHour(hour).setMinute(minute).setSecond(second);
    }

    public Date getDate(){
        return calendar.getTime();
    }
}
