package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;

import java.util.HashMap;

public class IntValue implements Value{
    int val;

    public IntValue(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }

    @Override
    public boolean equals(Object another) {
        if (!(another instanceof IntValue))
            return false;
        IntValue castValue = (IntValue) another;
        return this.val == castValue.val;
    }
}