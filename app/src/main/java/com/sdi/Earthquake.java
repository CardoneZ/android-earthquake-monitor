package com.sdi;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "earthquakes")
public class Earthquake {

    @PrimaryKey
    @NonNull
    private String id;
    private String place;
    private double magnitude;
    private long time;
    private double longitude;
    private double latitude;

    public Earthquake(String id, String place, double magnitude, long time, double longitude, double latitude) {
        this.id = id;
        this.place = place;
        this.magnitude = magnitude;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double loingitude) {
        this.longitude = loingitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Earthquake that = (Earthquake) o;
        return Double.compare(magnitude, that.magnitude) == 0 && time == that.time && Double.compare(longitude, that.longitude) == 0 && Double.compare(latitude, that.latitude) == 0 && Objects.equals(id, that.id) && Objects.equals(place, that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, place, magnitude, time, longitude, latitude);
    }
}
