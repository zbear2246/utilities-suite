package utilities.features.powerGrid;

import utilities.features.Feature;

import mindustry.world.blocks.power.PowerGraph;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.Vars;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import arc.struct.Seq;
import arc.util.Log;

public class PowerGrid implements Feature {
    private Set<PowerGraph> powerGraphs;
    private Map<PowerGraph, GridInfo> gridInfo;
    private Seq<GridInfo> displayOrder;

    public void init() {
        powerGraphs = new HashSet<PowerGraph>();
        gridInfo = new HashMap<PowerGraph, GridInfo>();
        displayOrder = new Seq<GridInfo>();
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

        for (Building building : Groups.build) {
            if (building.team != myTeam)
                continue;
            if (building.power == null)
                continue;

            powerGraphs.add(building.power.graph);
        }
        Log.info("found " + powerGraphs.size());
    }

    public void logPowerGridInfo() {
        for (PowerGraph graph : powerGraphs) {
            GridData gridData = getGridData(graph);

            GridInfo grid = new GridInfo(
                    gridData.production,
                    gridData.consumption,
                    gridData.netProduction,
                    gridData.storedBatteryPower,
                    gridData.totalBatteryCapacity

            );

            gridInfo.put(graph, grid);
            displayOrder.add(grid);
        }
    }

    public void update() {
        findPowerGrids();

        var removedGraphs = gridInfo.keySet().stream()
                .filter(graph -> !powerGraphs.contains(graph))
                .toList();

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

    private record GridData(int production, int consumption, int netProduction, int storedBatteryPower,
            int totalBatteryCapacity) {
    }

}
