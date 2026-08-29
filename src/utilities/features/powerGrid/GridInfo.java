package utilities.features.powerGrid;

public class GridInfo {
    private float production;
    private float consumption;
    private float netProduction;
    private float storedPower;
    private float capacity;

    public GridInfo(float production, float consumption, float storedPower, float capacity, float netProduction){
        this.production = production;
        this.consumption = consumption;
        this.storedPower = storedPower;
        this.capacity = capacity;
        this.netProduction = netProduction;
    }

    public float getProduction(){
        return production;
    }

    public float getConsumption(){
        return consumption;
    }
    
    public float getStoredPower(){
        return storedPower;
    }

    public float getCapacity() {
        return capacity;
    }

    public float getNetProduction() {
        return netProduction;
    }

}
