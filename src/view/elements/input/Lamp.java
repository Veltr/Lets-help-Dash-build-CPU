package view.elements.input;

import model.data.InputElementData;

public class Lamp extends InputElement {
    public Lamp(){
        super();
        _data = new InputElementData(1);
        getGFX("src/resources/data/LAMP.dat");
        setVisibleAllPorts(false);
    }

    @Override
    public void setState(){
        _curValue = ((InputElementData)_data).getData()[0].getValue();
        setIcon(_icons.get(_curValue));
    }
}
