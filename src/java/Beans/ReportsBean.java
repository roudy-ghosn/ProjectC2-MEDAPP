/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import BusinessObjects.Report;
import CommonUtils.QueryUtils;
import CommonUtils.SessionUtils;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author user
 */
@ManagedBean
@ViewScoped

public class ReportsBean implements Serializable {

    private Report report;

    public ReportsBean() {
        report = new Report();
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public String getReportIdFromURL() {
        return SessionUtils.getRequest().getParameter("reportId");
    }

    public void getSpecifiedReport(String id) {
        report = QueryUtils.getReport(id);
    }

    public boolean isCreateMode() {
        return SessionUtils.getRequest().getParameter("createMode") != null;
    }

    public boolean isDisplayMode() {
        return !isCreateMode();
    }

    public void onLoad() {
        if (isCreateMode()) {
            report = new Report();
        } else {
            getSpecifiedReport(getReportIdFromURL());
        }
    }

    public void save() {
        if (report.getId() != null) {
            QueryUtils.updateReport(report);
        } else {
            QueryUtils.insertReport(report);
        }
    }

}
