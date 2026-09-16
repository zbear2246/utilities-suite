package utilities.features.ui;

import arc.scene.ui.layout.Stack;
import arc.util.Log;
import arc.Core;
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

    // constructor
    public DesktopUi(PowerGrid powerGrid) {
        Log.info("Desktop UI constructing feature panels for powerGrid=" + (powerGrid != null) + ".");
        powerGridUi = new PowerGridUi(powerGrid);
        autoDrillUi = new AutoDrillUi();
        smartUpgradeUi = new SmartUpgradeUi();
        throughputCalculatorUi = new ThroughputCalculatorUi();

        buttons = new Buttons(powerGridUi, autoDrillUi, smartUpgradeUi, throughputCalculatorUi);

        Log.info("Desktop UI panels created: powerGridUi=" + (powerGridUi != null)
                + ", autoDrillUi=" + (autoDrillUi != null)
                + ", smartUpgradeUi=" + (smartUpgradeUi != null)
                + ", throughputCalculatorUi=" + (throughputCalculatorUi != null));
    }

    // public lifecycle
    public void init() {
        setFillParent(true);

        initializeFeaturesUi();
        initializeButtons();
        addUiElements();
        configureVisibility();

        Log.info("Desktop UI initialized with child panels=" + getChildren().size + ", buttonsVisible="
                + buttons.visible + ".");
    }

    public void update() {
        powerGridUi.update();
    }

    // setup
    private void initializeFeaturesUi() {
        powerGridUi.init();
        Log.info("Desktop UI feature initialization complete; powerGridUiInitialized=" + (powerGridUi != null) + ".");
        // autoDrillUi.init();
        // smartUpgradeUi.init();
        // throughputCalculatorUi.init();
    }

    private void initializeButtons(){
        buttons.init();
    }

    private void addUiElements() {
        add(powerGridUi);
        add(autoDrillUi);
        add(smartUpgradeUi);
        add(throughputCalculatorUi);
        add(buttons);
    }

    // layout
    private void configureVisibility(){
        powerGridUi.visible = false;
        buttons.visible = true;
    }
    public void setPositions() {
        setPowerGridPosition();
        setButtonPosition();
    }

    private void setPowerGridPosition() {
        powerGridUi.bottom().left();
        powerGridUi.marginLeft(5f).marginBottom(powerGridUi.getPrefHeight());
    }

    private void setButtonPosition() {
        findMinimap();

        float gap = 10f;

        if (minimap == null) {
            buttons.right();
            Log.info("Desktop UI button group has no minimap anchor; using right-aligned layout.");
        } else {
            buttons.top().right();

            buttons.marginTop(minimap.getPrefHeight() - buttons.getPrefHeight())
                    .marginRight(minimap.getPrefWidth() + gap);

            Log.info("Desktop UI button group aligned relative to minimap: topMargin=" + minimap.getHeight()
                    + ", rightMargin=" + (minimap.getWidth() - gap) + ".");

        }
    }

    // lookup
    private void findMinimap() {
        minimap = Core.scene.find("minimap");
        Log.info("Desktop UI minimap lookup complete; minimapFound=" + (minimap != null) + ".");
    }

}
