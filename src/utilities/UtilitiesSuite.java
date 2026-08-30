package utilities;

import arc.Events;
import arc.util.Time;
import mindustry.game.EventType;
import mindustry.game.EventType.Trigger;
import mindustry.mod.Mod;
import utilities.features.powerGrid.PowerGrid;
import utilities.features.powerGrid.PowerGridUi;


public class UtilitiesSuite extends Mod{
    private float elapsedTime;
    private PowerGrid powerGrid = new PowerGrid();
    private PowerGridUi powerGridUi;

    
    public UtilitiesSuite(){

        Events.on(EventType.WorldLoadEvent.class, event -> {
            powerGrid.init();
            powerGrid.findPowerGrids();

            Events.run(Trigger.update, () -> {

                elapsedTime += Time.delta / 60;

                if (elapsedTime >- 2){
                    if (powerGrid.getGridInfo() == null){
                        powerGrid.findPowerGrids();
                        powerGrid.logPowerGridInfo();
                        powerGridUi = new PowerGridUi();
                    } else {
                        powerGrid.update();
                        powerGridUi = new PowerGridUi();
                    }

                    
                }
            });
        });
    }
    
}