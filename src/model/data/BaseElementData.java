package model.data;

import java.util.ArrayList;

public abstract class BaseElementData {
    public abstract ArrayList<WireData> getOutput();
    public abstract void add(PortData data, boolean isInput);
    public void add(PortData data){ add(data, true); }
}
