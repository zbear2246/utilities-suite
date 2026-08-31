package utilities.features.powerGrid;

import arc.scene.ui.layout.Table;

public class PowerGridUi extends Table{

    public PowerGridUi(){
        createGraph();
    }

    public void createGraph(){
        top();
        setFillParent(true);
        visible = false;
        add("Power Grid Graph");
    }

    public void toggle(){
        visible = !visible;
    }
}
