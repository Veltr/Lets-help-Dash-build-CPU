package view.elements.logic;

import model.data.LogicElementData;
import model.data.WireData;
import view.staff.Wire;

public class ANDElement extends LogicElement {
    public ANDElement(){
        super();
        _data = new LogicElementData(2, 1, a -> {
            WireData[] out = new WireData[1];
            out[0] = new WireData(a[0].getType(), a[0].getValue() & a[1].getValue());
            return out;
        });
        getGFX("src/resources/data/AND.dat");
        setVisibleAllPorts(false);
    }
}
