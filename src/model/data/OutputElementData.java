package model.data;

import java.util.AbstractCollection;

public class OutputElementData extends BaseElementData {
    private final PortData[] _outputPorts;
    private WireData _outData;

    public OutputElementData(int output){
        _outputPorts = new PortData[output];
    }

    public void setOutputData(WireData data){
        _outData = data;
    }
    public WireData getOutputData(){
        return _outData;
    }


    @Override
    public void add(PortData data, boolean isInput) {
        for(int i = 0; i < _outputPorts.length; i++)
            if(_outputPorts[i] == null){
                data.index = i;
                _outputPorts[i] = data;
                break;
            }
    }
    public void add(PortData data){ add(data, false); }

    @Override
    public void execute(){}

    @Override
    protected WireData getPortData(int index) {
        return _outData;
    }

    @Override
    protected void addNextElements(AbstractCollection<BaseElementData> out) {
        for(var i : _outputPorts) out.add(i.getConnectionBase());
    }

    @Override
    public void connect(BaseElementData to, int outputPortIndex, int inputPortIndex) {
        _outputPorts[outputPortIndex].connect(to.getPort(inputPortIndex, true));
    }

    @Override
    protected PortData getPort(int index, boolean isInput) {
        return _outputPorts[index];
    }
}
