package model.data;

import java.util.ArrayList;

public class InputElementData extends BaseElementData {
    private final ArrayList<PortData> _inputPorts;

    public InputElementData(){
        _inputPorts = new ArrayList<>();
    }

    @Override
    public ArrayList<WireData> getOutput() { return null; }
    @Override
    public void add(PortData data, boolean isInput) {
        _inputPorts.add(data);
    }
}
