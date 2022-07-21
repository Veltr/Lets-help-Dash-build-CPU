package model.data;

import model.exceptions.NotReadyException;
import model.exceptions.NullConnectionException;

import java.util.AbstractCollection;

public class LogicElementData extends BaseElementData {
    private final PortData[] _inputPorts;
    private final PortData[] _outputPorts;
    private final SignalData[] _inData;
    private SignalData[] _outData;
    private final ElementAction _action;
    private boolean _outputReady = false;

    public LogicElementData(int input, int output, ElementAction action){
        _inputPorts = new PortData[input];
        _outputPorts = new PortData[output];
        _inData = new SignalData[input];
        _outData = new SignalData[output];

        _action = action;

        for(int i = 0; i < input; i++) _inputPorts[i] = new PortData(BusType.B1, i, this);
        for(int i = 0; i < output; i++) _outputPorts[i] = new PortData(BusType.B1, i, this);
    }

    protected void clearForBfs(){
        _outputReady = false;
    }

    @Override
    public void setPort(int index, PortData data, boolean isInput) {
        if(isInput) _inputPorts[index].setAll(data);
        else _outputPorts[index].setAll(data);
    }

    @Override
    public void execute() throws NotReadyException, NullConnectionException {
        //for(var i : _outputPorts) if(!i.isConnected()) throw new NullConnectionException(this, i);
        for(int i = 0; i < _inputPorts.length; i++)
            if((_inData[i] = _inputPorts[i].getData()) == null) throw new NotReadyException();

        _outData = _action.execute(_inData);
    }

    @Override
    protected SignalData getDataFromPort(int index) {
        if(!_outputReady) return null;
        return _outData[index];
    }

    @Override
    public void addNextElements(AbstractCollection<BaseElementData> out) throws NullConnectionException {
        if(_outputReady) return;
        _outputReady = true;
        for(var i : _outputPorts) {
            BaseElementData cur = i.getConnectionBase();
            if(cur == null) throw new NullConnectionException(this, i);
            out.add(cur);
        }
    }

    @Override
    public void connect(BaseElementData to, int outputPortIndex, int inputPortIndex) {
        _outputPorts[outputPortIndex].connect(to.getPortData(inputPortIndex, true));
    }

    @Override
    public PortData getPortData(int index, boolean isInput) {
        if(isInput) return _inputPorts[index];
        else return _outputPorts[index];
    }
}
