package model.data;

import model.exceptions.NotReadyException;
import model.exceptions.NullConnectionException;

import java.util.AbstractCollection;

public class InputElementData extends BaseElementData {
    private final PortData[] _inputPorts;
    private final WireData[] _inData;

    public InputElementData(int input){
        _inputPorts = new PortData[input];
        _inData = new WireData[input];
    }

    @Override
    public void add(PortData data, boolean isInput) {
        for(int i = 0; i < _inputPorts.length; i++)
            if(_inputPorts[i] == null){
                data.index = i;
                _inputPorts[i] = data;
                break;
            }
    }
    public void add(PortData data){ add(data, true); }

    @Override
    public void execute() throws NullConnectionException, NotReadyException {
        for(int i = 0; i < _inputPorts.length; i++)
            if((_inData[i] = _inputPorts[i].getData()) == null) throw new NotReadyException();

        System.out.printf("Hello there, it's %d\n", _inData[0].getValue());
    }

    @Override
    protected WireData getPortData(int index) { return null; }

    @Override
    protected void addNextElements(AbstractCollection<BaseElementData> out){}

    @Override
    public void connect(BaseElementData to, int outputPortIndex, int inputPortIndex){}

    @Override
    public PortData getPort(int index, boolean isInput) {
        return _inputPorts[index];
    }
}
