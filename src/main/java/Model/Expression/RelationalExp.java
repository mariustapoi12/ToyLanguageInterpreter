package Model.Expression;

import Exception.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Model.Value.BoolValue;

import java.util.Objects;

public class RelationalExp implements Exp{
    Exp e1;
    Exp e2;
    String op;

    public RelationalExp(Exp e1, Exp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (Objects.equals(this.op, "<"))
                    return new BoolValue((n1 < n2));
                else if (Objects.equals(this.op, "<="))
                    return new BoolValue(n1 <= n2);
                else if (Objects.equals(this.op, "=="))
                    return new BoolValue(n1 == n2);
                else if (Objects.equals(this.op, "!="))
                    return new BoolValue(n1 != n2);
                else if (Objects.equals(this.op, ">"))
                    return new BoolValue(n1 > n2);
                else if (Objects.equals(this.op, ">="))
                    return new BoolValue(n1 >= n2);
            } else
                throw new MyException("Second operand is not an integer");
        } else
            throw new MyException("First operand is not an integer");
        return null;
    }

    @Override
    public Exp deepCopy() {
        return new RelationalExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    @Override
    public String toString() {
        if (Objects.equals(op, "<")) return e1.toString()+"<"+e2.toString();
        if (Objects.equals(op, "<=")) return e1.toString()+"<="+e2.toString();
        if (Objects.equals(op, "==")) return e1.toString()+"=="+e2.toString();
        if (Objects.equals(op, "!=")) return e1.toString()+"!="+e2.toString();
        if (Objects.equals(op, ">")) return e1.toString()+">"+e2.toString();
        if (Objects.equals(op, ">=")) return e1.toString()+">="+e2.toString();
        return null;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=e1.typeCheck(typeEnv);
        typ2=e2.typeCheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            }
            else
                throw new MyException("second operand is not an integer");
        }
        else
            throw new MyException("first operand is not an integer");
    }
}
