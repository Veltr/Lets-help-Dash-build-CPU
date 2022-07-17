package view.elements.logic;

import model.data.LogicElementData;
import model.data.WireData;

public class NOTElement extends LogicElement {
    public NOTElement(){
        super();
        _data = new LogicElementData(1, 1, e -> {
            WireData[] out = new WireData[1];
            out[0] = new WireData(e[0].getType(), e[0].getValue() == 0 ? 1 : 0);
            return out;
        });
        getGFX("src/resources/data/NOT.dat");
        setVisibleAllPorts(false);
    }
}
