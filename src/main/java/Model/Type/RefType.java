package Model.Type;

import Model.Value.Value;
import Model.Value.RefValue;

public class RefType implements Type{
    Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RefType)
            return inner.equals(((RefType) obj).getInner());
        return false;
    }

    @Override
    public String toString() {
        return "Ref("+inner.toString()+")";
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public Type deepCopy() {
        return new RefType(inner.deepCopy());
    }
}
