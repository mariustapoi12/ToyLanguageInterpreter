package view.lab8;

import Controller.Controller;
import Model.ADT.*;
import Model.Expression.*;
import Model.PrgState.PrgState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Exception.MyException;
import Repository.IRepository;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class ProgramListController {

    private InterpreterController interpreterController;

    public void setInterpreterController(InterpreterController interpreterController) {
        this.interpreterController = interpreterController;
    }

    @FXML
    private ListView<IStmt> programListView;

    public ListView<IStmt> getProgramListView() {
        return programListView;
    }

    public void setProgramListView(List<IStmt> programList) {
        ObservableList<IStmt> programs = FXCollections.observableArrayList(programList);
        programListView.setItems(programs);
    }

    @FXML
    private void displayProgram(MouseEvent mouseEvent) {
        IStmt selectedStatement = programListView.getSelectionModel().getSelectedItem();
        if(selectedStatement == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText("No program was selected!");
            alert.showAndWait();
        }
        else{
            int id = programListView.getSelectionModel().getSelectedIndex();
                try {
                    selectedStatement.typeCheck(new MyDictionary<>(new HashMap<>()));
                    PrgState programState = new PrgState(new MyStack<>(new Stack<>()), new MyDictionary<>(new HashMap<>()), new MyList<>(new ArrayList<>()), new MyHeap(), selectedStatement, new MyDictionary<>(new HashMap<>()), new MyLatch());
                    IRepository repository = new Repository(programState, "log" + (id + 1) + ".txt");
                    Controller controller = new Controller(repository);
                    interpreterController.setController(controller);
                } catch (MyException exception) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error encountered!");
                    alert.setContentText(exception.getMessage());
                    alert.showAndWait();
                }
            }
        programListView.getSelectionModel().clearSelection();
    }

    @FXML
    public void initialize(){
        programListView.setItems(getAllStatements());
        programListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private ObservableList<IStmt> getAllStatements(){
        List<IStmt> allStmts = new ArrayList<>();

        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()), new CompStmt(new AssignStmt("a", new ArithExp(1, new ValueExp(new IntValue(2)), new ArithExp(3, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(new AssignStmt("b", new ArithExp(1, new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))), new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseReadFile(new VarExp("varf"))))))))));
        IStmt ex5 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(5))),
                                new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(7))),
                                        new IfStmt(new RelationalExp(new VarExp("a"),
                                                new VarExp("b"),">" ),new PrintStmt(new VarExp("a")),
                                                new PrintStmt(new VarExp("b")))))));
        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp(1,new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));
        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt( new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp(1, new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5))))))));
        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));
        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));
        IStmt ex11 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));
        allStmts.add(ex1);
        allStmts.add(ex2);
        allStmts.add(ex3);
        allStmts.add(ex4);
        allStmts.add(ex5);
        allStmts.add(ex6);
        allStmts.add(ex7);
        allStmts.add(ex8);
        allStmts.add(ex9);
        allStmts.add(ex10);
        allStmts.add(ex11);
        IStmt ex12 = new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(
                                new VarDeclStmt("v3", new RefType(new IntType())),
                                new CompStmt(
                                        new VarDeclStmt("cnt", new IntType()),
                                        new CompStmt(
                                                new NewStmt("v1", new ValueExp(new IntValue(2))),
                                                new CompStmt(
                                                        new NewStmt("v2", new ValueExp(new IntValue(3))),
                                                        new CompStmt(
                                                                new NewStmt("v3", new ValueExp(new IntValue(4))),
                                                                new CompStmt(
                                                                        new NewLatchStmt("cnt", new ReadHeapExp(new VarExp("v2"))),
                                                                        new CompStmt(
                                                                                new ForkStmt(
                                                                                        new CompStmt(
                                                                                                new WriteHeapStmt("v1", new ArithExp(3, new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                                                                new CompStmt(
                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                                                                        new CompStmt(
                                                                                                                new CountDownStmt("cnt"),
                                                                                                                new ForkStmt(
                                                                                                                        new CompStmt(
                                                                                                                                new WriteHeapStmt("v2", new ArithExp(3, new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                                                new CompStmt(
                                                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                                                                                                                                        new CompStmt(
                                                                                                                                                new CountDownStmt("cnt"),
                                                                                                                                                new ForkStmt(
                                                                                                                                                        new CompStmt(
                                                                                                                                                                new WriteHeapStmt("v3", new ArithExp(3, new ReadHeapExp(new VarExp("v3")), new ValueExp(new IntValue(10)))),
                                                                                                                                                                new CompStmt(
                                                                                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v3"))),
                                                                                                                                                                        new CountDownStmt("cnt")
                                                                                                                                                                )
                                                                                                                                                        )
                                                                                                                                                )
                                                                                                                                        )
                                                                                                                                )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                ),
                                                                                new CompStmt(
                                                                                        new AwaitStmt("cnt"),
                                                                                        new CompStmt(
                                                                                                new PrintStmt(new ValueExp(new IntValue(100))),
                                                                                                new CompStmt(
                                                                                                        new CountDownStmt("cnt"),
                                                                                                        new PrintStmt(new ValueExp(new IntValue(100)))
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        allStmts.add(ex12);
        return FXCollections.observableArrayList(allStmts);
    }
}
