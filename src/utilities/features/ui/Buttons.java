package utilities.features.ui;

import arc.scene.ui.layout.Table;
import arc.util.Log;

import mindustry.gen.Icon;

import utilities.features.autoDrill.AutoDrillUi;
import utilities.features.powerGrid.PowerGridUi;
import utilities.features.smartUpgrade.SmartUpgradeUi;
import utilities.features.throughputCalculator.ThroughputCalculatorUi;

public class Buttons extends Table {
    private PowerGridUi powerGridUi;
    private AutoDrillUi autoDrillUi;
    private SmartUpgradeUi smartUpgradeUi;
    private ThroughputCalculatorUi throughputCalculatorUi;

    private Table featureButtons;

    public Buttons(PowerGridUi powerGridUi, AutoDrillUi autoDrillUi, SmartUpgradeUi smartUpgradeUi,
            ThroughputCalculatorUi throughputCalculatorUi) {
        this.powerGridUi = powerGridUi;
        this.autoDrillUi = autoDrillUi;
        this.smartUpgradeUi = smartUpgradeUi;
        this.throughputCalculatorUi = throughputCalculatorUi;
    }

    public void init() {
        featureButtons = new Table();

        featureButtons.visible = false;
        add(featureButtons).row();

        powerGridButton();
        autoDrillButton();
        smartUpgradeButton();
        throughputCalculatorButton();

        masterToggleButton();
        Log.info("Utilities Suite feature buttons initialized.");
    }

    public void powerGridButton() {
        if (powerGridUi == null)
            return;

        featureButtons.button(Icon.power, () -> powerGridUi.toggle());
    }

    public void autoDrillButton() {
        if (autoDrillUi == null)
            return;

        featureButtons.button(Icon.production, () -> autoDrillUi.toggle());
    }

    public void smartUpgradeButton() {
        if (smartUpgradeUi == null)
            return;

        featureButtons.button(Icon.up, () -> smartUpgradeUi.toggle());
    }

    public void throughputCalculatorButton() {
        if (throughputCalculatorUi == null)
            return;

        featureButtons.button(Icon.distribution, () -> throughputCalculatorUi.toggle());
    }

    public void toggleVisibility() {
        featureButtons.visible = !featureButtons.visible;
        Log.info("Utilities Suite feature buttons " + (featureButtons.visible ? "shown" : "hidden") + ".");
    }

    public void masterToggleButton() {
        button("Utilities Suite", this::toggleVisibility);
    }
}
