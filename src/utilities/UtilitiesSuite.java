package utilities;

import arc.Core;
import arc.Events;
import arc.input.KeyCode;
import arc.scene.ui.layout.Table;
import arc.util.Time;
import mindustry.Vars;
import mindustry.core.GameState;
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
    private Table toggleButton;

    public UtilitiesSuite() {

        Events.on(EventType.WorldLoadEvent.class, event -> {
            powerGrid.init();
            powerGrid.findPowerGrids();
            powerGridUi = new PowerGridUi();
            worldLoaded = true;
            Vars.ui.hudGroup.addChild(powerGridUi);

            if (Vars.mobile) {
                toggleButton = new Table();
                toggleButton.top().right();
                toggleButton.setFillParent(true);
                toggleButton.button("power", this::togglePowerGridUi).size(80f, 40f);
                Vars.ui.hudGroup.addChild(toggleButton);
            }
        });

        Events.run(Trigger.update, () -> {
            if (!worldLoaded)
                return;

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

            if (!Vars.mobile && Core.input.keyTap(KeyCode.p)) {
                togglePowerGridUi();
            }

        });
        Events.on(EventType.StateChangeEvent.class, event -> {
            if (event.to == GameState.State.menu) {
                worldLoaded = false;
                Vars.ui.hudGroup.removeChild(powerGridUi);
                Vars.ui.hudGroup.removeChild(toggleButton);
            }

        });
    }

    public void togglePowerGridUi() {
        if (powerGridUi != null) {
            powerGridUi.visible = !powerGridUi.visible;
        }
    }

}