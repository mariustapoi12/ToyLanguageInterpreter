package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

import java.util.HashMap;

public class BoolValue implements Value{
    boolean val;

    public BoolValue(boolean val) {
        this.val = val;
    }

    public boolean getVal() {
        return val;
    }

    public void setVal(boolean val) {
        this.val = val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }
    @Override
    public boolean equals(Object another) {
        if (!(another instanceof BoolValue))
            return false;
        BoolValue castValue = (BoolValue) another;
        return this.val == castValue.val;
    }
}