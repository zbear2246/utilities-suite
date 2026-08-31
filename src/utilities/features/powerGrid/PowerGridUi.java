package utilities.features.powerGrid;

import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.gen.Tex;

public class PowerGridUi extends Table {
    private PowerGrid powerGrid;
    private Seq<GridInfo> displayOrder;
    private Table powerGridGraph;
    private Table gridTable;

    public PowerGridUi(PowerGrid powerGrid) {
        this.powerGrid = powerGrid;
    }

    public void init() {
        powerGridGraph = new Table();
        displayOrder = powerGrid.getDisplayOrder();
        createGraph();
    }

    public void createGraph() {
        setPosition();
        createPowerGraph();

        add("Power Grid Graph").row();
        add(powerGridGraph).row();

    }

    private void setPosition(){
        top();
        setFillParent(true);
        visible = false;
        powerGridGraph.top();
        powerGridGraph.visible = false;
    }

    private void createPowerGraph(){
        for (int i = 0; i <= 1 && i < displayOrder.size; i++) {
            GridInfo gridInfo = displayOrder.get(i);
            gridTable = new Table();

            gridTable.top();
            gridTable.background(Tex.pane);

            gridTable.add("Graph " + i).row();;
            gridTable.add("Production: " + gridInfo.getProduction()).row();
            gridTable.add("Consumption: " + gridInfo.getConsumption()).row();
            gridTable.add("Net Production: " + gridInfo.getNetProduction()).row();
            gridTable.add("Stored Battery Power: " + gridInfo.getStoredBatteryPower()).row();
            gridTable.add("Total Battery: " + gridInfo.getTotalBatteryCapacity()).row();

            powerGridGraph.add(gridTable).pad(10f);
        }
    }

    public void update(){
        powerGridGraph.clear();
        createPowerGraph();
    }

    public void toggle() {
        visible = !visible;
        powerGridGraph.visible = !powerGridGraph.visible;
    }
}
