package view.elements.logic;

import model.data.LogicElementData;

public class ORElement extends LogicElement {
    public ORElement(){
        super();
        _data = new LogicElementData(2, 1, e->{ return e; });
        getGFX("src/resources/data/OR.dat");
        setVisibleAllPorts(false);
    }
}
