package view.lab8;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Interpreter extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader programListLoader = new FXMLLoader(Interpreter.class.getResource("ProgramList.fxml"));
        Parent programListRoot = programListLoader.load();
        Scene programListScene = new Scene(programListRoot, 500, 550);
        ProgramListController programListController = programListLoader.getController();
        stage.setTitle("Select a program");
        stage.setScene(programListScene);
        stage.show();

        FXMLLoader interpreterLoader = new FXMLLoader(Interpreter.class.getResource("Interpreter.fxml"));
        Parent interpreterRoot = interpreterLoader.load();
        Scene interpreterScene = new Scene(interpreterRoot, 700, 500);
        InterpreterController interpreterController = interpreterLoader.getController();
        programListController.setInterpreterController(interpreterController);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Interpreter");
        secondaryStage.setScene(interpreterScene);
        secondaryStage.show();
        stage.setOnCloseRequest(e -> secondaryStage.close());
        secondaryStage.setOnCloseRequest(e -> stage.close());
    }

    public static void main(String[] args) {
        launch();
    }
}