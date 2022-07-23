package view.elements.logic;

import model.data.LogicElementData;
import model.data.SignalData;

public class XORElement extends LogicElement {
    public XORElement(){
        super();
        _data = new LogicElementData(2, 1, e -> {
            SignalData[] out = new SignalData[1];
            out[0] = new SignalData(e[0].getType(), e[0].getValue() ^ e[1].getValue());
            return out;
        });
        getGFX("resources/data/XOR.dat");
        setVisibleAllPorts(false);
    }
}
