package view.elements.output;

import model.data.BusType;
import model.data.OutputElementData;
import model.data.WireData;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GeneratorB1 extends OutputElement {
    public GeneratorB1(){
        super();
        _data = new OutputElementData(1);
        ((OutputElementData)_data).setOutputData(new WireData(BusType.B1, 0));
        getGFX("src/resources/data/GEN-B1.dat");
        setVisibleAllPorts(false);
        addMouseListener(new GeneratorListener());
    }

    private class GeneratorListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            _curValue ^= 1;
            ((OutputElementData)_data).setOutputData(_curValue);
            setIcon(_icons.get(_curValue));
        }
    }
}