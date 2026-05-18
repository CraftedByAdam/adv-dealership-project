package com.pluralsight.dealership;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;
    private Scanner scanner;

    public UserInterface() {
        scanner = new Scanner(System.in);
    }

    public void display() {
        init();
        boolean quit = false;
        while (!quit) {
            System.out.println("---------- Menu ----------");
            System.out.println("1. Get vehicles by price");
            System.out.println("2. Get vehicles by make and model");
            System.out.println("3. Get vehicles by year");
            System.out.println("4. Get vehicles by color");
            System.out.println("5. Get vehicles by mileage");
            System.out.println("6. Get vehicles by type");
            System.out.println("7. Get all vehicles");
            System.out.println("8. Add vehicle");
            System.out.println("9. Remove vehicle");
            System.out.println("10. Sell/Lease a Vehicle");
            System.out.println("99. Quit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    processGetByPriceRequest();
                    break;
                case "2":
                    processGetByMakeModelRequest();
                    break;
                case "3":
                    processGetByYearRequest();
                    break;
                case "4":
                    processGetByColorRequest();
                    break;
                case "5":
                    processGetByMileageRequest();
                    break;
                case "6":
                    processGetByVehicleTypeRequest();
                    break;
                case "7":
                    processGetAllVehiclesRequest();
                    break;
                case "8":
                    processAddVehicleRequest();
                    break;
                case "9":
                    processRemoveVehicleRequest();
                    break;
                case "10":
                    processSellLeaseVehicleRequest();
                    break;
                case "99":
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        double min = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double max = scanner.nextDouble();
        List<Vehicle> vehicles = dealership.getVehiclesByPrice(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByMakeModelRequest() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(vehicles);
    }

    public void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int min = scanner.nextInt();
        System.out.print("Enter maximum year: ");
        int max = scanner.nextInt();
        List<Vehicle> vehicles = dealership.getVehiclesByYear(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByColor(color);
        displayVehicles(vehicles);
    }

    public void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int min = scanner.nextInt();
        System.out.print("Enter maximum mileage: ");
        int max = scanner.nextInt();
        List<Vehicle> vehicles = dealership.getVehiclesByMileage(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByVehicleTypeRequest() {
        System.out.print("Enter vehicle type: ");
        String vehicleType = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByType(vehicleType);
        displayVehicles(vehicles);
    }

    public void processGetAllVehiclesRequest() {
        List<Vehicle> vehicles = dealership.getAllVehicles();
        displayVehicles(vehicles);
    }

    public void processAddVehicleRequest() {
        System.out.print("Enter vehicle vin: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter vehicle make: ");
        String make = scanner.nextLine();

        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine();

        System.out.print("Enter vehicle year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter vehicle price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter vehicle color: ");
        String color = scanner.nextLine();

        System.out.print("Enter vehicle mileage: ");
        int mileage = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter vehicle type (Car, Truck, SUV, Motorcycle): ");
        String type = scanner.nextLine();

        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, mileage, price);

        dealership.addVehicle(vehicle);
        System.out.println("Vehicle added successfully!");
        DealershipFileManager manager = new DealershipFileManager();
        manager.saveDealership(dealership);
    }

    public void processRemoveVehicleRequest() {
        System.out.print("Enter the VIN of the vehicle you wish to remove: ");
        int vin = scanner.nextInt();

        boolean vehicleRemoved = false;
        for (Vehicle vehicle : dealership.getAllVehicles()) {
            if (vehicle.getVin() == vin) {
                dealership.removeVehicle(vehicle);
                System.out.println("Vehicle removed successfully!");
                vehicleRemoved = true;
                break;
            }
        }

        if (!vehicleRemoved) {
            System.out.println("Vehicle not found. Please try again.");
            return;
        }

        DealershipFileManager manager = new DealershipFileManager();
        manager.saveDealership(dealership);
    }

    public void processSellLeaseVehicleRequest() {
        System.out.print("Enter the Date: ");
        String date = scanner.nextLine();

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your Email: ");
        String email = scanner.nextLine();

        System.out.println("Enter the Vin: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        Vehicle vehicle = null;
        for (Vehicle vehicleFound : dealership.getAllVehicles()) {
            if (vehicleFound.getVin() == vin) {
                vehicle = vehicleFound;
            }
        }

        if (vehicle == null) {
            System.out.println("Vehicle not found. Please try again.");
            return;
        }
        System.out.println("Enter Sale or Lease: ");
        String saleOrLease = scanner.nextLine();

        int processingFee;
        if (vehicle.getPrice() < 10000) {
            processingFee = 295;
        }else {
            processingFee = 495;
        }
        if (saleOrLease.equalsIgnoreCase("Sale")) {
            System.out.print("Would you like to finance? (Yes/No): ");
            String finance = scanner.nextLine();
            boolean isFinance = finance.equalsIgnoreCase("Yes");
            SalesContract salesContract = new SalesContract(date, name, email, vehicle,0.05, 100, processingFee, isFinance);

            ContractFileManager contractFileManager = new ContractFileManager();
            contractFileManager.saveContract(salesContract);

        } else if (saleOrLease.equalsIgnoreCase("Lease")) {
            boolean isLease = scanner.nextLine().equalsIgnoreCase("No");
            LeaseContract leaseContract = new LeaseContract(date, name,email, vehicle, vehicle.getPrice() * 0.50, vehicle.getPrice() * 0.07);

            ContractFileManager contractFileManager = new ContractFileManager();
            contractFileManager.saveContract(leaseContract);
        }
        dealership.removeVehicle(vehicle);

        DealershipFileManager dealershipFileManager = new DealershipFileManager();
        dealershipFileManager.saveDealership(dealership);
    }

    private void init() {
        DealershipFileManager manager = new DealershipFileManager();
        dealership = manager.getDealership();
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.toString());
        }
    }
}