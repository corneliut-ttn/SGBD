package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Created by Cornelius on 22.05.2015.
 */
public class View {

    private Stage stage;
    private Scene scene;
    private Button getResult;
    private BorderPane principalPane;
    private TextArea interogationArea;
    private HBox buttomBox;
    private Button createReportButton = new Button("Create Report");
    private Button makeConnecion;
    private Button showMetadate;
    private Button showMetadateProcedure;
    private Button showGraph;

    public View(Stage stage){
        this.stage = stage;
    }

    public void startApp(){
        initTextArea();
        initAndSetButtomBox();
        initPrincipalPane();
        setPrincipalPane();
        setScene();
        stage.setScene(scene);
        stage.show();
    }

    private void setScene(){

        scene = new Scene(principalPane);

    }
    private void initPrincipalPane(){
        principalPane = new BorderPane();
    }
    private void setPrincipalPane(){
        principalPane.setCenter(interogationArea);
        principalPane.setTop(buttomBox);
    }
    private void initAndSetButtomBox(){
        showMetadate = new Button("Show Metadate-Table");
        makeConnecion = new Button("Make Connection");
        getResult = new Button("Get Result");
        showMetadateProcedure = new Button("Show procedures");
        showGraph = new Button("Show graph");
        buttomBox = new HBox();
        buttomBox.getChildren().addAll(makeConnecion,getResult,showMetadate,showMetadateProcedure,showGraph);
    }
    private void initTextArea(){
        interogationArea = new TextArea();
    }

    public Button getGetResult() {
        return getResult;
    }

    public TextArea getInterogationArea() {
        return interogationArea;
    }

    public Button getMakeConnecion() {
        return makeConnecion;
    }

    public Button getShowMetadate() {
        return showMetadate;
    }

    public void printConnectionOk(String s){

        Button button = new Button("OK");
        final Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setScene(new Scene(VBoxBuilder.create().
                children(new Text(s),button).
                alignment(Pos.CENTER).padding(new Insets(5)).build()));
        dialogStage.show();

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                dialogStage.close();
            }
        });
    }


    public Button getCreateReportButton() {
        return createReportButton;
    }

    public Button getShowMetadateProcedure() {
        return showMetadateProcedure;
    }

    public Button getShowGraph(){
        return showGraph;
    }

    public void printResult(TableView tableView,String interogation){

        final Stage dialogStage = new Stage();


        final Label label = new Label(interogation);
        label.setFont(new Font("Arial", 20));

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, tableView, createReportButton);
        Scene scene1 = new Scene(vbox);
        dialogStage.setScene(scene1);
        dialogStage.show();
    }

    public void printMetadate(String metaDate){
        Stage dialogStage = new Stage();
        TextArea textArea = new TextArea();
        textArea.appendText(metaDate);
        Scene scene1 = new Scene(textArea);
        dialogStage.setScene(scene1);
        dialogStage.show();

    }
}




