package view.elements.output;

import model.data.OutputElementData;

public class Generator extends OutputElement {
    public Generator(){
        super();
        _data = new OutputElementData(1);
        getGFX("src/resources/data/GEN-B1.dat");
        setVisibleAllPorts(false);
    }
}
