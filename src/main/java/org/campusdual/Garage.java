package org.campusdual;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Garage {
    private String name;
    private List<Car> cars;

    public Garage(String name) {
        this.name = name;
        this.cars = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void removeCar(Car car) {
        cars.remove(car);
    }

    public Car getRandomCar() {
        Random random = new Random();
        if (!cars.isEmpty()) {
            int randomIndex = random.nextInt(cars.size());
            return cars.get(randomIndex);
        }
        return null;
    }
}


