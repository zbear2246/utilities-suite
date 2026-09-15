package utilities;

import arc.Events;
import arc.util.Log;
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

    // private AutoDrill autoDrill;

    // private SmartUpgrade smartUpgrade;

    private float elapsedTime;
    private boolean worldLoaded;
    private boolean firstTick;

    public UtilitiesSuite() {
        initialize();
        registerClientLoadedListener();
        registerWorldLoadedListener();
        registerStateChangeListener();
        runEveryTick();
        Log.info("Utilities Suite initialized for " + (Vars.mobile ? "mobile" : "desktop") + " clients.");
    }

    private void initialize() {

        powerGrid = new PowerGrid();
        // autoDrill = new AutoDrill();
        // smartUpgrade = new SmartUpgrade();

        if (Vars.mobile) {
            mobileUi = new MobileUi(powerGrid);
        } else {
            desktopUi = new DesktopUi(powerGrid);
        }
    }

    private void registerClientLoadedListener() {
        Events.on(EventType.ClientLoadEvent.class, event -> {
            Log.info("Utilities Suite client UI loading.");
            if (Vars.mobile) {
                mobileUi.init();
                mobileUi.visible = true;
            } else {
                desktopUi.init();
                desktopUi.visible = true;
            }
        });
    }

    private void registerWorldLoadedListener() {
        Events.on(EventType.WorldLoadEvent.class, event -> {
            Log.info("Utilities Suite world loaded; initializing power grid tracking.");
            powerGrid.init();
            powerGrid.findPowerGrids();
            firstTick = true;
            worldLoaded = true;

            if (Vars.mobile) {
                Vars.ui.hudGroup.addChild(mobileUi);
            } else {
                desktopUi.setButtonLocation();
                Vars.ui.hudGroup.addChild(desktopUi);
            }

        });
    }

    private void registerStateChangeListener() {
        Events.on(EventType.StateChangeEvent.class, event -> {
            if (event.to != GameState.State.menu)
                return;
            Log.info("Utilities Suite world unloaded; hiding feature UI.");
            worldLoaded = false;

            if (Vars.mobile) {
                Vars.ui.hudGroup.removeChild(mobileUi);
            } else {
                Vars.ui.hudGroup.removeChild(desktopUi);
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
                update();
                firstTick = false;
            }

            if (!(elapsedTime >= 2.0f))
                return;

            update();
            elapsedTime = 0f;

        });
    }

    private void update(){
        if (!firstTick) powerGrid.update();

        if (Vars.mobile) {
            mobileUi.update();
        } else {
            desktopUi.update();
        }
    }

}