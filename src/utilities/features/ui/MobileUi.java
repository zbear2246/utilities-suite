package utilities.features.ui;

import arc.scene.ui.layout.Stack;
import arc.util.Log;

import utilities.features.autoDrill.AutoDrillUi;
import utilities.features.powerGrid.PowerGrid;
import utilities.features.powerGrid.PowerGridUi;
import utilities.features.smartUpgrade.SmartUpgradeUi;
import utilities.features.throughputCalculator.ThroughputCalculatorUi;

public class MobileUi extends Stack {
    private PowerGridUi powerGridUi;
    private AutoDrillUi autoDrillUi;
    private SmartUpgradeUi smartUpgradeUi;
    private ThroughputCalculatorUi throughputCalculatorUi;
    private Buttons buttons;

    public MobileUi(PowerGrid powerGrid){
        Log.info("Mobile UI constructing feature panels for powerGrid=" + (powerGrid != null) + ".");
        powerGridUi = new PowerGridUi(powerGrid);
        autoDrillUi = new AutoDrillUi();
        smartUpgradeUi = new SmartUpgradeUi();
        throughputCalculatorUi = new ThroughputCalculatorUi();

        buttons = new Buttons(powerGridUi, autoDrillUi, smartUpgradeUi, throughputCalculatorUi);
        Log.info("Mobile UI panels created: powerGridUi=" + (powerGridUi != null)
                + ", autoDrillUi=" + (autoDrillUi != null)
                + ", smartUpgradeUi=" + (smartUpgradeUi != null)
                + ", throughputCalculatorUi=" + (throughputCalculatorUi != null));
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
        Log.info("Mobile UI initialized with child panels=" + getChildren().size + ", buttonsVisible=" + buttons.visible + ".");
    }

    public void setUpPowerGridUi() {
        powerGridUi.top();
        powerGridUi.visible = false;
        Log.info("Mobile UI power grid panel anchored to top and hidden by default.");
    }

    public void setUpButtons() {
        buttons.top().right();
        buttons.init();
        buttons.visible = true;
        Log.info("Mobile UI buttons initialized and anchored to top-right.");
    }

    public void initializeFeaturesUi(){
        powerGridUi.init();
        Log.info("Mobile UI feature initialization complete; powerGridUiInitialized=" + (powerGridUi != null) + ".");
        // autoDrillUi.init();
        // smartUpgradeUi.init();
        // throughputCalculatorUi.init();
    }

    public void update(){
        powerGridUi.update();
    }
}
