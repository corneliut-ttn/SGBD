package controller;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;

public class SimpleReportExample {
    private JasperReportBuilder report;
    public void makeReport(Connection connection,String title,String query,String[] columns){
        this.report = DynamicReports.report();//a new report
        for(int i = 0; i < columns.length;i++){
            this.report.addColumn(Columns.column(columns[i],columns[i],DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setHorizontalAlignment(HorizontalAlignment.LEFT));
        }
        this.report.addTitle(Components.text(title));
        this.report.setDataSource(query,connection);


    }
    public void writeReport(String filename){
        try {
            System.out.println("Begin to write");
            this.report.show();//show the report
            this.report.toPdf(new FileOutputStream("C:/Users/Cornelius/Desktop/"+ filename+".pdf"));//export the report to a pdf file
        } catch (DRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
