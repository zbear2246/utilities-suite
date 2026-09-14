package utilities.features.ui;

import arc.scene.ui.layout.Table;
import utilities.features.autoDrill.AutoDrillUi;
import utilities.features.powerGrid.PowerGridUi;
import utilities.features.smartUpgrade.SmartUpgradeUi;

public class DesktopUi extends Table {
    public PowerGridUi powerGridUi;
    public AutoDrillUi autoDrillUi;
    public SmartUpgradeUi smartUpgradeUi;

    public void init(PowerGridUi powerGridUi, AutoDrillUi autoDrillUi, SmartUpgradeUi smartUpgradeUi) {
        this.powerGridUi = powerGridUi;
        this.autoDrillUi = autoDrillUi;
        this.smartUpgradeUi = smartUpgradeUi;
    }

}
