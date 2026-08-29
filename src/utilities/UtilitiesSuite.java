package utilities;

import arc.Events;
import arc.util.Time;
import mindustry.game.EventType.Trigger;
import mindustry.mod.Mod;
import utilities.features.powerGrid.PowerGrid;


public class UtilitiesSuite extends Mod{
    private float elapsedTime;
    private PowerGrid powerGrid = new PowerGrid();

    
    public UtilitiesSuite(){
        Events.run(Trigger.update, () -> {
            elapsedTime += Time.delta;

            if(elapsedTime >= 1.0f){
                powerGrid.findPowerGrids();
                powerGrid.logPowerGridInfo();
            }


            elapsedTime = 0f;
        });
    }
    
}