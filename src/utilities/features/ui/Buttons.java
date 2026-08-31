package utilities.features.ui;

import arc.scene.ui.layout.Table;
import mindustry.Vars;
import utilities.features.autoDrill.AutoDrillUi;
import utilities.features.powerGrid.PowerGridUi;
import utilities.features.smartUpgrade.SmartUpgradeUi;

public class Buttons extends Table {
    private PowerGridUi powerGridUi;
    private AutoDrillUi autoDrillUi;
    private SmartUpgradeUi smartUpgradeUi;
    private Table featureButtons;

    public Buttons(PowerGridUi powerGridUi, AutoDrillUi autoDrillUi, SmartUpgradeUi smartUpgradeUi) {
        this.powerGridUi = powerGridUi;
        this.autoDrillUi = autoDrillUi;
        this.smartUpgradeUi = smartUpgradeUi;
    }

    public void init() {
        featureButtons = new Table();

        top().right().setFillParent(true);
        featureButtons.visible = false;
        add(featureButtons).row();

        powerGridButton();
        autoDrillButton();
        smartUpgradeButton();

        masterToggleButton();

        Vars.ui.hudGroup.addChild(powerGridUi);
        //Vars.ui.hudGroup.addChild(autoDrillUi);
        //Vars.ui.hudGroup.addChild(smartUpgradeUi);

    }

    public void powerGridButton() {
        if (powerGridUi == null)
            return;

        powerGridUi.init();

        featureButtons.button("Power Grid", () -> powerGridUi.toggle()).size(50f, 50f);
    }

    public void autoDrillButton() {
        if (autoDrillUi == null)
            return;

        // autoDrillui.init();

        featureButtons.button("Auto Drill", () -> autoDrillUi.toggle()).size(50f, 50f);
    }

    public void smartUpgradeButton() {
        if (smartUpgradeUi == null)
            return;

        // smartUpgradeButton.init();

        featureButtons.button("Smart Upgrade", () -> smartUpgradeUi.toggle()).size(50f, 50f);
    }

    public void toggleVisibility() {
        featureButtons.visible = !featureButtons.visible;
    }

    public void masterToggleButton() {
        button("Utilities Suite", this::toggleVisibility).size(50f, 50f);
    }
}
