package Repository;

import Model.PrgState.PrgState;
import Exception.MyException;

import java.util.List;

public interface IRepository {
    List<PrgState> getPrgList();

    void setPrgList(List<PrgState> programStates);
    void addPrgState(PrgState state);

    void logPrgStateExec(PrgState prgState) throws MyException;

    void emptyLogFile() throws MyException;
}