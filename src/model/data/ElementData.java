package model.data;

import java.util.ArrayList;

public class ElementData {
    public ArrayList<PortData> inputPorts;
    public ArrayList<PortData> outputPorts;

    public ElementData(){
        inputPorts = new ArrayList<>();
        outputPorts = new ArrayList<>();
    }
}
