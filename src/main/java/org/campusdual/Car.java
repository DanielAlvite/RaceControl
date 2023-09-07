package org.campusdual;

public class Car {
    private String brand;
    private String model;

    private double maxSpeed;

    public Car(String brand,String model,double maxSpeed){
        this.brand = brand;
        this.model = model;
        this.maxSpeed = maxSpeed;
    }
    public void accelerate (double acceleration) {

    }

    public void brake(double deceleration){

    }

    public String getBrand(){
        return brand;
    }
    public String getModel() {
        return model;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }
}
