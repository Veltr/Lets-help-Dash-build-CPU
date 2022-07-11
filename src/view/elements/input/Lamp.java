package view.elements.input;

import model.data.InputElementData;

public class Lamp extends InputElement {
    public Lamp(){
        super();
        _data = new InputElementData(1);
        getGFX("src/resources/data/LAMP.dat");
        setVisibleAllPorts(false);
    }
}
