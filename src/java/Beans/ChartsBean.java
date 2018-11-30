/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import BusinessObjects.Patient;
import CommonUtils.QueryUtils;
import java.util.List;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ChartsBean {

    private PieChartModel model;
    private LineChartModel lineModel;

    @PostConstruct
    public void init() {
        drawPatientAgeLineChart();
        drawPatientDiseasePieChart();
    }

    public void drawPatientAgeLineChart() {
        List<Patient> patientsList = QueryUtils.getPatientList(null);
        if (patientsList != null && patientsList.size() > 0) {

            lineModel = new LineChartModel();
            LineChartSeries s = new LineChartSeries();
            s.setLabel("Patients Age");
            Integer counter = 1;

            for (Patient patient : patientsList) {
                s.set(counter++, patient.getAge());
            }
            
            lineModel.addSeries(s);
            lineModel.setLegendPosition("e");
            Axis y = lineModel.getAxis(AxisType.Y);
            y.setMin(0);
            y.setMax(100);
            y.setLabel("Years Old");

            Axis x = lineModel.getAxis(AxisType.X);
            x.setMin(0);
            x.setMax(10);
            x.setTickInterval("1");
            x.setLabel("Number of Patients");
        }
    }

    public void drawPatientDiseasePieChart() {
        model = new PieChartModel();
        model.set("Java", 62);
        model.set("Python", 46);
        model.set("JavaScript", 38);
        model.set("C++", 31);
        model.set("C#", 27);
        model.set("PHP", 14);
        model.set("Perl", 14);

        //followings are some optional customizations:
        //set title
        model.setTitle("2018 Jobs for top languages");
        //set legend position to 'e' (east), other values are 'w', 's' and 'n'
        model.setLegendPosition("e");
        //enable tooltips
        model.setShowDatatip(true);
        //show labels inside pie chart
        model.setShowDataLabels(true);
        //show label text  as 'value' (numeric) , others are 'label', 'percent' (default). Only one can be used.
        model.setDataFormat("value");
        //format: %d for 'value', %s for 'label', %d%% for 'percent'
        model.setDataLabelFormatString("%dK");
        //pie sector colors
        model.setSeriesColors("aaf,afa,faa,ffa,aff,faf,ddd");
    }

    public PieChartModel getModel() {
        return model;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }
}
