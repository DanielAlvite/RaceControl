package org.campusdual;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Random;

public class Car {
    private String brand;
    private String model;
    private String garageSticker;
    private int distanceTraveled;
    private int points;

    public Car(String brand, String model, String garageSticker) {
        this.brand = brand;
        this.model = model;
        this.garageSticker = garageSticker;
        this.distanceTraveled = 0;
        this.points = 0;
    }

    public String getGarageSticker() {
        return garageSticker;
    }

    public int getDistanceTraveled() {
        return distanceTraveled;
    }

    public int getPoints() {
        return points;
    }

    public void accelerate(int durationMinutes) {
        Random random = new Random();
        for (int minute = 0; minute < durationMinutes; minute++) {
            int acceleration = random.nextInt(3) - 1; // -1, 0, 1
            int newSpeed = Math.max(0, getDistanceTraveled() + acceleration * 10);
            setDistance(newSpeed);
        }
    }

    public void setDistance(int distance) {
        this.distanceTraveled = distance;
    }

    public void increasePoints(int amount) {
        this.points += amount;
    }
}