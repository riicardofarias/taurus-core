package br.com.gransistemas.taurus.helpers;

import br.com.gransistemas.taurus.model.Coordinate;

import java.util.List;

public class GeoPoint {
    public boolean isInPolygon(List<Coordinate> polygon, Coordinate point) {
        boolean result = false;
        int j = polygon.size() - 1;

        for (int i = 0; i < polygon.size(); i++) {
            if (polygon.get(i).getLatitude() < point.getLatitude() && polygon.get(j).getLatitude() >= point.getLatitude() || polygon.get(j).getLatitude() < point.getLatitude() && polygon.get(i).getLatitude() >= point.getLatitude()) {
                if (polygon.get(i).getLongitude() + (point.getLatitude() - polygon.get(i).getLatitude()) / (polygon.get(j).getLatitude() - polygon.get(i).getLatitude()) * (polygon.get(j).getLongitude() - polygon.get(i).getLongitude()) < point.getLongitude()) {
                    result = !result;
                }
            }

            j = i;
        }

        return result;
    }

    public double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
