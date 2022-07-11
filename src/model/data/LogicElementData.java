package model.data;

import model.exceptions.NotReadyException;
import model.exceptions.NullConnectionException;

import java.util.AbstractCollection;

public class LogicElementData extends BaseElementData {
    private final PortData[] _inputPorts;
    private final PortData[] _outputPorts;
    private final WireData[] _inData;
    private WireData[] _outData;
    private final ElementAction _action;
    private boolean _outputReady = false;

    public LogicElementData(int input, int output, ElementAction action){
        _inputPorts = new PortData[input];
        _outputPorts = new PortData[output];
        _inData = new WireData[input];
        _outData = new WireData[output];

        _action = action;
    }

    protected void clearForBfs(){
        _outputReady = false;
    }

    @Override
    public void add(PortData data, boolean isInput) {
        PortData[] arr;
        if(isInput) arr = _inputPorts;
        else arr = _outputPorts;

        for(int i = 0; i < arr.length; i++)
            if(arr[i] == null){
                data.index = i;
                arr[i] = data;
                break;
            }
    }

    @Override
    public void execute() throws NotReadyException, NullConnectionException {
        for(int i = 0; i < _inputPorts.length; i++)
            if((_inData[i] = _inputPorts[i].getData()) == null) throw new NotReadyException();

        _outData = _action.execute(_inData);
        _outputReady = true;
    }

    @Override
    protected WireData getPortData(int index) {
        if(!_outputReady) return null;
        return _outData[index];
    }

    @Override
    public void addNextElements(AbstractCollection<BaseElementData> out) {
        for(var i : _outputPorts) out.add(i.getConnectionBase());
    }

    @Override
    public void connect(BaseElementData to, int outputPortIndex, int inputPortIndex) {
        _outputPorts[outputPortIndex].connect(to.getPort(inputPortIndex, true));
    }

    @Override
    protected PortData getPort(int index, boolean isInput) {
        if(isInput) return _inputPorts[index];
        else return _outputPorts[index];
    }
}
