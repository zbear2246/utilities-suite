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
import utilities.features.ui.DesktopUi;
import utilities.features.ui.MobileUi;

public class UtilitiesSuite extends Mod {
    private MobileUi mobileUi;
    private DesktopUi desktopUi;

    private PowerGrid powerGrid;
    private PowerGridUi powerGridUi;

    // private AutoDrill autoDrill;
    private AutoDrillUi autoDrillUi;

    // private SmartUpgrade smartUpgrade;
    private SmartUpgradeUi smartUpgradeui;

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

        if (Vars.mobile) {
            mobileUi = new MobileUi(powerGrid);
        }
    }

    private void registerClientLoadedListener() {
        Events.on(EventType.ClientLoadEvent.class, event -> {
            if (Vars.mobile) {
                mobileUi.init();
                mobileUi.visible = true;
            }
        });
    }

    private void registerWorldLoadedListener() {
        Events.on(EventType.WorldLoadEvent.class, event -> {
            powerGrid.init();
            powerGrid.findPowerGrids();
            firstTick = true;
            worldLoaded = true;

            if (Vars.mobile) {
                Vars.ui.hudGroup.addChild(mobileUi);
            }

        });
    }

    private void registerStateChangeListener() {
        Events.on(EventType.StateChangeEvent.class, event -> {
            if (event.to != GameState.State.menu)
                return;
            worldLoaded = false;

            if (Vars.mobile) {
                Vars.ui.hudGroup.removeChild(mobileUi);
            }
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