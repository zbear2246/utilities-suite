package utilities.features.ui;

import arc.scene.ui.layout.Stack;
import arc.util.Log;
import arc.Core;
import arc.math.geom.Vec2;
import arc.scene.Element;

import utilities.features.autoDrill.AutoDrillUi;
import utilities.features.powerGrid.PowerGrid;
import utilities.features.powerGrid.PowerGridUi;
import utilities.features.smartUpgrade.SmartUpgradeUi;
import utilities.features.throughputCalculator.ThroughputCalculatorUi;

public class DesktopUi extends Stack {
    private PowerGridUi powerGridUi;
    private AutoDrillUi autoDrillUi;
    private SmartUpgradeUi smartUpgradeUi;
    private ThroughputCalculatorUi throughputCalculatorUi;
    private Buttons buttons;
    private Element minimap;

    public DesktopUi(PowerGrid powerGrid) {
        powerGridUi = new PowerGridUi(powerGrid);
        autoDrillUi = new AutoDrillUi();
        smartUpgradeUi = new SmartUpgradeUi();
        throughputCalculatorUi = new ThroughputCalculatorUi();

        buttons = new Buttons(powerGridUi, autoDrillUi, smartUpgradeUi, throughputCalculatorUi);
    }

    public void init() {
        setFillParent(true);

        initializeFeaturesUi();

        setUpButtons();
        setUpPowerGridUi();

        add(powerGridUi);
        add(autoDrillUi);
        add(smartUpgradeUi);
        add(throughputCalculatorUi);
        add(buttons);
        Log.info("Desktop UI initialized.");
    }

    public void findMinimap() {
        minimap = Core.scene.find("minimap");
    }

    public void setUpPowerGridUi() {
        powerGridUi.bottom().left();
        powerGridUi.marginLeft(5f).marginBottom(20f);
        powerGridUi.visible = false;
    }

    public void setUpButtons() {
        buttons.init();
        buttons.visible = true;
        buttons.bottom().left();
    }

    public void setButtonLocation() {
        findMinimap();

        if (minimap == null) {
            buttons.right();
            Log.info("Minimap not found; placing Utilities Suite buttons on the right.");
        } else {
            Vec2 minimapPosition = minimap.localToStageCoordinates(new Vec2(0,0));
            buttons.marginLeft(minimapPosition.x - 20f).marginBottom(minimapPosition.y);
            Log.info("Utilities Suite buttons positioned relative to the minimap.");
        }
    }

    public void initializeFeaturesUi() {
        powerGridUi.init();
        // autoDrillUi.init();
        // smartUpgradeUi.init();
        // throughputCalculatorUi.init();
    }

    public void update() {
        powerGridUi.update();
    }
}
