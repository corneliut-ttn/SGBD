package main;

import connection.OracleJDBC;
import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import view.View;

/**
 * Created by Cornelius on 22.05.2015.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        View view =new  View(primaryStage);
        setUserAgentStylesheet(STYLESHEET_MODENA);
        OracleJDBC oracleJDBC = new OracleJDBC();
        Controller controller = new Controller(view,oracleJDBC);
        controller.startApp();

    }
}
