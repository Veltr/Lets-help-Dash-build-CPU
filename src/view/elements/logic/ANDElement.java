package view.elements.logic;

import model.data.LogicElementData;
import model.data.WireData;

public class ANDElement extends LogicElement {
    public ANDElement(){
        super();
        _data = new LogicElementData(2, 1, e -> {
            WireData[] out = new WireData[1];
            out[0] = new WireData(e[0].getType(), e[0].getValue() & e[1].getValue());
            return out;
        });
        getGFX("src/resources/data/AND.dat");
        setVisibleAllPorts(false);
    }
}
