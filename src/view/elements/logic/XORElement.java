package view.elements.logic;

import model.data.LogicElementData;

public class XORElement extends LogicElement {
    public XORElement(){
        super();
        _data = new LogicElementData(2, 1, e->{ return e; });
        getGFX("src/resources/data/XOR.dat");
        setVisibleAllPorts(false);
    }
}
