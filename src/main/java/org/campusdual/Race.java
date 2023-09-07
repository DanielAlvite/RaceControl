package org.campusdual;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Race {
    @JsonProperty("nombre")
    private String name;

    @JsonProperty("duracionMinutos")
    private int durationMinutes;

    private List<Garage> garages;

    public Race(String name, int durationMinutes) {
        this.name = name;
        this.durationMinutes = durationMinutes;
        this.garages = new ArrayList<>();
    }

    public void addGarage(Garage garage) {
        garages.add(garage);
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

        podium.sort((car1, car2) -> Integer.compare(car2.getDistanceTraveled(), car1.getDistanceTraveled()));

        return podium.subList(0, Math.min(podium.size(), 3));
    }
}

