package utilities;

import arc.Events;
import arc.util.Time;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.EventType.Trigger;
import mindustry.mod.Mod;
import utilities.features.powerGrid.PowerGrid;
import utilities.features.powerGrid.PowerGridUi;

public class UtilitiesSuite extends Mod {
    private float elapsedTime;
    private PowerGrid powerGrid = new PowerGrid();
    private PowerGridUi powerGridUi;
    private boolean worldLoaded = false;

    public UtilitiesSuite() {

        Events.on(EventType.WorldLoadEvent.class, event -> {
            powerGrid.init();
            powerGrid.findPowerGrids();
            powerGridUi = new PowerGridUi();
            worldLoaded = true;
            Vars.ui.hudGroup.addChild(powerGridUi);
        });

        Events.run(Trigger.update, () -> {
            if(!worldLoaded) return;

            elapsedTime += Time.delta / 60;

            if (elapsedTime >= 2.0f) {
                if (powerGrid.getGridInfo().isEmpty()) {
                    powerGrid.findPowerGrids();
                    powerGrid.logPowerGridInfo();
                    elapsedTime = 0f;
                } else {
                    powerGrid.update();
                    elapsedTime = 0f;
                }

            }
        });
    }

}