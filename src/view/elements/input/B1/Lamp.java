package view.elements.input.B1;

import model.data.InputElementData;
import view.elements.input.InputElement;

public class Lamp extends InputElement {
    public Lamp(){
        super();
        _data = new InputElementData(1);
        getGFX("resources/data/LAMP.dat");
        setVisibleAllPorts(false);
    }

    @Override
    public void setState(){
        _curValue = ((InputElementData)_data).getData()[0].getValue();
        setIcon(_icons.get(_curValue));
    }
}
