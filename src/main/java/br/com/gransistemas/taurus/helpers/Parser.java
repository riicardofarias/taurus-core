package br.com.gransistemas.taurus.helpers;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private final Matcher matcher;
    private final AtomicInteger position;

    public Parser(Pattern pattern, String input) {
        this.matcher = pattern.matcher(input);
        this.position = new AtomicInteger(1);
    }

    public boolean matches(){
        return matcher.matches();
    }

    public void skip(){
        position.getAndIncrement();
    }

    public String next(){
        return matcher.group(position.getAndIncrement());
    }

    public Integer nextInt(){
        return Integer.parseInt(next());
    }

    public Double nextDouble(){
        return Double.parseDouble(next());
    }

    public double nextCoordinate(CoordinateFormat format){
        String hemisphere = next();
        double coordinate = 0;

        if(format == CoordinateFormat.DEG_MIN_MIN_HEM){
            coordinate = nextInt();
            coordinate += Double.parseDouble(next() + "." + next()) / 60;
        }

        if(hemisphere != null && (hemisphere.equals("-"))){
            coordinate = -Math.abs(coordinate);
        }

        return coordinate;
    }

    public Date nextDateTime(DateTimeFormat format) {
        int year = 0, month = 0, day = 0;
        int hour = 0, min = 0, sec = 0;

        if (format == DateTimeFormat.DMY_HMS) {
            day = nextInt();
            month = nextInt();
            year = nextInt();
            hour = nextInt();
            min = nextInt();
            sec = nextInt();
        }

        DateBuilder dateBuilder = new DateBuilder();
        dateBuilder.setDate(year, month, day);
        dateBuilder.setTime(hour, min, sec);

        return dateBuilder.getDate();
    }

    public enum CoordinateFormat{
        DEG_MIN_MIN_HEM
    }

    public enum DateTimeFormat{
        DMY_HMS
    }
}
