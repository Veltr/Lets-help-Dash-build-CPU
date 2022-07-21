package view.elements.logic;

import model.data.LogicElementData;
import model.data.SignalData;

public class IMPElement extends LogicElement {
    public IMPElement(){
        super();
        _data = new LogicElementData(2, 1, e -> {
            SignalData[] out = new SignalData[1];
            boolean a = (e[0].getValue() == 0), b = (e[1].getValue() == 0);
            out[0] = new SignalData(e[0].getType(), !(!a & b) ? 1 : 0);
            return out;
        });
        getGFX("src/resources/data/IMP.dat");
        setVisibleAllPorts(false);
    }
}
