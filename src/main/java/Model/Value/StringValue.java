package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

import java.util.HashMap;

public class StringValue implements Value{
    String val;

    public StringValue(String val) {
        this.val = val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public boolean equals(Object another) {
        if (!(another instanceof StringValue))
            return false;
        StringValue castValue = (StringValue) another;
        return this.val.equals(castValue.val);
    }
}
