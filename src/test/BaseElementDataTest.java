package test;

import model.data.*;
import model.exceptions.NotReadyException;
import model.exceptions.NullConnectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BaseElementDataTest {
    private LogicElementData _logicTest;
    private final ArrayList<InputElementData> _inputs = new ArrayList<>();
    private final ArrayList<OutputElementData> _outputs = new ArrayList<>();
    @BeforeEach
    void setUp() {
        for(int i = 0; i < 2; i++) _inputs.add(new InputElementData(1));
        for(int i = 0; i < 1; i++) _outputs.add(new OutputElementData(1));

        _logicTest = new LogicElementData(1, 2, e -> {
            SignalData[] out = new SignalData[2];
            out[0] = new SignalData(e[0].getType(), e[0].getValue());
            out[1] = new SignalData(e[0].getType(), e[0].getValue() == 0 ? 1 : 0);
            return out;
        });
        _logicTest.connect(_inputs.get(0), 0, 0);
        _logicTest.connect(_inputs.get(1), 1, 0);

        _outputs.get(0).connect(_logicTest, 0, 0);
    }

    @Test
    void connect() {
        _logicTest.connect(_inputs.get(0), 0, 0);
        assertEquals(_inputs.get(0), _logicTest.getPortData(0, false).getConnectionBase());
    }

    @Test
    void execute() throws NullConnectionException, NotReadyException {
        int outN = 0;
        for(var i : _outputs) { i.setOutputData(outN); i.execute(); }
        _logicTest.execute(); _logicTest.addNextElements(new ArrayList<>());
        for(var i : _inputs) i.execute();

        assertEquals(outN, _inputs.get(0).getData()[0].getValue());
        assertEquals(outN == 0 ? 1 : 0, _inputs.get(1).getData()[0].getValue());
    }
}