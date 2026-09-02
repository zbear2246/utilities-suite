package utilities.features.powerGrid;

import utilities.features.Feature;

import mindustry.world.blocks.power.PowerGraph;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.Vars;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import arc.struct.Seq;
import arc.util.Log;

public class PowerGrid implements Feature {
    private Set<PowerGraph> powerGraphs = new HashSet<>();
    private Map<PowerGraph, GridInfo> gridInfo = new HashMap<>();
    private Seq<GridInfo> displayOrder = new Seq<>();

    public void init() {
        powerGraphs.clear();
        gridInfo.clear();
        displayOrder.clear();
    }

    public Map<PowerGraph, GridInfo> getGridInfo() {
        return gridInfo;
    }

    public Seq<GridInfo> getDisplayOrder() {
        return displayOrder;
    }

    public void findPowerGrids() {
        powerGraphs.clear();
        Team myTeam = Vars.player.team();

        Groups.build.each(
                b -> b.team == myTeam && b.power != null && b.power.graph != null,
                b -> powerGraphs.add(b.power.graph));

        Log.info("found " + powerGraphs.size());
    }

    public void logPowerGridInfo() {
        int displayId = 1;
        boolean available = false;

        while (!available){
            available = true;

            for (GridInfo grid: gridInfo.values()){
                if (grid.getId() == displayId){
                    available = false;
                    displayId++;
                    break;
                }
            }
        }

        for (PowerGraph graph : powerGraphs) {
            if(gridInfo.containsKey(graph)) continue;

            GridData gridData = getGridData(graph);

            GridInfo grid = new GridInfo(
                    displayId,
                    gridData.production,
                    gridData.consumption,
                    gridData.netProduction,
                    gridData.storedBatteryPower,
                    gridData.totalBatteryCapacity

            );

            gridInfo.put(graph, grid);
            displayOrder.add(grid);
            displayId++;
        }
    }

    
    public void update() {
        findPowerGrids();
        logPowerGridInfo();

        Seq<PowerGraph> removedGraphs = new Seq<>();

        for (PowerGraph graph : gridInfo.keySet()) {
            if (powerGraphs.contains(graph))
                continue;
            removedGraphs.add(graph);
        }

        for (PowerGraph graph : removedGraphs) {
            displayOrder.remove(gridInfo.get(graph));
            gridInfo.remove(graph);
        }

        for (var entry : gridInfo.entrySet()) {
            PowerGraph graph = entry.getKey();
            GridInfo grid = entry.getValue();

            GridData gridData = getGridData(graph);

            boolean updated = grid.update(
                    gridData.production,
                    gridData.consumption,
                    gridData.netProduction,
                    gridData.storedBatteryPower,
                    gridData.totalBatteryCapacity);
            Log.info(grid.toString());

            if (updated)
                updateDisplayOrder(grid);
        }
    }

    private void updateDisplayOrder(GridInfo info) {
        displayOrder.remove(info);
        displayOrder.insert(0, info);
    }

    private GridData getGridData(PowerGraph graph) {

        int production = Math.round(graph.getLastScaledPowerIn() * 60),
                consumption = Math.round(graph.getLastScaledPowerOut() * 60),
                netProduction = Math.round(graph.getPowerBalance() * 60),
                storedBatteryPower = Math.round(graph.getBatteryStored()),
                totalBatteryCapacity = Math.round(graph.getTotalBatteryCapacity());

        return new GridData(production, consumption, netProduction, storedBatteryPower, totalBatteryCapacity);
    }

    private static class GridData {
        final int production;
        final int consumption;
        final int netProduction;
        final int storedBatteryPower;
        final int totalBatteryCapacity;

        GridData(int production, int consumption, int netProduction, int storedBatteryPower, int totalBatteryCapacity) {
            this.production = production;
            this.consumption = consumption;
            this.netProduction = netProduction;
            this.storedBatteryPower = storedBatteryPower;
            this.totalBatteryCapacity = totalBatteryCapacity;
        }
    }
}
