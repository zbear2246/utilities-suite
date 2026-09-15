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
    private Set<Integer> usedIDs = new HashSet<>();
    private Map<PowerGraph, GridInfo> gridInfo = new HashMap<>();
    private Seq<GridInfo> displayOrder = new Seq<>();
    private int lastLoggedGraphCount = -1;

    public void init() {
        powerGraphs.clear();
        usedIDs.clear();
        gridInfo.clear();
        displayOrder.clear();
        lastLoggedGraphCount = -1;
        Log.info("Power grid tracker reset.");
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

        if (powerGraphs.size() != lastLoggedGraphCount) {
            Log.info("Power grid topology changed; tracking " + powerGraphs.size() + " grid(s).");
            lastLoggedGraphCount = powerGraphs.size();
        }
    }

    public void logPowerGridInfo() {

        for (PowerGraph graph : powerGraphs) {
            if (gridInfo.containsKey(graph))
                continue;
            int displayId = 1;

            while (usedIDs.contains(displayId))
                displayId++;

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
            usedIDs.add(displayId);
            Log.info("Power grid " + displayId + " discovered: " + grid);
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
            usedIDs.remove(gridInfo.get(graph).getId());
            Log.info("Power grid " + gridInfo.get(graph).getId() + " removed from tracking.");
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
            if (updated) {
                Log.info("Power grid " + grid.getId() + " updated: " + grid);
                updateDisplayOrder(grid);
            }
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
