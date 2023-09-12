package org.campusdual;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<Garage> garages = new ArrayList<>();
        List<Car> cars = new ArrayList<>();
        List<Race> races = new ArrayList<>();
        List<Tournament> tournaments = new ArrayList<>();
        Map<String, List<Car>> raceParticipants = new HashMap<>();
        Map<String, Race> createdRaces = new HashMap<>();
        loadFromJSON(garages, cars);

        while (true) {
            System.out.println("*********************__NASCAR__***********************");
            System.out.println("*                                                    *");
            System.out.println("*                  MENÚ DE CARRERAS                  *");
            System.out.println("*                                                    *");
            System.out.println("*                  CUSTOM TEAM                       *");
            System.out.println("*                  1. Create Garage                  *");
            System.out.println("*                  2. Create Car                     *");
            System.out.println("*                  3. Delete Garage                  *");
            System.out.println("*                  4. Delete Car                     *");
            System.out.println("*                  5. Show Teams                     *");
            System.out.println("*                                                    *");
            System.out.println("*                  RACING MODE                       *");
            System.out.println("*                  6. Create Race                    *");
            System.out.println("*                  7. Start Race                     *");
            System.out.println("*                                                    *");
            System.out.println("*                  TOURNAMENT MODE                   *");
            System.out.println("*                  8. Create Tournament              *");
            System.out.println("*                  9. Start Tournament               *");
            System.out.println("*                                                    *");
            System.out.println("*                  10.SAVE AND EXIT                  *");
            System.out.println("*                                                    *");
            System.out.println("******************************************************");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter the name of the garage:");
                    String garageName = scanner.nextLine();
                    Garage newGarage = new Garage(garageName);
                    garages.add(newGarage);

                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.writeValue(new File("cars.json"), garages);
                        saveDataToJson(garages, cars);
                        System.out.println("Garage " + garageName + " created.");
                    } catch (IOException e) {
                        System.err.println("Error writing data to JSON: " + e.getMessage());
                    }

                    break;

                case 2:
                    if (!garages.isEmpty()) {
                        System.out.println("Enter the brand of the car:");
                        String brand = scanner.nextLine();
                        System.out.println("Enter the model of the car:");
                        String model = scanner.nextLine();

                        System.out.println("Select a garage to add the car to:");
                        for (int i = 0; i < garages.size(); i++) {
                            System.out.println(i + ". " + garages.get(i).getName());
                        }

                        int selectedGarageIndex = scanner.nextInt();
                        scanner.nextLine();

                        if (selectedGarageIndex >= 0 && selectedGarageIndex < garages.size()) {
                            Garage selectedGarage = garages.get(selectedGarageIndex);

                            String garageSticker = selectedGarage.getName();
                            Car newCar = new Car(brand, model, garageSticker);

                            boolean carExistsInGarage = false;

                            for (Car car : selectedGarage.getCars()) {
                                if (car.getBrand().equals(brand) && car.getModel().equals(model)) {
                                    carExistsInGarage = true;
                                    break;
                                }
                            }

                            if (!carExistsInGarage) {
                                selectedGarage.addCar(newCar);
                                cars.add(newCar);
                                System.out.println("Car " + brand + " " + model + " added to garage " + selectedGarage.getName() + ".");
                            } else {
                                System.out.println("Car already exists in the selected garage.");
                            }
                        } else {
                            System.out.println("Invalid garage selection.");
                        }
                    } else {
                        System.out.println("No garages available. Create a garage first.");
                    }
                    break;

                case 3:
                    if (!garages.isEmpty()) {
                        System.out.println("Enter the name of the garage to remove:");
                        String garageNameToRemove = scanner.nextLine();
                        removeGarage(garages, garageNameToRemove);
                    } else {
                        System.out.println("No garages available to remove.");
                    }
                    break;

                case 4:
                    if (!garages.isEmpty()) {
                        System.out.println("Select a garage to remove a car from:");
                        for (int i = 0; i < garages.size(); i++) {
                            System.out.println(i + ". " + garages.get(i).getName());
                        }

                        int selectedGarageIndex = scanner.nextInt();
                        scanner.nextLine();

                        if (selectedGarageIndex >= 0 && selectedGarageIndex < garages.size()) {
                            Garage selectedGarage = garages.get(selectedGarageIndex);

                            if (!selectedGarage.getCars().isEmpty()) {
                                System.out.println("Select a car to remove:");
                                List<Car> carsInGarage = selectedGarage.getCars();
                                for (int i = 0; i < carsInGarage.size(); i++) {
                                    System.out.println(i + ". " + carsInGarage.get(i).getBrand() + " " + carsInGarage.get(i).getModel());
                                }

                                int selectedCarIndex = scanner.nextInt();
                                scanner.nextLine();

                                if (selectedCarIndex >= 0 && selectedCarIndex < carsInGarage.size()) {
                                    Car selectedCar = carsInGarage.get(selectedCarIndex);
                                    selectedGarage.removeCar(selectedCar);
                                    System.out.println("Car removed from garage.");
                                } else {
                                    System.out.println("Invalid car selection.");
                                }
                            } else {
                                System.out.println("No cars in this garage.");
                            }
                        } else {
                            System.out.println("Invalid garage selection.");
                        }
                    } else {
                        System.out.println("No garages available. Create a garage first.");
                    }
                    break;

                case 5:
                    if (!garages.isEmpty()) {
                        System.out.println("---------List of Garages------------");
                        for (Garage garage : garages) {
                            System.out.println("Garage: " + garage.getName());

                            List<Car> carsInGarage = garage.getCars();
                            if (!carsInGarage.isEmpty()) {
                                System.out.println("Cars in Garage:");
                                for (Car car : carsInGarage) {
                                    System.out.println("- Car: " + car.getBrand() + " " + car.getModel());
                                }
                                System.out.println("-----------------------------------");
                            } else {
                                System.out.println("- No cars in this garage.");
                            }
                        }
                    } else {
                        System.out.println("No garages available. Create a garage first.");
                    }
                    break;

                case 6:
                    if (!garages.isEmpty()) {
                        System.out.println("Enter the name of the race:");
                        String raceName = scanner.nextLine();

                        System.out.println("Select garages to participate:");
                        for (int i = 0; i < garages.size(); i++) {
                            System.out.println(i + ". " + garages.get(i).getName());
                        }

                        List<Integer> selectedGarageIndices = new ArrayList<>();
                        while (selectedGarageIndices.size() < 5) {
                            int selectedGarageIndex = scanner.nextInt();
                            scanner.nextLine();
                            if (selectedGarageIndex >= 0 && selectedGarageIndex < garages.size()) {
                                selectedGarageIndices.add(selectedGarageIndex);
                            } else {
                                System.out.println("Invalid garage selection.");
                            }
                        }

                        List<Car> participants = new ArrayList<>();
                        Random random = new Random();
                        for (int selectedGarageIndex : selectedGarageIndices) {
                            Garage selectedGarage = garages.get(selectedGarageIndex);
                            Car randomCar = selectedGarage.getRandomCar();
                            participants.add(randomCar);
                        }

                        Race newRace = new Race(raceName, 180, participants);
                        races.add(newRace);
                        createdRaces.put(raceName, newRace);

                        System.out.println("Race " + raceName + " created with participants.");
                    } else {
                        System.out.println("Create at least one garage before creating a race.");
                    }
                    break;


                case 7:
                    if (!races.isEmpty()) {
                        System.out.println("Enter the name of the race you want to start:");
                        String raceNameToStart = scanner.nextLine();


                        Race selectedRace = null;
                        for (Race race : races) {
                            if (race.getName().equals(raceNameToStart)) {
                                selectedRace = race;
                                break;
                            }
                        }

                        if (selectedRace != null) {
                            selectedRace.startRace();


                            List<Car> podium = selectedRace.getPodium();
                            System.out.println("Race " + selectedRace.getName() + " has finished.");
                            System.out.println("Podium:");
                            for (int i = 0; i < podium.size(); i++) {
                                Car car = podium.get(i);
                                System.out.println((i + 1) + ". Car: " + car.getBrand() + " " + car.getModel() + " from Garage: " + car.getGarageSticker());
                            }
                        } else {
                            System.out.println("Race not found.");
                        }
                    } else {
                        System.out.println("No races available. Create a race first.");
                    }
                    break;

                case 8:
                    if (createdRaces.size() >= 3) {
                        System.out.println("Enter the name of the tournament:");
                        String tournamentName = scanner.nextLine();

                        Tournament newTournament = new Tournament(tournamentName, 10);
                        List<Race> tournamentRaces = new ArrayList<>();

                        for (int i = 0; i < 3; i++) {
                            System.out.println("Select a race for the tournament:");
                            for (String raceName : createdRaces.keySet()) {
                                System.out.println(raceName);
                            }
                            String selectedRaceName = scanner.nextLine();

                            Race selectedRace = createdRaces.get(selectedRaceName);
                            if (selectedRace != null) {
                                tournamentRaces.add(selectedRace);
                            } else {
                                System.out.println("Invalid race selection.");
                                i--;
                            }
                        }

                        newTournament.addRaces(tournamentRaces);
                        tournaments.add(newTournament);

                        System.out.println("Tournament " + tournamentName + " created with selected races.");
                    } else {
                        System.out.println("Create at least 3 races before creating a tournament.");
                    }
                    break;

                case 9:
                    if (!tournaments.isEmpty()) {
                        System.out.println("Enter the name of the tournament you want to start:");
                        String tournamentNameToStart = scanner.nextLine();

                        Tournament selectedTournament = null;
                        for (Tournament tournament : tournaments) {
                            if (tournament.getName().equalsIgnoreCase(tournamentNameToStart)) {
                                selectedTournament = tournament;
                                break;
                            }
                        }

                        if (selectedTournament != null) {
                            selectedTournament.simulateTournament();

                            // Obtener el resultado de las tres carreras del torneo
                            List<Car> tournamentResults = selectedTournament.getTournamentResults();

                            // Mostrar los tres mejores corredores del torneo
                            System.out.println("Tournament Results - Top 3:");
                            for (int i = 0; i < Math.min(tournamentResults.size(), 3); i++) {
                                Car car = tournamentResults.get(i);
                                System.out.println((i + 1) + ". Car: " + car.getBrand() + " " + car.getModel() + " (Garage: " + car.getGarageSticker() + ") - Points: " + car.getPoints());
                            }
                        } else {
                            System.out.println("Tournament " + tournamentNameToStart + " not found.");
                        }
                    } else {
                        System.out.println("No tournaments available. Create a tournament first.");
                    }
                    break;



                case 10:
                    saveToJSON(garages, cars);
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }

    private static void saveToJSON(List<Garage> garages, List<Car> cars) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode garageArray = objectMapper.createArrayNode();

            for (Garage garage : garages) {
                ObjectNode garageNode = objectMapper.createObjectNode();
                garageNode.put("name", garage.getName());

                List<Car> carsInGarage = new ArrayList<>(garage.getCars());
                ArrayNode carArray = objectMapper.createArrayNode();
                for (Car car : carsInGarage) {
                    ObjectNode carNode = objectMapper.createObjectNode();
                    carNode.put("brand", car.getBrand());
                    carNode.put("model", car.getModel());
                    carArray.add(carNode);
                }

                garageNode.set("cars", carArray);
                garageArray.add(garageNode);
            }

            objectMapper.writeValue(new File("cars.json"), garageArray);
            System.out.println("Data saved to JSON.");
        } catch (IOException e) {
            System.err.println("Error saving data to JSON: " + e.getMessage());
        }
    }
    private static void saveDataToJson(List<Garage> garages, List<Car> cars) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File("cars.json");

            ObjectNode rootNode = objectMapper.createObjectNode();

            ArrayNode garageArray = objectMapper.createArrayNode();
            for (Garage garage : garages) {
                ObjectNode garageNode = objectMapper.createObjectNode();
                garageNode.put("name", garage.getName());

                List<Car> carsInGarage = garage.getCars();
                ArrayNode carArray = objectMapper.createArrayNode();
                for (Car car : carsInGarage) {
                    ObjectNode carNode = objectMapper.createObjectNode();
                    carNode.put("brand", car.getBrand());
                    carNode.put("model", car.getModel());
                    carArray.add(carNode);
                }

                garageNode.set("cars", carArray);
                garageArray.add(garageNode);
            }

            ArrayNode carArray = objectMapper.createArrayNode();
            for (Car car : cars) {
                ObjectNode carNode = objectMapper.createObjectNode();
                carNode.put("brand", car.getBrand());
                carNode.put("model", car.getModel());
                carArray.add(carNode);
            }

            rootNode.set("garages", garageArray);
            rootNode.set("cars", carArray);

            objectMapper.writeValue(jsonFile, rootNode);

            System.out.println("Data saved to JSON.");
        } catch (IOException e) {
            System.err.println("Error saving data to JSON: " + e.getMessage());
        }
    }

    private static void loadFromJSON(List<Garage> garages, List<Car> cars) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File("cars.json");

            if (!jsonFile.exists()) {
                jsonFile.createNewFile();
                objectMapper.writeValue(jsonFile, new ArrayNode(objectMapper.getNodeFactory()));
                System.out.println("Created empty JSON file.");
            }

            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            if (jsonNode.isArray()) {
                for (JsonNode garageNode : jsonNode) {
                    String garageName = garageNode.get("name").asText();
                    Garage existingGarage = findGarageByName(garages, garageName);

                    if (existingGarage == null) {
                        existingGarage = new Garage(garageName);
                        garages.add(existingGarage);
                    }

                    if (garageNode.has("cars") && garageNode.get("cars").isArray()) {
                        ArrayNode carArray = (ArrayNode) garageNode.get("cars");
                        for (JsonNode carNode : carArray) {
                            String brand = carNode.get("brand").asText();
                            String model = carNode.get("model").asText();
                            String garageSticker = garageName;
                            Car newCar = new Car(brand, model, garageSticker);
                            cars.add(newCar);
                            existingGarage.addCar(newCar);
                        }
                    }
                }

                System.out.println("Data loaded from JSON.");
            } else {
                System.out.println("Invalid JSON structure.");
            }
        } catch (IOException e) {
            System.err.println("Error loading data from JSON: " + e.getMessage());
        }
    }

    private static Garage findGarageByName(List<Garage> garages, String name) {
        for (Garage garage : garages) {
            if (garage.getName().equals(name)) {
                return garage;
            }
        }
        return null;
    }

    private static void removeGarage(List<Garage> garages, String garageName) {
        Garage garageToRemove = null;

        for (Garage garage : garages) {
            if (garage.getName().equalsIgnoreCase(garageName)) {
                garageToRemove = garage;
                break;
            }
        }

        if (garageToRemove != null) {
            garages.remove(garageToRemove);
            System.out.println("Garage " + garageName + " removed.");
        } else {
            System.out.println("Garage " + garageName + " not found.");
        }
    }


    private static void addRandomGarageToRace(Race race, List<Garage> garages) {
        if (!garages.isEmpty()) {
            int randomIndex = (int) (Math.random() * garages.size());
            Garage randomGarage = garages.get(randomIndex);
            race.addGarage(randomGarage);
        } else {
            System.out.println("No garages available. Create at least one garage first.");
        }
    }
}