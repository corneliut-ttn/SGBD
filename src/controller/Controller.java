package controller;


import connection.OracleJDBC;
import graph.Bonus;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import view.View;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by Cornelius on 22.05.2015.
 */
public class Controller {
    SimpleReportExample simpleReportExample = new SimpleReportExample();
    View theView;
    OracleJDBC theOracleJDBC;
    public Controller(View view,OracleJDBC oracleJDBC){
        this.theView = view;
        this.theOracleJDBC = oracleJDBC;
    }
    public void startApp(){
        theView.startApp();
        addListener();
    }
    private void addListener(){
        theView.getMakeConnecion().setOnAction(new AddListenerToButtonMakeConnection());
        theView.getGetResult().setOnAction(new AddListenerToButtonGetResult());
        theView.getCreateReportButton().setOnAction(new AddListenerToButtonMakeReport());
        theView.getShowMetadate().setOnAction(new AddListnereToButtonGetMetaDate());
        theView.getShowMetadateProcedure().setOnAction(new AddListenerToButtonGetMetaDateProcedure());
        theView.getShowGraph().setOnAction(new AddListenerToButtonShowGraph());
    }
    private class AddListenerToButtonGetResult implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
                    actionForGetResult();
            }

        }

    private class AddListenerToButtonMakeConnection implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

            theOracleJDBC.setConnection(theOracleJDBC.makeConection());
            theView.printConnectionOk("Connection is made");

        }
    }

    private class AddListenerToButtonMakeReport implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            String[] coloane = getCollumns(getNrOfCollumns());
            makeReport(coloane);
        }
    }

    private void makeReport(String[] coloane){
        simpleReportExample.makeReport(theOracleJDBC.getConnection(), theView.getInterogationArea().getText(), theView.getInterogationArea().getText(),coloane);
        simpleReportExample.writeReport("raport");
    }
    private String[] getCollumns(int nr){
        String[] coloane = new String [nr];
        try {
            for(int i=0 ; i<theOracleJDBC.getResultSet().getMetaData().getColumnCount(); i++) {
                coloane[i] = theOracleJDBC.getResultSet().getMetaData().getColumnName(i + 1);
            }
        } catch (SQLException e) {
            theView.printConnectionOk(e.getMessage());
        }
        return coloane;
    }
    private int getNrOfCollumns(){
        int nrCol = -1;
        try {
            nrCol = theOracleJDBC.getResultSet().getMetaData().getColumnCount();
        } catch (SQLException e) {
            theView.printConnectionOk(e.getMessage());
        }
        return nrCol;
    }

    private void actionForGetResult(){
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        TableView tableview = new TableView();
        setStatement();
        executeQuery();
        setMetaDateForResult();
        setResult(tableview);
        setDataForTable(data);
        tableview.setItems(data);
        theView.printResult(tableview,theView.getInterogationArea().getText());

    }
    private void setStatement(){
        try {
            theOracleJDBC.setStatement(theOracleJDBC.getConnection().createStatement());
        } catch (SQLException e) {
            theView.printConnectionOk(e.getMessage());
        }
    }
    private void executeQuery(){
        try {
            theOracleJDBC.setResultSet(theOracleJDBC.getStatement().executeQuery(theView.getInterogationArea().getText()));
        } catch (SQLException e) {

            theView.printConnectionOk(e.getMessage());
        }
    }
    private void setMetaDateForResult(){
        try {
            theOracleJDBC.setMetaData(theOracleJDBC.getResultSet().getMetaData());
        } catch (SQLException e) {
            theView.printConnectionOk(e.getMessage());
        }
    }
    private void setResult(TableView tableview){
        try {
            for(int i=0 ; i<theOracleJDBC.getResultSet().getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(theOracleJDBC.getResultSet().getMetaData().getColumnName(i+1));//adaug in table numele coloanelor

                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {

                        if( (param.getValue().get(j) == null)) return new SimpleStringProperty("NULL");
                        else return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableview.getColumns().addAll(col);
            }
        } catch (SQLException e) {
            theView.printConnectionOk(e.getMessage());

        }
    }
    private void setDataForTable(ObservableList<ObservableList> data){
        try {
            while(theOracleJDBC.getResultSet().next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=theOracleJDBC.getResultSet().getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(theOracleJDBC.getResultSet().getString(i));
                }
                data.add(row);

            }
        } catch (SQLException e) {
            theView.printConnectionOk(e.getMessage());
        }
    }

    private class AddListnereToButtonGetMetaDate implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            theView.printMetadate(   getMetaDate("TABLE"));
        }
    }

    private class AddListenerToButtonGetMetaDateProcedure implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            theView.printMetadate(getMetaDate("PROCEDURE"));
        }
    }

    private class AddListenerToButtonShowGraph implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            System.out.println("start graph");
            Bonus bonus=new Bonus(theOracleJDBC.getConnection());
        }
    }

    private String getMetaDate(String type){
        String metaDate = "";
        String TABLE_NAME = "TABLE_NAME";
        String[] TABLE_TYPES = {type};
        DatabaseMetaData dbmd = null;
        try {
            dbmd = theOracleJDBC.getConnection().getMetaData();
        } catch (SQLException e) {
            theView.printConnectionOk(e.getMessage());
        }

        ResultSet tables = null;
        try {
            tables = dbmd.getTables(null, null, null, TABLE_TYPES);
        } catch (SQLException e) {
            theView.printConnectionOk(e.getMessage());

        }
        try {
            while (tables.next()) {
                metaDate = metaDate + tables.getString(TABLE_NAME) + "\n";
            }
        } catch (SQLException e) {
            theView.printConnectionOk(e.getMessage());
        }
        return metaDate;
    }
}


