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
public class ChartsBean extends ManagedBeanBase {

    private LineChartModel lineModel;
    private PieChartModel patientsDistributionByGenderModel;

    @PostConstruct
    public void init() {
        if (isLoggedInUserAdmin()) {
            drawPatientAgeLineChart();
            drawPatientsDistributionByGenderModel();
        }
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public PieChartModel getPatientsDistributionByGenderModel() {
        return patientsDistributionByGenderModel;
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

    public void drawPatientsDistributionByGenderModel() {
        patientsDistributionByGenderModel = new PieChartModel();
        
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
}
