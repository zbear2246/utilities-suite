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
        Log.info("Mobile UI initialized.");
    }

    public void setUpPowerGridUi() {
        powerGridUi.top();
        powerGridUi.visible = false;
    }

    public void setUpButtons() {
        buttons.top().right();
        buttons.init();
        buttons.visible = true;
    }

    public void initializeFeaturesUi(){
        powerGridUi.init();
        // autoDrillUi.init();
        // smartUpgradeUi.init();
        // throughputCalculatorUi.init();
    }

    public void update(){
        powerGridUi.update();
    }
}
