package utilities.features.powerGrid;

public class GridInfo {
    private int id;
    private int production;
    private int consumption;
    private int netProduction;
    private int storedBatteryPower;
    private int totalBatteryCapacity;

    public GridInfo(int id, int production, int consumption, int netProduction, int storedBatteryPower,
            int totalBatteryCapacity) {
        this.id = id;
        this.production = production;
        this.consumption = consumption;
        this.netProduction = netProduction;
        this.storedBatteryPower = storedBatteryPower;
        this.totalBatteryCapacity = totalBatteryCapacity;

    }

    public int getId(){
        return id;
    }

    public int getProduction() {
        return production;
    }

    public int getConsumption() {
        return consumption;
    }

    public int getNetProduction() {
        return netProduction;
    }

    public int getStoredBatteryPower() {
        return storedBatteryPower;
    }

    public int getTotalBatteryCapacity() {
        return totalBatteryCapacity;
    }

    public boolean update(int production, int consumption, int netProduction, int storedBatteryPower,
            int totalBatteryCapacity) {
        boolean changed = false;
        if (this.production != production) {
            this.production = production;
            changed = true;
        }
        if (this.consumption != consumption) {
            this.consumption = consumption;
            changed = true;
        }
        if (this.netProduction != netProduction) {
            this.netProduction = netProduction;
            changed = true;
        }
        if (this.storedBatteryPower != storedBatteryPower) {
            this.storedBatteryPower = storedBatteryPower;
            changed = true;
        }
        if (this.totalBatteryCapacity != totalBatteryCapacity) {
            this.totalBatteryCapacity = totalBatteryCapacity;
            changed = true;
        }

        return changed;
    }

    @Override
    public String toString() {
        String result = "";

        result = "\n{\n" +
                "\"ID\": " + getId() + ",\n" +
                "\"Production\": " + getProduction() + ",\n" +
                "\"Consumption\": " + getConsumption() + ",\n" +
                "\"Net Production\": " + getNetProduction() + ",\n" +
                "\"Power Stored\": " + getStoredBatteryPower() + ",\n" +
                "\"totalBatteryCapacity\": " + getTotalBatteryCapacity() +
                "\n}\n";

        return result;
    }

}
