package view.elements.logic;

import model.data.LogicElementData;

public class IMPElement extends LogicElement {
    public IMPElement(){
        super();
        _data = new LogicElementData(2, 1, e->{ return e; });
        getGFX("src/resources/data/IMP.dat");
        setVisibleAllPorts(false);
    }
}
