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

    public Set<PowerGraph> getPowerGraphs() {
        return powerGraphs;
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
            GridInfo grid = new GridInfo(
                    Math.round(graph.getLastScaledPowerIn() * 60),
                    Math.round(graph.getLastScaledPowerOut() * 60),
                    Math.round(graph.getBatteryStored()),
                    Math.round(graph.getTotalBatteryCapacity()),
                    Math.round(graph.getPowerBalance() * 60));

            gridInfo.put(graph, grid);
            Log.info(grid.toString());
        }
    }

    public void update() {
        findPowerGrids();

        gridInfo.entrySet().removeIf(entry -> !powerGraphs.contains(entry.getKey()));

        for (var entry : gridInfo.entrySet()) {
            PowerGraph graph = entry.getKey();
            GridInfo grid = entry.getValue();

            grid.update(
                    Math.round(graph.getLastScaledPowerIn() * 60),
                    Math.round(graph.getLastScaledPowerOut() * 60),
                    Math.round(graph.getBatteryStored()),
                    Math.round(graph.getTotalBatteryCapacity()),
                    Math.round(graph.getPowerBalance() * 60));
            Log.info(grid.toString());
        }
    }
}
