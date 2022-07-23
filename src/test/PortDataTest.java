package test;

import model.data.*;
import model.exceptions.NullConnectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortDataTest {
    private OutputElementData _output;
    private InputElementData _input;

    @BeforeEach
    void setUp() {
        _output = new OutputElementData(1);
        _input = new InputElementData(2);

        _input.setPort(1, new PortData(BusType.B8, 1));
    }

    @Test
    void connect() {
        assertFalse(_output.getPortData(0, false).connect(_input.getPortData(1, true)));
        assertTrue(_output.getPortData(0, false).connect(_input.getPortData(0, true)));
    }

    @Test
    void getData() throws NullConnectionException {
        _output.connect(_input, 0, 0);
        int outN = 1;
        _output.setOutputData(outN);
        assertEquals(outN, _input.getPortData(0, true).getData().getValue());
    }
}