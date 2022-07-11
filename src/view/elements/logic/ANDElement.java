package view.elements.logic;

import model.data.LogicElementData;

public class ANDElement extends LogicElement {
    public ANDElement(){
        super();
        _data = new LogicElementData(2, 1, e->{ return e; });
        getGFX("src/resources/data/AND.dat");
        setVisibleAllPorts(false);
    }
}
