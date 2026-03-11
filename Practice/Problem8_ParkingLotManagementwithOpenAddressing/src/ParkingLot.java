class Vehicle {
    String licensePlate;
    long entryTime;

    Vehicle(String licensePlate) {
        this.licensePlate = licensePlate;
        this.entryTime = System.currentTimeMillis();
    }
}

public class ParkingLot {
    private Vehicle[] table;
    private int size;

    public ParkingLot(int size) {
        this.size = size;
        table = new Vehicle[size];
    }

    private int hash(String licensePlate) {
        return Math.abs(licensePlate.hashCode()) % size;
    }

    public void parkVehicle(String licensePlate) {
        int index = hash(licensePlate);
        int probes = 0;

        while (table[index] != null) {
            index = (index + 1) % size;
            probes++;
        }

        table[index] = new Vehicle(licensePlate);
        System.out.println(licensePlate + " parked at spot " + index + " with " + probes + " probes");
    }

    public void exitVehicle(String licensePlate) {
        int index = hash(licensePlate);
        int start = index;

        while (table[index] != null) {
            if (table[index].licensePlate.equals(licensePlate)) {
                long duration = (System.currentTimeMillis() - table[index].entryTime) / 1000;
                table[index] = null;
                System.out.println(licensePlate + " exited from spot " + index + ", duration: " + duration + " sec");
                return;
            }
            index = (index + 1) % size;
            if (index == start) break;
        }

        System.out.println("Vehicle not found");
    }

    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot(10);

        lot.parkVehicle("ABC1234");
        lot.parkVehicle("XYZ9999");
        lot.parkVehicle("ABC1235");

        lot.exitVehicle("ABC1234");
    }
}
