package org.campusdual;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Race {
    private String name;
    private int durationMinutes;
    private List<Garage> garages;

    public Race(@JsonProperty("nombre") String name,
                @JsonProperty("duracionMinutos") int durationMinutes, List<Car> participants) {
        this.name = name;
        this.durationMinutes = durationMinutes;
        this.garages = new ArrayList<>();
    }

    public void addGarage(Garage garage) {
        garages.add(garage);
    }

    public String getName() {
        return name;
    }

    public void startRace() {
        for (Garage garage : garages) {
            List<Car> cars = garage.getCars();
            for (Car car : cars) {
                car.accelerate(durationMinutes);
            }
        }
    }

    public List<Car> getPodium() {
        List<Car> podium = new ArrayList<>();
        for (Garage garage : garages) {
            List<Car> cars = garage.getCars();
            podium.addAll(cars);
        }

        podium.sort(Comparator.comparingInt(Car::getDistanceTraveled).reversed());

        return podium.subList(0, Math.min(podium.size(), 3));
    }
}