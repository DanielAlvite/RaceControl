package org.campusdual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tournament {
    private String name;
    private int numRaces;
    private List<Garage> garages;
    private List<Race> races;
    private List<Car> participants;

    public Tournament(String name, int numRaces) {
        this.name = name;
        this.numRaces = numRaces;
        this.garages = new ArrayList<>();
        this.races = new ArrayList<>();
    }

    public void addGarages(List<Garage> garages) {
        this.garages.addAll(garages);
    }

    public void addRaces(List<Race> races) {
        this.races.addAll(races);
    }

    public void simulateTournament() {
        for (int i = 0; i < numRaces; i++) {
            Race race = new Race("Race " + (i + 1), 180, participants);
            addRandomGarageToRace(race);
            race.startRace();
        }
    }

    public List<Car> getTournamentResults() {
        List<Car> tournamentResults = new ArrayList<>();
        for (Garage garage : garages) {
            tournamentResults.addAll(garage.getCars());
        }
        tournamentResults.sort((car1, car2) -> car2.getPoints() - car1.getPoints());
        return tournamentResults;
    }

    public String getName() {
        return name;
    }

    private void addRandomGarageToRace(Race race) {
        if (!garages.isEmpty()) {
            Random random = new Random();
            Garage randomGarage = garages.get(random.nextInt(garages.size()));
            race.addGarage(randomGarage);
        }
    }
}
