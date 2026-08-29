package utilities.features.powerGrid;

import mindustry.world.blocks.power.PowerGraph;
import utilities.features.powerGrid.GridInfo;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Groups;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import arc.util.Log;
import mindustry.Vars;

public class PowerGrid {
    Set<PowerGraph> powerGraphs = new HashSet<>();
    Map<PowerGraph, GridInfo> gridInfo = new HashMap<>();

    public void findPowerGrids(){
        Team myTeam = Vars.player.team();
        

        for (Building building : Groups.build){
            if (building.team != myTeam) continue;
            if (building.power == null) continue;

            powerGraphs.add(building.power.graph);
        }
        Log.info("found" + powerGraphs.size() + " power graphs: " + powerGraphs);
    }

    public void logPowerGridInfo(){
        for (PowerGraph graph : powerGraphs){
            GridInfo grid = new GridInfo(
                graph.getPowerProduced(),
                graph.getPowerNeeded(),
                graph.getBatteryStored(),
                graph.getBatteryCapacity(),
                graph.getPowerBalance()
            );
            gridInfo.put(graph, grid);
        }
    }
}
