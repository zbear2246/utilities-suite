package utilities;

import arc.Events;
import arc.util.Time;
import mindustry.Vars;
import mindustry.core.GameState;
import mindustry.game.EventType;
import mindustry.game.EventType.Trigger;
import mindustry.mod.Mod;

import utilities.features.autoDrill.AutoDrillUi;
import utilities.features.powerGrid.PowerGrid;
import utilities.features.powerGrid.PowerGridUi;
import utilities.features.smartUpgrade.SmartUpgradeUi;
import utilities.features.ui.Buttons;

public class UtilitiesSuite extends Mod {
    private PowerGrid powerGrid;
    private PowerGridUi powerGridUi;

    // private AutoDrill autoDrill;
    private AutoDrillUi autoDrillUi;

    // private SmartUpgrade smartUpgrade;
    private SmartUpgradeUi smartUpgradeui;

    private Buttons uiToggleButtons;

    private float elapsedTime;
    private boolean worldLoaded;
    private boolean firstTick;

    public UtilitiesSuite() {
        initialize();
        registerClientLoadedListener();
        registerWorldLoadedListener();
        registerStateChangeListener();
        runEveryTick();
    }

    private void initialize() {
        powerGrid = new PowerGrid();
        powerGridUi = new PowerGridUi(powerGrid);

        // autoDrill = new AutoDrill();
        autoDrillUi = new AutoDrillUi();

        // smartUpgrade = new SmartUpgrade();
        smartUpgradeui = new SmartUpgradeUi();

        uiToggleButtons = new Buttons(powerGridUi, autoDrillUi, smartUpgradeui);
    }

    private void registerClientLoadedListener() {
        Events.on(EventType.ClientLoadEvent.class, event -> {
            uiToggleButtons.init();
            uiToggleButtons.visible = true;
        });
    }

    private void registerWorldLoadedListener() {
        Events.on(EventType.WorldLoadEvent.class, event -> {
            powerGrid.init();
            powerGrid.findPowerGrids();
            Vars.ui.hudGroup.addChild(uiToggleButtons);
            firstTick = true;
            worldLoaded = true;
        });
    }

    private void registerStateChangeListener() {
        Events.on(EventType.StateChangeEvent.class, event -> {
            if (event.to != GameState.State.menu)
                return;
            worldLoaded = false;
            Vars.ui.hudGroup.removeChild(uiToggleButtons);
        });
    }

    private void runEveryTick() {
        Events.run(Trigger.update, () -> {
            if (!worldLoaded)
                return;

            elapsedTime += Time.delta / 60;

            if (firstTick) {
                powerGrid.findPowerGrids();
                powerGrid.logPowerGridInfo();
                powerGridUi.update();
                firstTick = false;
            }

            if (!(elapsedTime >= 2.0f))
                return;

            powerGrid.update();
            powerGridUi.update();
            elapsedTime = 0f;

        });
    }

}