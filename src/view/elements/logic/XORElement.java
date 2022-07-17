package view.elements.logic;

import model.data.LogicElementData;
import model.data.WireData;
import view.elements.logic.LogicElement;

public class XORElement extends LogicElement {
    public XORElement(){
        super();
        _data = new LogicElementData(2, 1, e -> {
            WireData[] out = new WireData[1];
            out[0] = new WireData(e[0].getType(), e[0].getValue() ^ e[1].getValue());
            return out;
        });
        getGFX("src/resources/data/XOR.dat");
        setVisibleAllPorts(false);
    }
}
