package test;

import model.data.*;
import model.exceptions.NullConnectionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CircuitDataTest {
    private CircuitData _circuit;
    private InputElementData _lamp;
    private OutputElementData[] _gens;

    @BeforeEach
    void setUp() {
        _circuit = new CircuitData();

        _gens = new OutputElementData[2];
        for(int i = 0; i < _gens.length; i++){
            _gens[i] = new OutputElementData(1);
            _circuit.add(_gens[i]);
        }


        LogicElementData element = new LogicElementData(2, 1, a -> {
            SignalData[] out = new SignalData[1];
            out[0] = new SignalData(a[0].getType(), a[0].getValue() & a[1].getValue());
            return out;
        });
        _circuit.add(element);


        _lamp = new InputElementData(1);
        _circuit.add(_lamp);


        _gens[0].connect(element, 0, 0);
        _gens[1].connect(element, 0, 1);

        element.connect(_lamp, 0, 0);
    }

    @Test
    void start() throws NullConnectionException {
        int out1 = 1, out2 = 0;
        _gens[0].setOutputData(new SignalData(BusType.B1, out1));
        _gens[1].setOutputData(new SignalData(BusType.B1, out2));

        _circuit.start();
        Assertions.assertEquals(out1 & out2, _lamp.getData()[0].getValue());
    }
}