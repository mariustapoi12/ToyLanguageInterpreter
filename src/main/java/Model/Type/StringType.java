package Model.Type;

import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type{
    public StringType() {
    }

    public boolean equals(Object another){
        return another instanceof StringType;
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }
}
