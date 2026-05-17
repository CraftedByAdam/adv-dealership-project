package com.pluralsight.dealership;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ContractFileManager {

    public void saveContract(Contract contract) {

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("contracts.csv", true))) {

                if (contract instanceof SalesContract salesContract) {
                    bw.write("Sale|" + salesContract.getDate() + "|" + salesContract.getName() + "|" + salesContract.getEmail() +
                            "|" + salesContract.getVehicleSold().getVin() + "|" + salesContract.getVehicleSold().getYear() + "|" +
                            salesContract.getVehicleSold().getMake() + "|" + salesContract.getVehicleSold().getModel() + "|" +
                            salesContract.getVehicleSold().getVehicleType() + "|" + salesContract.getVehicleSold().getColor() +
                            "|" + salesContract.getVehicleSold().getOdometer() + "|" + salesContract.getVehicleSold().getPrice() +
                            "|" + salesContract.getSalesTaxAmount() + "|" + salesContract.getRecordingFee() +
                            "|" + salesContract.getProcessingFee() + "|" + salesContract.getTotalPrice() + "|" + salesContract.isFinanceOption() +
                            "|" + salesContract.getMonthlyPayment());
                    bw.newLine();

                } else if (contract instanceof LeaseContract) {
                    LeaseContract leaseContract = (LeaseContract) contract;
                    bw.write("Lease|" + leaseContract.getDate() + "|" + leaseContract.getName() + "|" + leaseContract.getEmail() +
                            "|" + leaseContract.getVehicleSold().getVin() + "|" + leaseContract.getVehicleSold().getYear() + "|" +
                            leaseContract.getVehicleSold().getMake() + "|" + leaseContract.getVehicleSold().getModel() + "|" +
                            leaseContract.getVehicleSold().getVehicleType() + "|" + leaseContract.getVehicleSold().getColor() +
                            "|" + leaseContract.getVehicleSold().getOdometer() + "|" + leaseContract.getVehicleSold().getPrice() +
                            "|" + leaseContract.getExpectedEndingValue() + "|" + leaseContract.getLeaseFee() +
                            "|" + leaseContract.getTotalPrice() + "|" +  leaseContract.getMonthlyPayment());
                    bw.newLine();
                }
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
    }
}