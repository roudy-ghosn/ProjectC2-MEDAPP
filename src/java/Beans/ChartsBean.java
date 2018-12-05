package Beans;

import BusinessObjects.Patient;
import CommonUtils.QueryUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
public class ChartsBean extends ManagedBeanBase {

    String year = new SimpleDateFormat("yyyy").format(new Date());

    private PieChartModel diseaseDistributionModel;
    private PieChartModel patientsDistributionByGenderModel;

    private LineChartModel usersDistributionForPast5YearsChart;

    @PostConstruct
    public void init() {
        diseaseDistributionModel = new PieChartModel();
        patientsDistributionByGenderModel = new PieChartModel();
        usersDistributionForPast5YearsChart = new LineChartModel();

        if (isLoggedInUserAdmin()) {
            drawDiseaseDistributionModel();
            drawPatientsDistributionByGenderModel();
            drawUsersDistributionForPast5YearsChart();
        }
    }

    public PieChartModel getDiseaseDistributionModel() {
        return diseaseDistributionModel;
    }

    public PieChartModel getPatientsDistributionByGenderModel() {
        return patientsDistributionByGenderModel;
    }

    public LineChartModel getUsersDistributionForPast5YearsChart() {
        return usersDistributionForPast5YearsChart;
    }

    public void drawUsersDistributionForPast5YearsChart() {
        Integer counter = 0;
        Integer maxUsers = 0;
        Map<String, String[]> results = new HashMap<String, String[]>();
        results = QueryUtils.getUsersDistributionForPast5Years(year);
        if (results != null && results.size() > 0) {
            usersDistributionForPast5YearsChart.setTitle("Users Distribution for the Past 5 years");

            LineChartSeries s = new LineChartSeries();
            s.setLabel("Users/Year");

            Iterator iterator = results.entrySet().iterator();
            while (iterator.hasNext() && counter < 5) {
                Map.Entry users = (Map.Entry) iterator.next();
                String[] values = (String[]) users.getValue();
                s.set(new Integer(values[0]), new Integer(values[1]));
                if (new Integer(values[1]) > maxUsers) {
                    maxUsers = new Integer(values[1]);
                }
                counter++;
            }

            usersDistributionForPast5YearsChart.addSeries(s);
            usersDistributionForPast5YearsChart.setLegendPosition("e");
            Axis y = usersDistributionForPast5YearsChart.getAxis(AxisType.Y);
            y.setMin(0);
            y.setMax(maxUsers + 10);

            Axis x = usersDistributionForPast5YearsChart.getAxis(AxisType.X);
            x.setMin((new Integer(year) - 5));
            x.setMax((new Integer(year) + 1));
            x.setTickInterval("1");
        }
    }

    public void drawPatientsDistributionByGenderModel() {
        String[] results = new String[2];
        results = QueryUtils.getPatientsDistributionByGender();

        patientsDistributionByGenderModel.set("Male", new Integer(results[0]));
        patientsDistributionByGenderModel.set("Female", new Integer(results[1]));

        //set title
        patientsDistributionByGenderModel.setTitle("Patients Distribution by Gender");
        //set legend position to 'e' (east), other values are 'w', 's' and 'n'
        patientsDistributionByGenderModel.setLegendPosition("e");
        //show labels inside pie chart
        patientsDistributionByGenderModel.setShowDataLabels(true);
        //show label text  as 'value' (numeric) , others are 'label', 'percent' (default). Only one can be used.
        patientsDistributionByGenderModel.setDataFormat("percent");
        //format: %d for 'value', %s for 'label', %d%% for 'percent'
        patientsDistributionByGenderModel.setDataLabelFormatString("%d%%");
        //pie sector colors
        patientsDistributionByGenderModel.setSeriesColors("3282BD,9ECAE1");
    }

    public void drawDiseaseDistributionModel() {
        Map<String, String[]> results = new HashMap<String, String[]>();
        results = QueryUtils.getDiseaseDistribution(year);
        Iterator iterator = results.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry disease = (Map.Entry) iterator.next();
            String[] values = (String[]) disease.getValue();
            diseaseDistributionModel.set(values[0], new Integer(values[1]));
        }

        //set title
        diseaseDistributionModel.setTitle("Disease Percentage for year " + year);
        //set legend position to 'e' (east), other values are 'w', 's' and 'n'
        diseaseDistributionModel.setLegendPosition("e");
        //show labels inside pie chart
        diseaseDistributionModel.setShowDataLabels(true);
        //show label text  as 'value' (numeric) , others are 'label', 'percent' (default). Only one can be used.
        diseaseDistributionModel.setDataFormat("percent");
        //format: %d for 'value', %s for 'label', %d%% for 'percent'
        diseaseDistributionModel.setDataLabelFormatString("%d%%");
        //pie sector colors
        diseaseDistributionModel.setSeriesColors("3282BD,9ECAE1,DEEBF7");
    }
}
