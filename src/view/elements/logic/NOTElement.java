package view.elements.logic;

import model.data.LogicElementData;
import model.data.SignalData;

public class NOTElement extends LogicElement {
    public NOTElement(){
        super();
        _data = new LogicElementData(1, 1, e -> {
            SignalData[] out = new SignalData[1];
            out[0] = new SignalData(e[0].getType(), e[0].getValue() == 0 ? 1 : 0);
            return out;
        });
        getGFX("src/resources/data/NOT.dat");
        setVisibleAllPorts(false);
    }
}
