package test;

import model.data.*;
import model.exceptions.NullConnectionException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CircuitDataTest {
    private CircuitData circuit;
    @BeforeEach
    void setUp() {
        circuit = new CircuitData();

        OutputElementData[] gens = new OutputElementData[2];
        for(int i = 0; i < gens.length; i++){
            gens[i] = new OutputElementData(1);
            circuit.add(gens[i]);
        }
        gens[0].setOutputData(new SignalData(BusType.B1, 1));
        gens[1].setOutputData(new SignalData(BusType.B1, 1));


        LogicElementData element = new LogicElementData(2, 1, a -> {
            SignalData[] out = new SignalData[1];
            out[0] = new SignalData(a[0].getType(), a[0].getValue() & a[1].getValue());
            return out;
        });
        circuit.add(element);


        InputElementData lamp = new InputElementData(1);
        circuit.add(lamp);


        gens[0].connect(element, 0, 0);
        gens[1].connect(element, 0, 1);

        element.connect(lamp, 0, 0);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void start() {

    }
}